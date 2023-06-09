package com.dicoding.picodiploma.hydros.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.ViewModelFactory
import com.dicoding.picodiploma.hydros.databinding.ActivityMainBinding
import com.dicoding.picodiploma.hydros.preferences.SettingPreferences
import com.dicoding.picodiploma.hydros.preferences.UserPreference
import com.dicoding.picodiploma.hydros.view.analysis.AnalysisActivity
import com.dicoding.picodiploma.hydros.view.login.LoginActivity
import com.dicoding.picodiploma.hydros.view.welcome.Onboarding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var switchTheme: SwitchMaterial
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val userPreference by lazy { UserPreference.getInstance(dataStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupDrawerLayout()
        setupNavigationMenu()
        setupSwitchTheme()

        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        } else {
            val navView: NavigationView = binding.navView
            val header = navView.getHeaderView(0)
            val uname = header.findViewById<TextView>(R.id.username)
            val email = header.findViewById<TextView>(R.id.email)

            email.text = firebaseUser.email
        }
    }

    private fun setupDrawerLayout() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (menuItem.itemId == binding.navView.checkedItem?.itemId) {
                        Toast.makeText(applicationContext, "You are already on the home screen", Toast.LENGTH_SHORT).show()
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
                    auth.signOut()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
                R.id.nav_language -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                }
                R.id.nav_dark_mode -> Toast.makeText(
                    applicationContext,
                    "Clicked settings",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Please reach me at www.linkedin.com/in/nitamayega/")
                    startActivity(Intent.createChooser(shareIntent, "Share Link"))
                }
                R.id.nav_rate -> Toast.makeText(
                    applicationContext,
                    "Clicked rate",
                    Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun setupSwitchTheme() {
        val navView: NavigationView = binding.navView
        val menu = navView.menu
        val darkModeMenuItem = menu.findItem(R.id.nav_dark_mode)
        val actionView = darkModeMenuItem.actionView
        if (actionView != null) {
            switchTheme = actionView.findViewById(R.id.switch_theme)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}