package me.javatar

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader

object CacheUpdater {

    data class Xtea(val mapsquare: Int, val key: IntArray)

    @JvmStatic
    fun main(args: Array<String>) {

        val gson = Gson()
        val file = File("/home/javatar/Downloads/latest/xteas.json")
        val xteas = if(!file.exists()) {
            mutableMapOf()
        } else {
            gson.fromJson<List<Xtea>>(FileReader(file), object : TypeToken<List<Xtea>>(){}.type).associateBy { it.mapsquare }
        }

        println(xteas.size)

        val latest_osrs = CacheLibrary.create("/home/javatar/Downloads/latest/cache")
        val custom_osrs = CacheLibrary.create("/home/javatar/Downloads/customCache/Cache")
        custom_osrs.indices().forEach {
            val latestIndex = latest_osrs.index(it.id)
            latestIndex.cache()
            try {
                when(it.id) {
                    0, 1, 2, 7, 8, 9 -> {
                        it.add(*latestIndex.copyArchives())
                    }
                    /*2 -> {
                        it.replaceConfigs(latestIndex, 10, 12, 9, 13, 6)
                    }*/
                    5 -> {
                        custom_osrs.replaceMaps(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@forEach
            } finally {
                latestIndex.clear()
                System.gc()
            }
        }
        custom_osrs.update()
        println("Finished Copying Assets.")

        /*custom_osrs.replaceIndex(latest_osrs.index(0))
        custom_osrs.replaceIndex(latest_osrs.index(1))
        custom_osrs.replaceConfigs(latest_osrs.index(2))
        //custom_osrs.replaceIndex(latest_osrs.index(3))
        //custom_osrs.replaceIndex(latest_osrs.index(4))
        custom_osrs.replaceMaps(latest_osrs.index(5))
        //custom_osrs.replaceIndex(latest_osrs.index(6))
        custom_osrs.replaceIndex(latest_osrs.index(7))
        custom_osrs.replaceIndex(latest_osrs.index(8))
        custom_osrs.replaceIndex(latest_osrs.index(9))
        *//*custom_osrs.replaceIndex(latest_osrs.index(10))
        custom_osrs.replaceIndex(latest_osrs.index(11))*//*
        //custom_osrs.replaceIndex(latest_osrs.index(12))
        *//*custom_osrs.replaceIndex(latest_osrs.index(13))
        custom_osrs.replaceIndex(latest_osrs.index(14))
        custom_osrs.replaceIndex(latest_osrs.index(15))
        custom_osrs.replaceIndex(latest_osrs.index(16))
        custom_osrs.replaceIndex(latest_osrs.index(17))
        custom_osrs.replaceIndex(latest_osrs.index(18))
        custom_osrs.replaceIndex(latest_osrs.index(19))
        custom_osrs.replaceIndex(latest_osrs.index(20))*/

    }

    private fun CacheLibrary.replaceMaps(latestMaps: Index, xteas: Map<Int, Xtea> = mutableMapOf()) {
        for (entry in xteas) {
            val regionId = entry.key
            val xtea = entry.value
            val regionX = regionId shr 8
            val regionY = regionId and 255
            val lfile = "l${regionX}_${regionY}"
            val mfile = "m${regionX}_${regionY}"
            val larchive = latestMaps.archive(lfile, xtea.key)
            val marchive = latestMaps.archive(mfile)
            if(larchive != null && marchive != null) {
                index(5).add(larchive, xtea = xtea.key)
                index(5).add(marchive)
            }
        }
    }

    private fun Index.replaceConfigs(latestConfigs: Index, vararg archiveIds: Int) {
        for (latArchiveId in latestConfigs.archiveIds()) {
            for (archiveId in archiveIds) {
                if(latArchiveId == archiveId) {
                    add(latestConfigs.archive(latArchiveId))
                }
            }
        }
    }

    private fun CacheLibrary.replaceConfigs(latestConfigs: Index, everything: Boolean = false) {
        if(everything) {
            for (archiveId in latestConfigs.archiveIds()) {
                val latestArchive = latestConfigs.archive(archiveId)
                if(latestArchive != null) {
                    for (fileId in latestArchive.fileIds()) {
                        val latestFile = latestArchive.file(fileId)
                        if (latestFile?.data != null) {
                            put(2, latestArchive.id, latestFile.id, latestFile.data!!)
                        }
                    }
                }
            }
        } else {
            for (archiveId in latestConfigs.archiveIds()) {
                val latestArchive = latestConfigs.archive(archiveId)
                if(latestArchive != null) {
                    for (fileId in latestArchive.fileIds()) {
                        val latestFile = latestArchive.file(fileId)
                        if (latestFile?.data != null && index(2).archive(archiveId)?.contains(fileId) == false) {
                            put(2, latestArchive.id, latestFile.id, latestFile.data!!)
                        }
                    }
                }
            }
        }
    }

    private fun CacheLibrary.replaceIndex(with: Index) {
        val customIndex = index(with.id)
        for (archiveId in customIndex.archiveIds()) {
            val customArchive = customIndex.archive(archiveId)
            if(customArchive != null) {
                val latestArchive = with.archive(customArchive.id)
                if(latestArchive != null) {
                    for (fileId in latestArchive.fileIds()) {
                        val file = latestArchive.file(fileId)
                        if(file?.data != null) {
                            customArchive.add(file)
                        }
                    }
                }
            }
        }
    }

}