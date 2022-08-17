package com.example.presentacionproximate.ui.views

import android.Manifest
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.presentacionproximate.databinding.ActivityCameraBinding
import java.io.File
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.*

typealias LumaListener = (luma: Double) -> Unit

/*class Camera : AppCompatActivity() {
    private lateinit var broadcastManager: LocalBroadcastManager

    private var displayId: Int = -1
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var cameraProvider: ProcessCameraProvider? = null

    var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    lateinit var binding: ActivityCameraBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.cameraCaptureButton.setOnClickListener {
            takePhoto()
        }

        binding.cameraSwitchButton.setOnClickListener {
            lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            // Se realiza nuevamente la vinculación
            bindCameraUseCases()
        }

        //Solicitar permisos de uso al usuario
        if (allPermissionsGranted()) {
            startCamera()

        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Debe proporcionar permisos para accesar al dispositivo.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({

            //CameraProvider
            cameraProvider = cameraProviderFuture.get()

            //Cambio entre camaras dependiendo la disponibilidad
            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("No existe dispositivo de captura disponible...")
            }

            // Habilitar o deshabilitar boton de cambio de camaras
            updateCameraSwitchButton()

            //Uses case
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun takePhoto() {
        // Se crea el archivo de salida
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Crear archivo de salida incluyendo metadatos
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Capturar la imagen actual de la camara
        imageCapture?.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Error al realizar la captura de la foto: ${exc.message}", exc)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    Toast.makeText(baseContext,"IMG guardada ${savedUri}", Toast.LENGTH_SHORT).show()

                    // API lvl 23+ API

                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                        // Actualizar galeria con imagen almacenada
                        setGalleryThumbnail(savedUri)
                    }

                    // Utilizando (MediaScannerConnecction) para escanear los medios de la galeria
                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)
                    MediaScannerConnection.scanFile(
                        baseContext,
                        arrayOf(savedUri.toFile().absolutePath),
                        arrayOf(mimeType)
                    ){_, uri ->
                        Log.d(TAG,"Imagen capturada fue guardada: $uri")
                    }
                }
            })
    }

    private fun updateCameraSwitchButton(){
        val switchCameraButton = binding.cameraSwitchButton
        try {
            switchCameraButton.isEnabled = hasBackCamera() && hasFrontCamera()
        }catch (exception: CameraInfoUnavailableException){
            switchCameraButton.isEnabled = false
        }
    }

    /** Devuelve true si el dispositivo cuenta con camara trasera, sino false*/
    private fun hasBackCamera(): Boolean{
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    /** Devuelve true si el dispositivo cuenta con camara frontal, sino false*/
    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    private fun bindCameraUseCases(){

        //Metricas para determinar tamaño completo de pantalla
        val metrics = DisplayMetrics().also { binding.viewFinder.display.getRealMetrics(it) }
        Log.d(TAG,"Pantalla: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Log.d(TAG, "Aspecto: $screenAspectRatio")

        val rotation = binding.viewFinder.display.rotation

        //CameraProvider
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Error al iniciar la camara.")

        //CameraSelector

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        // Preview
        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        // ImageCapture
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        // ImageAnalysis
        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            // Se asigna a una instancia
            .also {
                it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                    Log.d(TAG, "Average luminosity: $luma")
                })
            }

        // Desvincular para poder usar de nuevo
        cameraProvider.unbindAll()

        try {
            //Vincular la camara a "use cases"
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalyzer
            )

            // Generar vista previa de la camara
            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case fallo", exc)
        }
    }
    /**
     * Our custom image analysis class.
     *
     * <p>All we need to do is override the function `analyze` with our desired operations. Here,
     * we compute the average luminosity of the image by looking at the Y plane of the YUV frame.
     */
    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set

        /**
         * Used to add listeners that will be called with each luma computed
         */
        fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

        /**
         * Helper extension function used to extract a byte array from an image plane buffer
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        /**
         * Analyzes an image to produce a result.
         *
         * <p>The caller is responsible for ensuring this analysis method can be executed quickly
         * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
         * images will not be acquired and analyzed.
         *
         * <p>The image passed to this method becomes invalid after this method returns. The caller
         * should not store external references to this image, as these references will become
         * invalid.
         *
         * @param image image being analyzed VERY IMPORTANT: Analyzer method implementation must
         * call image.close() on received images when finished using them. Otherwise, new images
         * may not be received or the camera may stall, depending on back pressure setting.
         *
         */
        override fun analyze(image: ImageProxy) {
            // If there are no listeners attached, we don't need to perform analysis
            if (listeners.isEmpty()) {
                image.close()
                return
            }

            // Keep track of frames analyzed
            val currentTime = System.currentTimeMillis()
            frameTimestamps.push(currentTime)

            // Compute the FPS using a moving average
            while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
            val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
            val timestampLast = frameTimestamps.peekLast() ?: currentTime
            framesPerSecond = 1.0 / ((timestampFirst - timestampLast) /
                    frameTimestamps.size.coerceAtLeast(1).toDouble()) * 1000.0

            // Analysis could take an arbitrarily long amount of time
            // Since we are running in a different thread, it won't stall other use cases

            lastAnalyzedTimestamp = frameTimestamps.first

            // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance plane
            val buffer = image.planes[0].buffer

            // Extract image data from callback object
            val data = buffer.toByteArray()

            // Convert the data into an array of pixel values ranging 0-255
            val pixels = data.map { it.toInt() and 0xFF }

            // Compute average luminance for the image
            val luma = pixels.average()

            // Call all listeners with new value
            listeners.forEach { it(luma) }

            image.close()
        }
    }

    /**
     *  [androidx.camera.core.ImageAnalysisConfig] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun setGalleryThumbnail(uri: Uri) {
        // Reference of the view that holds the gallery thumbnail
        val thumbnail = binding.photoViewButton

        // Run the operations in the view's thread
        thumbnail.post {

            // Remove thumbnail padding
            //thumbnail.setPadding(resources.getDimension(R.dimen.stroke_small).toInt())

            // Load thumbnail into circular button using Glide
            Glide.with(thumbnail)
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(thumbnail)
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, "ejcamerax").apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    //declaracion de constantes
    companion object {

        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10

        //Crear el nombre del archivo
        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, java.text.SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )
    }


}*/
