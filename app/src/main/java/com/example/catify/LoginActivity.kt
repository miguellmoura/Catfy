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
import android.view.animation.AnimationUtils

class LoginActivity : AppCompatActivity() {

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.contajaexiste.startAnimation(fadeInAnimation)

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
}