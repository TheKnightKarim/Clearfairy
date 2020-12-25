package pt.licious.clearfairy.config

import com.google.gson.GsonBuilder
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.config.adapter.SpecAdapter
import pt.licious.clearfairy.config.data.ConfigHolder
import java.io.File

object ConfigManager {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .registerTypeAdapter(PokemonSpec::class.java, SpecAdapter())
        .create()

    private val configDir = File("config/${Clearfairy.MOD_ID}")
    private val configFile = File(configDir, "config.json")

    var config = ConfigHolder()
        private set

    internal fun onPostInit() {
        if (!configDir.exists())
            configDir.mkdirs()
        if (configFile.exists())
            load()
        else
            configFile.createNewFile()
        // Always write on startup to add in any new options that might be added in the future.
        write()
    }

    fun load() = configFile.bufferedReader().use { config = gson.fromJson(it.readText(), ConfigHolder::class.java) }

    private fun write() = configFile.bufferedWriter().use { it.write(gson.toJson(config)) }

}