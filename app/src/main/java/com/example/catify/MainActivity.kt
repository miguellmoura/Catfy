//package com.example.catify
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.databinding.DataBindingUtil
//import com.example.catify.databinding.ActivityMainBinding
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class MainActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.thecatapi.com/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//        val api = retrofit.create(CatBreedAPI::class.java)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == RESULT_OK) {
//                val data = it.data
//                val result = data?.getStringExtra("result")
//                binding.button.text = result
//            }
//        }
//
//        binding.button.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.putExtra("username", "MEGUELZINHO")
//            Singleton.texto = "not ok"
//            resultLauncher.launch(intent)
//        }
//
//        /*binding.getAPI.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val cat = api.getCat(100, "live_TcefF8M5RHzpZv9FkAFTSLudUjrvuDZmqIn48InB092e11xK1zETx9k41iWx1WA5", 1)
//                runOnUiThread{
//                    binding.catInfo.text = cat[0].breeds[0].name
//                }
//            }
//        }*/
//
//    }
//}
