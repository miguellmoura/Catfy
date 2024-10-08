package com.example.catify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catify.Singleton.catsList
import com.example.catify.databinding.ActivityCatsListViewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CatsListViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityCatsListViewBinding

    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api = retrofit.create(CatBreedAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatsListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

            CoroutineScope(Dispatchers.IO).launch {
                val cats = api.getCat(100, "live_TcefF8M5RHzpZv9FkAFTSLudUjrvuDZmqIn48InB092e11xK1zETx9k41iWx1WA5", 1)

                for (cat in cats) {
                    val catFinal = Cat(cat.id, cat.breeds[0].name, cat.url, cat.breeds[0].description, cat.breeds[0].life_span, cat.breeds[0].adaptability, cat.breeds[0].affection_level, cat.breeds[0].energy_level, cat.breeds[0].health_issues, cat.breeds[0].intelligence)
                    catsList.add(catFinal)
                }

                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = CatAdapter(object: CatAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {

                            val intent = Intent(this@CatsListViewActivity, CatInfoActivity::class.java)
                            intent.putExtra("position", position)
                            startActivity(intent)

                            Log.d("TAG", "onItemClick: $position")
                        }
                        override fun onItemLongClick(view: View, position: Int) {


                        }
                    })
                    binding.recyclerView.addItemDecoration(DividerItemDecoration(this@CatsListViewActivity, DividerItemDecoration.VERTICAL))
                    binding.recyclerView.addItemDecoration(DividerItemDecoration(this@CatsListViewActivity, DividerItemDecoration.HORIZONTAL))
                    binding.recyclerView.layoutManager = GridLayoutManager(this@CatsListViewActivity, 2)
                }
            }



    }
}
