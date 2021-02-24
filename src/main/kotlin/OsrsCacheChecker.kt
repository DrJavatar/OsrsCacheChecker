import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.displee.cache.index.archive.Archive

object OsrsCacheChecker {

    @JvmStatic
    fun main(args: Array<String>) {

        val latest_osrs = CacheLibrary.create("./latest_osrs")
        val custom_osrs = CacheLibrary.create("./custom_osrs")

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
            if(it.id != 2 && custom_osrs.exists(it.id)) {
                val customIndex = custom_osrs.index(it.id)
                copyMissingArchives(it, customIndex)
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
