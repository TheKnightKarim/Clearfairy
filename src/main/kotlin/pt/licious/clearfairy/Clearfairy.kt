package pt.licious.clearfairy

import com.pixelmonmod.pixelmon.Pixelmon
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.LogManager
import pt.licious.clearfairy.listener.TickListener
import pt.licious.clearfairy.clear.ClearManager
import pt.licious.clearfairy.commands.ClearfairyCommands
import pt.licious.clearfairy.config.ConfigManager

@Mod(
    modid = Clearfairy.MOD_ID,
    name = Clearfairy.MOD_NAME,
    version = Clearfairy.VERSION,
    modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
    acceptableRemoteVersions = "*",
    dependencies = "after:${Pixelmon.MODID}",
    acceptedMinecraftVersions = "[1.12.2]"
)
object Clearfairy {

    const val MOD_ID = "clearfairy"
    const val MOD_NAME = "Clearfairy"
    const val VERSION = "1.0"
    val LOG = LogManager.getLogger(MOD_NAME)

    @Mod.EventHandler
    fun onPosInit(e: FMLPostInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(TickListener())
        ConfigManager.onPostInit()
        ClearManager.onPostInit()
    }

    @Mod.EventHandler
    fun onServerStarting(e: FMLServerStartingEvent) {
        e.registerServerCommand(ClearfairyCommands())
    }

}