package ru.itis.kpfu.diyor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.itis.kpfu.diyor.databinding.ActivityMainBinding
import ru.itis.kpfu.diyor.Constants.PREF_LOGIN_STATUS

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: NavController
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        controller = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        val status = preferences.getBoolean(PREF_LOGIN_STATUS, false)
        if (status) {
            controller.navigate(R.id.action_signInFragment_to_profileFragment)
        }
    }
}