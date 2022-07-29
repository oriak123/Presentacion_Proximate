package com.example.presentacionproximate

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.presentacionproximate.databinding.ActivityDrawerLayoutBinding
import com.example.presentacionproximate.ui.adapter.ShowProducts
import com.example.presentacionproximate.ui.utils.Conocenos
import com.example.presentacionproximate.ui.utils.GlobalDatesApplication.Companion.prefs
import com.example.presentacionproximate.ui.views.LoginActivity
import com.example.presentacionproximate.ui.views.QuejasyReclamos
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home2.*

class Home : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityDrawerLayoutBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding =ActivityDrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        toolbar_main.setTitle("")
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
         navigationView.setNavigationItemSelectedListener(this)

        putGreeting()

    }


    private fun putGreeting() {
        val getNameUser = prefs.getGreeting()
        tvGreeting.text = "!BIENVENIDO A APP LEGEND ${getNameUser.toUpperCase()}!"
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_item_one -> {
                val intent1 = Intent(this, Home::class.java)
                startActivity(intent1)
            }

            R.id.nav_item_two -> {
                val intent2 = Intent(this, ShowProducts::class.java)

                startActivity(intent2)
            }

            R.id.nav_item_three -> {
                startActivity(Intent(this, QuejasyReclamos::class.java))
            }

            R.id.nav_item_four -> {
                startActivity(Intent(this, Conocenos::class.java))
            }

            R.id.nav_item_five -> {
                Log.d("ole", prefs.getRemember().toString())
                if (!prefs.getRemember()) {

                    prefs.saveUser("")
                    prefs.savePassw("")
                }

                if (prefs.getToken().isNotEmpty()) {
                    prefs.saveToken("")
                }
                val intent2 = Intent(this, LoginActivity::class.java)
                startActivity(intent2)
            }

        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
