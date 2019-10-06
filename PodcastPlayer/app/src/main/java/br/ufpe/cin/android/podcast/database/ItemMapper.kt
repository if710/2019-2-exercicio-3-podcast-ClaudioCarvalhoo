package br.ufpe.cin.android.podcast.database

import br.ufpe.cin.android.podcast.ItemFeed

class ItemMapper {
    companion object {
        fun toModel(itemDTO: ItemFeed): ItemModel {
            return ItemModel(
                itemDTO.title,
                itemDTO.link,
                itemDTO.pubDate,
                itemDTO.description,
                itemDTO.downloadLink,
                itemDTO.fileLocation
            )
        }

        fun toDTO(itemModel: ItemModel): ItemFeed {
            return ItemFeed(
                itemModel.title,
                itemModel.link,
                itemModel.pubDate,
                itemModel.description,
                itemModel.downloadLink,
                itemModel.fileLocation
            )
        }
    }
}