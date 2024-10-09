package com.example.catify

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
        binding.lifeExpectations.text = selectedCat.life_span
        binding.adaptability.text = selectedCat.adaptability.toString()
        binding.affectionLevel.text = selectedCat.affection_level.toString()
        binding.energyLevel.text = selectedCat.energy_level.toString()
        binding.healthProblems.text = selectedCat.health_issues.toString()


        Picasso.get()
            .load(selectedCat.url)
            .resize(300, 200)
            .centerCrop()
            .transform(RoundedCornersTransformation(14, 0))
            .into(binding.gatinhoIcon)

    }
}