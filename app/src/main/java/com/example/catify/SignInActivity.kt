package com.example.catify

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.catify.databinding.ActivitySignInBinding
import java.math.BigInteger
import java.security.MessageDigest
import android.view.animation.AnimationUtils
import com.example.catify.R

class SignInActivity : AppCompatActivity() {

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Singleton.setContextUser(this)

        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        var isAuthorizedToSignIn: Boolean = false

        binding.contajaexiste.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.signIn.startAnimation(fadeInAnimation)

        binding.signIn.setOnClickListener {
            Log.d("SignInActivity", "Botão de cadastro clicado")

            val username = binding.nickname.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString().md5()
            val confirmPassword = binding.confirmPassword.text.toString().md5()

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                isAuthorizedToSignIn = false
            }
            if (username.isNotEmpty() && password.isNotEmpty() && !isAuthorizedToSignIn) {
                val user = User(null, email, username, password)

                Singleton.addUser(user)

                Log.d("usuarios adicionados", Singleton.userList[0].nickname)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                isAuthorizedToSignIn = false
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}