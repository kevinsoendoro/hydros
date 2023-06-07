package com.dicoding.picodiploma.hydros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.hydros.databinding.ActivityMainBinding
import com.dicoding.picodiploma.hydros.preferences.UserPreference
import com.dicoding.picodiploma.hydros.view.analysis.AnalysisActivity
import com.dicoding.picodiploma.hydros.view.welcome.Onboarding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val userPreference by lazy { UserPreference.getInstance(dataStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

                R.id.nav_logout -> {
                    lifecycleScope.launch {
                        userPreference.logout()
                        val intent = Intent(this@MainActivity, Onboarding::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                R.id.nav_language -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                }

                R.id.nav_dark_mode -> Toast.makeText(
                    applicationContext,
                    "Clicked settings",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
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