package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.database.ItemDatabase
import kotlinx.android.synthetic.main.itemlista.view.*
import org.jetbrains.anko.doAsync

class CustomAdapter(private val items: List<ItemFeed>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.date.text = item.pubDate
        holder.title.setOnClickListener {
            val intent = Intent(context, EpisodeDetailActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("link", item.link)
            intent.putExtra("pubDate", item.pubDate)
            intent.putExtra("description", item.description)
            intent.putExtra("downloadLink", item.downloadLink)
            startActivity(context, intent, null)
        }
        holder.button.setOnClickListener{
            val intent = Intent(this.context, DownloadIntentService::class.java)
            intent.putExtra("url", item.downloadLink)
            intent.putExtra("title", item.title)
            this.context.startService(intent)
        }

        holder.playButton.setOnClickListener{
            doAsync {
                val db = ItemDatabase.getInstance(context)
                val location = db!!.itemDAO().getByTitle(item.title).fileLocation
                if (location != "") {
                    val player = MediaPlayer.create(context, Uri.parse(location))
                    player.start()
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.item_title
        val button = itemView.item_action
        val date = itemView.item_date
        val playButton = itemView.play_button
    }

}