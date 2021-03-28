import com.displee.cache.CacheLibrary
import java.io.File

object MapPacker {

    @JvmStatic
    fun main(args: Array<String>) {

        //val latest_osrs = CacheLibrary.create("./latest_osrs")
        val custom_osrs = CacheLibrary.create("./custom_osrs")

        val landscapeFile = File("./floors.dat")
        val objectsFile = File("./objects.dat")

        val regionID = 12342

        val regionX = regionID shr 8
        val regionY = regionID and 255

        val maps = custom_osrs.index(5)

        val objects = maps.add("l${regionX}_${regionY}", intArrayOf(0, 0, 0, 0))
        val floors = maps.add("m${regionX}_${regionY}", intArrayOf(0, 0, 0, 0))
        objects.add(0, objectsFile.readBytes())
        floors.add(0, landscapeFile.readBytes())

        maps.update()
    }

}