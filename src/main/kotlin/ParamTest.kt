import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.NpcLoader
import com.javatar.osrs.definitions.loaders.ParamLoader

object ParamTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val cache = CacheLibrary.create("/home/javatar/Downloads/latest/cache")
        val loader = ParamLoader()
        val ids = cache.index(2).archive(11)?.fileIds() ?: intArrayOf()

        println("Params: ${ids.size}")
        val newIds = ids.toList().subList(1164, ids.size)

        newIds.forEach {
            val data = cache.data(2, 11, it)
            val def = loader.load(it, data)
            println("$it\t${def.rawType.toInt()}")
        }
        /*ids.forEach {
            val data = cache.data(2, 9, it)
            val def = loader.load(it, data)
            if(def != null) {
                if(def.params != null) {
                    println(def.name)
                    def.params.forEach { (key, value) ->
                        println("Key $key value $value")
                    }
                }
            }
        }*/
    }

}