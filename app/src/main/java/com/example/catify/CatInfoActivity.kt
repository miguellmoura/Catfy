package com.example.catify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catify.databinding.ActivityCatInfoBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CatInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        var selectedCat: Cat


        if (Singleton.catsList.isNotEmpty() && position < Singleton.catsList.size) {
            selectedCat = Singleton.catsList[position]
        } else {
            return finish()
        }

        binding.catName.text = selectedCat.name
        binding.catDescription.text = selectedCat.description
        binding.lifeExpectations.text = " * Expectativa de vida:  " + selectedCat.life_span
        binding.adaptability.text = " * Adaptabilidade:  "+ selectedCat.adaptability.toString()
        binding.affectionLevel.text = " * Nível de amor:  " + selectedCat.affection_level.toString()
        binding.energyLevel.text = " * Nível de energia:  " + selectedCat.energy_level.toString()
        binding.healthProblems.text = " * Problemas de saúde:  " + selectedCat.health_issues.toString()

        binding.flechaIcon.setOnClickListener {
            val intent = Intent(this, CatsListViewActivity::class.java)
            startActivity(intent)
        }


        Picasso.get()
            .load(selectedCat.url)
            .resize(300, 200)
            .centerCrop()
            .transform(RoundedCornersTransformation(14, 0))
            .into(binding.gatinhoIcon)

    }
}