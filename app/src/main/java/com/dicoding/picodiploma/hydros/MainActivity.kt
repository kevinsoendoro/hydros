package com.dicoding.picodiploma.hydros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.dicoding.picodiploma.hydros.databinding.ActivityMainBinding
import com.dicoding.picodiploma.hydros.view.analysis.AnalysisActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    if (it.itemId == binding.navView.checkedItem?.itemId) {
                        Toast.makeText(
                            applicationContext,
                            "you are already in home",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val moveIntent = Intent(this@MainActivity, MainActivity::class.java)
                        moveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(moveIntent)
                    }
                }

                R.id.nav_analysis -> {
                    val moveIntent = Intent(this@MainActivity, AnalysisActivity::class.java)
                    startActivity(moveIntent)
                }

                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    "Clicked settings",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_logout -> Toast.makeText(
                    applicationContext,
                    "Clicked logout",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND);
                    shareIntent.type = "text/plain";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Please reach me at www.linkedin.com/in/nitamayega/");
                    startActivity(Intent.createChooser(shareIntent, "Share Link"))
                }

                R.id.nav_rate -> Toast.makeText(
                    applicationContext,
                    "Clicked rate",
                    Toast.LENGTH_SHORT
                ).show()
        }
        true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}