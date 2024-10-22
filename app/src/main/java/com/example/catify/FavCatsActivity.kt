package com.example.catify
import FavCatsAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catify.Singleton
import com.example.catify.databinding.ActivityFavCatsBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch

class FavCatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavCatsBinding
    private lateinit var adapter: FavCatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o binding
        binding = ActivityFavCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            // Obtém os gatos favoritados para o usuário atual
            val favoriteCats = Singleton.getFavCats(Singleton.getCurrentUser().id!!)

            for (i in favoriteCats) {
                Log.d("GATO FAVORITO", i.name)
            }

            // Configura o RecyclerView
            adapter = FavCatsAdapter(favoriteCats, object : FavCatsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    TODO("Not yet implemented")
                }

                override fun onItemLongClick(view: View, position: Int) {
                    TODO("Not yet implemented")
                }
            })

            binding.recyclerView.layoutManager = GridLayoutManager(this@FavCatsActivity, 2)
            binding.recyclerView.adapter = adapter

        }

    }
}
