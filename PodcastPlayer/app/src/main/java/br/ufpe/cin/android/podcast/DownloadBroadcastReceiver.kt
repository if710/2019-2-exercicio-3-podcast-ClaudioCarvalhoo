package br.ufpe.cin.android.podcast

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.ufpe.cin.android.podcast.database.ItemDatabase
import org.jetbrains.anko.doAsync

class DownloadBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action){
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.d("receiver", id.toString())
            doAsync {
                val manager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val query = DownloadManager.Query()
                query.setFilterById(id)
                val cursor = manager.query(query)
                if (cursor.moveToFirst()) {
                    val localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                    val db = ItemDatabase.getInstance(context)
                    val item = db!!.itemDAO().getByTitle(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION)))
                    item.fileLocation = localUri
                    db.itemDAO().insert(item)
                }
            }
        }
    }
}