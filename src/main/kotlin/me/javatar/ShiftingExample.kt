package me.javatar

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.output.OutputStream

object ShiftingExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val bank = 12 // Archive ID, where the bank components are stored

        val cache = CacheLibrary.create("/home/javatar/Downloads/latest/cache")

        val components = cache.index(3).archive(12)?.fileIds() ?: intArrayOf()

        println("Bank Components size: ${components.size}")

        val component = 5

        val out = OutputStream()

        val widget = bank shl 16 or component //12 << 16 | 5

        println(widget)

        out.writeInt(widget)

        val parentId = widget shr 16
        val childId = widget and 65535

        println(parentId)
        println(childId)

    }

}