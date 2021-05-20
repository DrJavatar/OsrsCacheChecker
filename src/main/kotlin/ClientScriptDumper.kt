import com.displee.cache.CacheLibrary
import java.nio.file.Files
import java.nio.file.Path

object ClientScriptDumper {

    @JvmStatic
    fun main(args: Array<String>) {

        val latest = CacheLibrary.create("/home/javatar/Downloads/latest/cache")

        val scripts = latest.index(12)
        scripts.cache()

        for(id in scripts.archiveIds()) {
            val data = latest.data(12, id)
            if (data != null) {
                Files.write(Path.of("/home/javatar/IdeaProjects/cs2/cs2/osrs/$id"), data)
            }
        }

    }

}