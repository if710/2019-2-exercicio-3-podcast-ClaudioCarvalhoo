package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*

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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.item_title
        val button = itemView.item_action
        val date = itemView.item_date
    }

}