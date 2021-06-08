package me.javatar

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.displee.cache.index.archive.Archive

object OsrsCacheChecker {

    @JvmStatic
    fun main(args: Array<String>) {

        val latest_osrs = CacheLibrary.create("~/Downloads/original cache/Cache")
        val custom_osrs = CacheLibrary.create("~/Downloads/latest/cache")

        val config = latest_osrs.index(2)
        config.archiveIds().forEach {
            val customConfig = custom_osrs.index(2)
            val customArchive = customConfig.archive(it)
            val latestArchive = config.archive(it)
            if (customArchive != null && latestArchive != null) {
                copyMissingFiles(latestArchive, customArchive)
            }
        }

        latest_osrs.indices().forEach {
            if(it.id != 2 && it.id != 5 && it.id != 3 && custom_osrs.exists(it.id)) {
                val customIndex = custom_osrs.index(it.id)
                copyMissingArchives(it, customIndex)
            }
        }

        replaceIndexes(latest_osrs, custom_osrs)

        custom_osrs.update()

    }

    private fun replaceIndexes(latest_osrs: CacheLibrary, custom_osrs: CacheLibrary) {
        custom_osrs.index(5).add(*latest_osrs.index(5).copyArchives())
        //copyInterfaces(latest_osrs, custom_osrs)
        custom_osrs.index(12).add(*latest_osrs.index(12).copyArchives())
    }

    private fun copyInterfaces(latest_osrs: CacheLibrary, custom_osrs: CacheLibrary) {
        val latest = latest_osrs.index(3).also { it.cache() }
        val custom = custom_osrs.index(3).also { it.cache() }

        latest.archives().forEach {
            val a = custom.archive(it.id)
            if (a != null) {
                it.files().forEach { lat ->
                    if(lat.data != null) {
                        a.add(lat)
                    }
                }
            } else {
                custom.add(it)
            }
        }

    }

    private fun copyMissingFiles(latest: Archive, custom: Archive) {
        custom.add(*latest.copyFiles(), overwrite = false)
    }

    private fun copyMissingArchives(latest: Index, custom: Index) {
        custom.add(*latest.copyArchives(), overwrite = false)
    }

}
