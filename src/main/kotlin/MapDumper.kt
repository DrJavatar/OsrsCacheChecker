import com.displee.cache.CacheLibrary
import com.displee.cache.index.archive.Archive

object MapDumper {

    @JvmStatic
    fun main(args: Array<String>) {

        val path = ""
        val cache = CacheLibrary.create(path)
        val custom = CacheLibrary.create("/home/javatar/custom/cache")

        val a = cache.index(5).copyArchives()
        custom.index(5).add(
            archives = a,
            true
        )

        custom.index(5).update()

    }

}