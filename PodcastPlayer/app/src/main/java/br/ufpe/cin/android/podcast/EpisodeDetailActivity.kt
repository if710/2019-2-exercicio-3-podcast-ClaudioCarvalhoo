package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_episode_detail.*

class EpisodeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        val title = intent.getStringExtra("title")
        val link = intent.getStringExtra("link")
        val pubDate = intent.getStringExtra("pubDate")
        val description = intent.getStringExtra("description")
        val downloadLink = intent.getStringExtra("downloadLink")
        episodeTitle.text = title
        episodeLink.text = link
        episodePubDate.text = pubDate
        episodeDescription.text = description
        episodeDownloadLink.text = downloadLink
    }
}
