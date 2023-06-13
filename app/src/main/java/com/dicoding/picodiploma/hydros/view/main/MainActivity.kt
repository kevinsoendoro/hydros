package com.dicoding.picodiploma.hydros.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.ImageView
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
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.ViewModelFactory
import com.dicoding.picodiploma.hydros.databinding.ActivityMainBinding
import com.dicoding.picodiploma.hydros.model.UserModel
import com.dicoding.picodiploma.hydros.preferences.SettingPreferences
import com.dicoding.picodiploma.hydros.view.analysis.AnalysisActivity
import com.dicoding.picodiploma.hydros.view.login.LoginActivity
import com.dicoding.picodiploma.hydros.view.welcome.Onboarding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var switchTheme: SwitchMaterial
    private val sharedPreferences by lazy { getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val pref: SettingPreferences by lazy {
        SettingPreferences.getInstance(application.dataStore)
    }
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupDrawerLayout()
        setupNavigationMenu()
        setupSwitchTheme()

        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            val intent = Intent(this@MainActivity, Onboarding::class.java)
            startActivity(intent)
            finish()
            return
        } else {
            setupNavigationHeader()
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

    private fun setupNavigationHeader() {
        val navView: NavigationView = binding.navView
        val header = navView.getHeaderView(0)
        val uname = header.findViewById<TextView>(R.id.username)
        val email = header.findViewById<TextView>(R.id.email)
        val photo = header.findViewById<ImageView>(R.id.profile_image)

        val userAvatar = sharedPreferences.getInt("userAvatar", R.drawable.ic_user)
        email.text = auth.currentUser?.email
        photo.setImageResource(userAvatar)

        photo.setOnClickListener {
            val newAvatar = when (Random().nextInt(8)) {
                0 -> R.drawable.ava
                1 -> R.drawable.ava_coder
                2 -> R.drawable.ava_girl
                3 -> R.drawable.ava_oldlady
                4 -> R.drawable.ava_oldman
                5 -> R.drawable.ava_professor
                6 -> R.drawable.ava_teacher
                else -> R.drawable.ic_user
            }
            photo.setImageResource(newAvatar)

            val editor = sharedPreferences.edit().putInt("userAvatar", newAvatar)
            editor.apply()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser?.email?.replace(".", "_") ?: "")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                val displayName = user?.name
                uname.text = displayName
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
