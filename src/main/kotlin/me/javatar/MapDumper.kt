package me.javatar

import com.displee.cache.CacheLibrary

object MapDumper {

    @JvmStatic
    fun main(args: Array<String>) {

        val path = ""
        val cache = CacheLibrary.create(path)
        val custom = CacheLibrary.create("/home/javatar/custom/cache")

        val a = cache.index(5).copyArchives()

        cache.index(5).copyArchives().forEach {
            custom.index(5).add(it)
        }

        custom.index(5).add(
            archives = a,
            true
        )

        custom.index(5).update()

    }

}