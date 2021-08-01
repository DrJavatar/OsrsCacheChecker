package me.javatar

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.MapLoader
import com.javatar.osrs.definitions.loaders.ObjectLoader

object CacheConfigIntegrityCheck {

    @JvmStatic
    fun main(args: Array<String>) {

        val cache = CacheLibrary.create("/home/javatar/Downloads/Niels_Cache/Cache")

        objectCheck(cache)

        //mapCheck(cache)

    }

    private fun objectCheck(cache: CacheLibrary) {
        val loader = ObjectLoader()

        val configs = cache.index(2)
        configs.cache()

        val archive = configs.archive(6)

        val fileIds = archive?.fileIds() ?: intArrayOf()

        for (fileId in fileIds) {
            val data = cache.data(2, 6, fileId)
            if (data != null) {
                loader.load(fileId, data)
            } else {
                println("Object Null $fileId")
            }
        }
    }

    private fun mapCheck(cache: CacheLibrary) {

        val maps = cache.index(5)
        maps.cache()

        val regionID = 8523

        val regionX = regionID shr 8
        val regionY = regionID and 255

        val objects = maps.archive("l${regionX}_${regionY}", intArrayOf(0, 0, 0, 0))
        //val floors = maps.archive("m${regionX}_${regionY}")

        val loader = MapLoader()

        val data = objects?.file(0)?.data
        if (data != null) {
            loader.load(regionX, regionY, data)
        }

    }

}