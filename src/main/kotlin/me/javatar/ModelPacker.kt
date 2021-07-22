package me.javatar

import com.displee.cache.CacheLibrary
import java.nio.file.Files
import java.nio.file.Path

object ModelPacker {

    @JvmStatic
    fun main(args: Array<String>) {

        val custom_cache = CacheLibrary.create("latest_cache/cache")

        val models = custom_cache.index(7)

        val modelData = Files.readAllBytes(Path.of("path/to/model/model.dat"))
        //Replace 1 with the model ID you want to replace
        val modelID = 1

        custom_cache.put(7, modelID, modelData)

        models.update()

    }

}