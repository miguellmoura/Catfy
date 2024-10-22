//package com.example.catify
//
//import android.app.ActivityOptions
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.appcompat.view.SupportActionModeWrapper
//import androidx.databinding.DataBindingUtil
//import com.example.catify.databinding.ActivitySignInBinding
//import java.math.BigInteger
//import java.security.MessageDigest
//import com.example.catify.R
//
//class SignInActivity : AppCompatActivity() {
//
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var viewModel: MainViewModel
//
//    fun String.md5(): String {
//        val md = MessageDigest.getInstance("MD5")
//        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        Singleton.initialize(UserDatabase.getInstance(applicationContext)?.UserDAO()!!)
//
//        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
//
//        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
//        AppCompatDelegate.setDefaultNightMode(
//            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
//        )
//
//        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
//        viewModel = MainViewModelFactory().create(MainViewModel::class.java)
//
//        viewModel.userList.observe(this) {
//            binding.notifyChange()
//        }
//
//        val container = binding.container
//        val backgroundColor = if (isDarkMode) {
//            getColor(R.color.primaryDarkColor)
//        } else {
//            getColor(R.color.bg_color)
//        }
//
//
//        val textColor = if (isDarkMode) {
//            getColor(R.color.white)
//        } else {
//            getColor(R.color.black)
//        }
//
//        container.setBackgroundColor(backgroundColor)
//
//        binding.themeSwitch.isChecked = isDarkMode
//        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
//            val editor = sharedPreferences.edit()
//            editor.putBoolean("dark_mode", isChecked)
//            editor.apply()
//
//            AppCompatDelegate.setDefaultNightMode(
//                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
//            )
//
//            val newBackgroundColor = if (isChecked) {
//                getColor(R.color.primaryDarkColor)
//            } else {
//                getColor(R.color.bg_color)
//            }
//            container.setBackgroundColor(newBackgroundColor)
//
//            val textColor = if (isChecked) {
//                getColor(R.color.white)
//            } else {
//                getColor(R.color.black)
//            }
//            binding.nickname.setTextColor(textColor)
//            binding.email.setTextColor(textColor)
//            binding.password.setTextColor(textColor)
//            binding.confirmPassword.setTextColor(textColor)
//            binding.contajaexiste.setTextColor(textColor)
//            binding.signIn.setTextColor(textColor)
//
//        }
//
//        binding.contajaexiste.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.signIn.setOnClickListener {
//            Log.d("SignInActivity", "Botão de cadastro clicado")
//
//            val username = binding.nickname.text.toString()
//            val email = binding.email.text.toString()
//            val password = binding.password.text.toString().md5()
//            val confirmPassword = binding.confirmPassword.text.toString().md5()
//
//            if (password != confirmPassword) {
//                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
//            } else if (username.isNotEmpty() && email.isNotEmpty()) {
//                val user = User(null, email, username, password)
//                Singleton.addUser(user)
//
//                Log.d("usuarios adicionados", Singleton.userList[0].nickname)
//
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}

package com.example.catify

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.catify.databinding.ActivitySignInBinding
import java.math.BigInteger
import java.security.MessageDigest

class SignInActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: MainViewModel

    // Função de extensão para MD5
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Singleton.initialize(UserDatabase.getInstance(applicationContext)?.UserDAO()!!)

        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Verifica se o modo escuro está ativado
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Inflate o layout com DataBinding
        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = MainViewModelFactory().create(MainViewModel::class.java)

        // Observa a lista de usuários
        viewModel.userList.observe(this) {
            binding.notifyChange()
        }

        val container = binding.container
        // Define as cores de fundo e texto de acordo com o modo
        updateTheme(container, binding, isDarkMode)

        binding.themeSwitch.isChecked = isDarkMode
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Atualiza preferências e o tema
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )

            // Atualiza cores com base no novo estado do modo escuro
            updateTheme(container, binding, isChecked)
        }

        binding.contajaexiste.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signIn.setOnClickListener {
            Log.d("SignInActivity", "Botão de cadastro clicado")

            val username = binding.nickname.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString().md5()
            val confirmPassword = binding.confirmPassword.text.toString().md5()

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else if (username.isNotEmpty() && email.isNotEmpty()) {
                val user = User(null, email, username, password)
                Singleton.addUser(user)

                Log.d("usuarios adicionados", Singleton.userList[0].nickname)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para atualizar o tema
    private fun updateTheme(container: View, binding: ActivitySignInBinding, isDarkMode: Boolean) {
        val backgroundColor = if (isDarkMode) {
            getColor(R.color.primaryDarkColor)
        } else {
            getColor(R.color.bg_color)
        }

        val textColor = if (isDarkMode) {
            getColor(R.color.white)
        } else {
            getColor(R.color.black)
        }

        container.setBackgroundColor(backgroundColor)
        binding.nickname.setTextColor(textColor)
        binding.email.setTextColor(textColor)
        binding.password.setTextColor(textColor)
        binding.confirmPassword.setTextColor(textColor)
    }
}
