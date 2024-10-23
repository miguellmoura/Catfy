package com.example.catify

import FavCatsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catify.databinding.ActivityFavCatsBinding
import kotlinx.coroutines.launch

class FavCatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavCatsBinding
    private lateinit var adapter: FavCatsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o viewModel
        viewModel = MainViewModelFactory().create(MainViewModel::class.java)

        // Observa a lista de gatos favoritos
        viewModel.favCatsList.observe(this) { favoriteCats ->
            Log.d("FAVORITOS", favoriteCats.toString())

            // Configura o adapter e o comportamento de clique longo
            adapter = FavCatsAdapter(favoriteCats, object : FavCatsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    // Clique curto ainda abre a tela de detalhes
                    Log.d("CLICK", "Clicou no item $position")
                    val intent = Intent(this@FavCatsActivity, CatInfoActivity::class.java)
                    intent.putExtra("catId", favoriteCats[position].id)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, position: Int) {
                    // Clique longo remove o gato da lista de favoritos
                    Log.d("LONG CLICK", "Clicou e segurou no item $position")

                    // Remove o gato da lista de favoritos
                    viewModel.deleteFavCat(position)

                    // A UI será atualizada automaticamente devido ao LiveData
                }
            })

            // Configura o RecyclerView com o layout em grade
            binding.recyclerView.layoutManager = GridLayoutManager(this@FavCatsActivity, 2)
            binding.recyclerView.adapter = adapter
        }

        // Carrega os favoritos do usuário logado
        lifecycleScope.launch {
            val userId = Singleton.getCurrentUser().id!!
            viewModel.fetchFavCats(userId)
        }

        // Configura o botão de voltar
        binding.iconArrow.setOnClickListener {
            val intent = Intent(this, CatsListViewActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}
