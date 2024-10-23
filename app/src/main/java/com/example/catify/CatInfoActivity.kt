package com.example.catify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.catify.databinding.ActivityCatInfoBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CatInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera o catId passado pela intent
        val catId = intent.getStringExtra("catId")

        if (catId == null) {
            Log.d("CatInfoActivity", "catId é nulo")
            Toast.makeText(this, "Erro: Cat ID não foi passado", Toast.LENGTH_SHORT).show() // Adiciona feedback visual
            finish() // Encerra a activity se o catId for nulo
            return
        }

        Log.d("CatInfoActivity", "catId recebido: $catId")

        // Encontra o gato baseado no catId
        var selectedCat: Cat? = null
        for (cat in Singleton.catsList) {
            if (cat.id == catId) {
                Log.d("CatInfoActivity", "Gato encontrado: ${cat.name}")
                selectedCat = cat
                break
            }
        }

        // Se o gato não for encontrado, encerra a activity
        if (selectedCat == null) {
            Log.d("CatInfoActivity", "Gato não encontrado")
            Toast.makeText(this, "Gato não encontrado", Toast.LENGTH_SHORT).show() // Adiciona feedback visual
            finish()
            return
        }

        // Atualiza a UI com os detalhes do gato encontrado
        selectedCat?.let { cat ->
            binding.catName.text = cat.name
            binding.catDescription.text = cat.description
            binding.lifeExpectations.text = " * Expectativa de vida:  ${cat.life_span}"
            binding.adaptability.text = " * Adaptabilidade:  ${cat.adaptability}"
            binding.affectionLevel.text = " * Nível de amor:  ${cat.affection_level}"
            binding.energyLevel.text = " * Nível de energia:  ${cat.energy_level}"
            binding.healthProblems.text = " * Problemas de saúde:  ${cat.health_issues}"

            // Carrega a imagem do gato com Picasso, usando um placeholder e tratamento de erro
            Picasso.get()
                .load(cat.url)
                .resize(300, 200)
                .centerCrop()
                .transform(RoundedCornersTransformation(14, 0))
                .into(binding.gatinhoIcon)
        }

        // Botão de voltar
        binding.flechaIcon.setOnClickListener {
            val intent = Intent(this, CatsListViewActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
