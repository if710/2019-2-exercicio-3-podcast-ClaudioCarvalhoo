package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.ufpe.cin.android.podcast.database.ItemDatabase
import br.ufpe.cin.android.podcast.database.ItemMapper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadXML()
    }

    fun downloadXML() {
        doAsync {
            val db = ItemDatabase.getInstance(this@MainActivity)
            try {
                val feed =
                    URL("https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml").readText()
                val itemFeeds = Parser.parse(feed)
                for (item in itemFeeds) {
                    db!!.itemDAO().insert(ItemMapper.toModel(item))
                }
            }catch (e: Exception){
                println("app is offline")
            }
            val itemsFromDb = db!!.itemDAO().getAll().map{itemModel -> ItemMapper.toDTO(itemModel)}

            uiThread {
                val recyclerView = recycler_view
                recyclerView.adapter = CustomAdapter(itemsFromDb, this@MainActivity)
                val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                recyclerView.layoutManager = layoutManager
            }
        }
    }
}
