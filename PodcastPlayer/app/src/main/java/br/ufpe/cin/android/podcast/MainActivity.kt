package br.ufpe.cin.android.podcast

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.ufpe.cin.android.podcast.database.ItemDatabase
import br.ufpe.cin.android.podcast.database.ItemMapper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val WRITE_STORAGE_PERMISSION_CODE: Int = 74
    private val READ_STORAGE_PERMISSION_CODE: Int = 74

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_STORAGE_PERMISSION_CODE
            )
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_CODE
            )
        }

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
                    if (db!!.itemDAO().existsByTitle(item.title) == 0L){
                        db.itemDAO().insert(ItemMapper.toModel(item))
                    }
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
