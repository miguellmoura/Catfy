import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catify.MyItem
import com.example.catify.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val myItemList = listOf(
            MyItem.MyItem("Item 1", R.drawable.gatinho_icon),
            MyItem.MyItem("Item 2", R.drawable.gatinho_icon),
        )
    }
    }
