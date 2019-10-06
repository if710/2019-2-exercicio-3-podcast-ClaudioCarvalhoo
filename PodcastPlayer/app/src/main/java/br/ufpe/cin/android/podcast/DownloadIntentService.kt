package br.ufpe.cin.android.podcast

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log

class DownloadIntentService : IntentService("DownloadIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        val downloadLink = intent?.getStringExtra("url")!!
        val title = intent.getStringExtra("title")!!
        val request = DownloadManager.Request(Uri.parse(downloadLink))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Downloading episode")
        request.setDescription(title)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title+".mp3")
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val id = manager.enqueue(request)
        Log.d("service", id.toString())
    }
}