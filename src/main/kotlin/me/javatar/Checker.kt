package me.javatar

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.VarbitLoader

/**
 * Comment this class out if you don't have dep
 */

object Checker {

    @JvmStatic
    fun main(args: Array<String>) {

        val latest_osrs = CacheLibrary.create("/home/javatar/IdeaProjects/official_rsps/norse-cache")

        val archive = latest_osrs.index(2).archive(14)

        val varbit = archive?.file(1327)

        val varbits = VarbitLoader()
        val def = varbits.load(1327, varbit?.data)

        println("Varbit ${varbit?.id}")
        println("Least Significant Bit - ${def.leastSignificantBit}")
        println("Most Significant Bit - ${def.mostSignificantBit}")
        println("Index ${def.index}")
        println("Varbit count: ${archive?.fileIds()?.size}")

    }

}