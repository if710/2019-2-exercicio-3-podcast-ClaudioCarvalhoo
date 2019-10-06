package br.ufpe.cin.android.podcast

data class ItemFeed(val title: String, val link: String, val pubDate: String, val description: String, val downloadLink: String, val fileLocation: String) {

    override fun toString(): String {
        return title
    }
}
