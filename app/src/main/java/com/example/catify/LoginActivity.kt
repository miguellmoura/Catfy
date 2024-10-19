package com.example.catify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.catify.databinding.ActivityLoginBinding
import java.math.BigInteger
import java.security.MessageDigest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val daoUser = UserDatabase.getInstance(applicationContext)?.UserDAO()
        Singleton.initialize(daoUser = daoUser!!)

        val container = binding.container

        val backgroundColor = if (isDarkMode) {
            getColor(R.color.primaryDarkColor)
        } else {
            getColor(R.color.bg_color)
        }
        container.setBackgroundColor(backgroundColor)

        binding.themeSwitch.isChecked = isDarkMode
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )

            val newBackgroundColor = if (isChecked) {
                getColor(R.color.primaryDarkColor)
            } else {
                getColor(R.color.bg_color)
            }
            container.setBackgroundColor(newBackgroundColor)
        }

        binding.contajaexiste.setOnClickListener {
            Log.d("LoginActivity", "Botão de cadastro clicado")

            val email = binding.nickname.text.toString()
            val password = binding.password.text.toString().md5()

            Singleton.getUser(email)?.let {
                if (it.password == password) {
                    val intent = Intent(this, CatsListViewActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}