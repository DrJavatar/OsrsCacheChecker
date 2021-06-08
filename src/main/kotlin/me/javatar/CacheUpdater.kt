package me.javatar

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import java.lang.RuntimeException

object CacheUpdater {

    data class Xtea(val mapsquare: Int, val key: IntArray)

    @JvmStatic
    fun main(args: Array<String>) {

        /*val gson = Gson()
        val file = File("/home/javatar/Downloads/latest/xteas.json")
        val xteas = if(!file.exists()) {
            mutableMapOf()
        } else {
            gson.fromJson<List<Xtea>>(FileReader(file), object : TypeToken<List<Xtea>>(){}.type).associateBy { it.mapsquare }
        }

        println(xteas.size)*/

        val latest_osrs = CacheLibrary.create("/home/javatar/Downloads/latest/cache")
        val custom_osrs = CacheLibrary.create("/home/javatar/Downloads/customCache/Cache")

        latest_osrs.indices().forEach { it.cache() }


        custom_osrs.indices().forEach {
            try {
                it.cache()
                if(it.id == 5) {
                    custom_osrs.replaceMaps(it)
                } else {
                    it.add(*latest_osrs.index(it.id).copyArchives())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                it.clear()
                throw RuntimeException("Index ${it.info}")
            } finally {
                it.clear()
            }
        }

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

        custom_osrs.update()
    }

    private fun CacheLibrary.replaceMaps(latestMaps: Index, xteas: Map<Int, Xtea> = mutableMapOf()) {
        if(xteas.isEmpty()) {
            for (archiveId in latestMaps.archiveIds()) {
                val latestArchive = latestMaps.archive(archiveId)
                if(latestArchive != null) {
                    for (fileId in latestArchive.fileIds()) {
                        val file = latestArchive.file(fileId)
                        if(file?.data != null) {
                            put(5, archiveId, fileId, file.data!!)
                        }
                    }
                }
            }
        } else {
            TODO("Strip xteas to 0,0,0,0")
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