package me.javatar

import com.displee.cache.CacheLibrary

object AddMissingScripts
{

    @JvmStatic
    fun main(args: Array<String>) {

        val latest = CacheLibrary.create("")
        val customCache = CacheLibrary.create("")

        val scripts = latest.index(12)
        scripts.cache()

        val customScripts = customCache.index(12)
        customScripts.cache()

        scripts.archiveIds().forEach { id ->
            val a = customScripts.archive(id)
            val aa = scripts.archive(id)
            if(a == null && aa != null && aa.file(0) != null) {
                customCache.put(12, id, aa.file(0)!!.data!!)
            }
        }

    }

}