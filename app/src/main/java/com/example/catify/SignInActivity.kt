package com.example.catify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.catify.databinding.ActivityLoginBinding

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_sign_in)
        val username = intent.getStringExtra("username")
        binding.name.text = username
        binding.welcome.text = Singleton.texto

        binding.contajaexiste.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signIn.setOnClickListener {

            // IMPLEMENTAR AQUI O CADASTRO DO USUÁRIO NO BANCO DE DADOS E VERIFICACOES DE INPUTS NAO PREENCHIDOS
            
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}