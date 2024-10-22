import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catify.CatAdapter
import com.example.catify.FavCats
import com.example.catify.databinding.ItemViewBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class FavCatsAdapter(private val cats: List<FavCats>, val listener: FavCatsAdapter.OnItemClickListener ) : RecyclerView.Adapter<FavCatsAdapter.CatViewHolder>() {

    class CatViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        // Usando o View Binding para inflar o layout do item
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        // Ligando o dado à view usando o binding
        holder.binding.nomeGatinhozinho.text = cat.name
        Picasso.get()
            .load(cat.url)
            .resize(300, 200)
            .centerCrop()
            .transform(RoundedCornersTransformation(14, 0))
            .into(holder.binding.fotoGatinhozinho)

//        holder.binding.root.setOnClickListener {
//            listener.onItemClick(holder.binding.root, adapterPosition)
//        }
//
//        holder.binding.root.setOnLongClickListener {
//            listener.onItemLongClick(holder.binding.root, adapterPosition)
//            true
//        }
    }

    override fun getItemCount(): Int {
        return cats.size
    }
}
