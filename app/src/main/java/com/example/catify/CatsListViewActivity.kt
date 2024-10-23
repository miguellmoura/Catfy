package com.example.catify

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catify.Singleton.catsList
import com.example.catify.databinding.ActivityCatsListViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatsListViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatsListViewBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val api = retrofit.create(CatBreedAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatsListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Verifica se o modo escuro está ativado
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        val container = binding.container

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

        viewModel = MainViewModelFactory().create(MainViewModel::class.java)

        viewModel.favCatsList.observe(this) {
            Log.d("FAVORITOS", it.toString())
        }

        CoroutineScope(Dispatchers.IO).launch {
            val cats = api.getCat(100, "live_TcefF8M5RHzpZv9FkAFTSLudUjrvuDZmqIn48InB092e11xK1zETx9k41iWx1WA5", 1)

            cats.forEach { cat ->
                val catFinal = Cat(
                    cat.id,
                    cat.breeds[0].name,
                    cat.url,
                    cat.breeds[0].description,
                    cat.breeds[0].life_span,
                    cat.breeds[0].adaptability,
                    cat.breeds[0].affection_level,
                    cat.breeds[0].energy_level,
                    cat.breeds[0].health_issues,
                    cat.breeds[0].intelligence
                )
                catsList.add(catFinal)
            }

            withContext(Dispatchers.Main) {
                binding.recyclerView.adapter = CatAdapter(object : CatAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@CatsListViewActivity, CatInfoActivity::class.java)
                        intent.putExtra("position", position)
                        startActivity(intent)

                        Log.d("TAG", "onItemClick: $position")
                    }

                    override fun onItemLongClick(view: View, position: Int) {
                        val gatoFavorito = FavCats(
                            idFav = null,
                            catsList[position].id,
                            Singleton.getCurrentUser()?.id ?: return,
                            catsList[position].url,
                            catsList[position].name,
                            catsList[position].description,
                            catsList[position].life_span,
                            catsList[position].adaptability,
                            catsList[position].affection_level,
                            catsList[position].energy_level,
                            catsList[position].health_issues,
                            catsList[position].intelligence
                        )

                        Singleton.addFavCat(gatoFavorito)

                        CoroutineScope(Dispatchers.IO).launch {
                            Singleton.getFavCats(Singleton.getCurrentUser()?.id ?: return@launch).forEach {
                                withContext(Dispatchers.Main) {
                                    Log.d("gato favoritoooo", "onItemLongClick: ${it.name}")
                                }
                            }
                        }
                        Toast.makeText(this@CatsListViewActivity, "Gato favoritado", Toast.LENGTH_SHORT).show()
                    }
                })
                binding.recyclerView.addItemDecoration(DividerItemDecoration(this@CatsListViewActivity, DividerItemDecoration.VERTICAL))
                binding.recyclerView.addItemDecoration(DividerItemDecoration(this@CatsListViewActivity, DividerItemDecoration.HORIZONTAL))
                binding.recyclerView.layoutManager = GridLayoutManager(this@CatsListViewActivity, 2)

                binding.userIcon.setOnClickListener {
                    val intent = Intent(this@CatsListViewActivity, FavCatsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateTheme(container: View, binding: ActivityCatsListViewBinding, isDarkMode: Boolean) {
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

        val backgroundTextColor = if (isDarkMode) {
            getDrawable(R.drawable.rounded_border_black)

        } else {
            getDrawable(R.drawable.rounded_border)
        }

        if (isDarkMode) {
            binding.border.setBackgroundColor(getColor(R.color.black))
        } else {
            binding.border.setBackgroundColor(getColor(R.color.bg_color))
        }

        container.setBackgroundColor(backgroundColor)
        binding.themeSwitch.setTextColor(textColor)
        binding.recyclerView.setBackgroundColor(backgroundColor)
        binding.centerText.background = backgroundTextColor
        binding.centerText.setTextColor(textColor)
        binding.firstText.setTextColor(textColor)
    }
}
