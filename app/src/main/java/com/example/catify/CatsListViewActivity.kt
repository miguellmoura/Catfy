package com.example.catify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catify.Singleton.catsList
import com.example.catify.databinding.ActivityCatsListViewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

            CoroutineScope(Dispatchers.IO).launch {
                val cat = api.getCat(100, "live_TcefF8M5RHzpZv9FkAFTSLudUjrvuDZmqIn48InB092e11xK1zETx9k41iWx1WA5", 1)

                for (cat in cat) {
                    catsList.add(cat)
                }

            }




        binding = DataBindingUtil.setContentView<ActivityCatsListViewBinding>(this, R.layout.activity_main)
        binding.recyclerView.adapter = CatAdapter()
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}
