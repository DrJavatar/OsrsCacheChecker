package me.javatar

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.definitions.loaders.NpcLoader
import com.javatar.osrs.definitions.loaders.ObjectLoader
import java.nio.file.Files
import java.nio.file.Path

object DataDumper {

    @JvmStatic
    fun main(args: Array<String>) {

        val latest_osrs = CacheLibrary.create("/home/javatar/Downloads/latest/cache")

        latest_osrs.index(2).cache()
        dumpItems(latest_osrs)
        dumpNpcs(latest_osrs)
        dumpObjects(latest_osrs)

    }

    private fun dumpItems(cache: CacheLibrary) {
        val configs = cache.index(2)
        val items = configs.archive(10)
        val loader = ItemLoader()


        if (items != null) {
            val builder = StringBuilder()
            for (fileId in items.fileIds()) {
                val file = items.file(fileId)
                if(file?.data != null) {
                    val def = loader.load(fileId, file.data)
                    builder.append(fileId).append("\t").append(def.name.lowercase().replace(" ", "_"))
                        .append("\n")
                    if(def.unknownString != null) {
                        println("${def.id} - ${def.name} - ${def.unknownString}")
                    }
                }
            }

            Files.write(Path.of("/home/javatar/IdeaProjects/OsrsCacheChecker/data/items.tsv"), builder.toString().toByteArray())
        }

    }

    private fun dumpNpcs(cache: CacheLibrary) {
        val configs = cache.index(2)
        val items = configs.archive(9)
        val loader = NpcLoader()


        if (items != null) {
            val builder = StringBuilder()
            for (fileId in items.fileIds()) {
                val file = items.file(fileId)
                if(file?.data != null) {
                    val def = loader.load(fileId, file.data)
                    builder.append(fileId).append("\t").append(def.name.lowercase().replace(" ", "_"))
                        .append("\n")
                }
            }

            Files.write(Path.of("/home/javatar/IdeaProjects/OsrsCacheChecker/data/npcs.tsv"), builder.toString().toByteArray())
        }

    }

    private fun dumpObjects(cache: CacheLibrary) {
        val configs = cache.index(2)
        val items = configs.archive(6)
        val loader = ObjectLoader()


        if (items != null) {
            val builder = StringBuilder()
            for (fileId in items.fileIds()) {
                val file = items.file(fileId)
                if(file?.data != null) {
                    val def = loader.load(fileId, file.data)
                    builder.append(fileId).append("\t").append(def.name.lowercase().replace(" ", "_"))
                        .append("\n")
                }
            }

            Files.write(Path.of("/home/javatar/IdeaProjects/OsrsCacheChecker/data/objects.tsv"), builder.toString().toByteArray())
        }

    }

}