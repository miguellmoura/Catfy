package com.example.catify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.catify.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        val username = intent.getStringExtra("username")
        binding.name.text = username
        binding.welcome.text = Singleton.texto
        binding.contajaexiste.setOnClickListener {
            val intent = Intent()
            intent.putExtra("result", "result from secondActivity")
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}