package pt.licious.clearfairy.config.data

import net.minecraft.network.play.server.SPacketCustomSound
import net.minecraft.util.SoundCategory
import net.minecraftforge.fml.common.FMLCommonHandler
import pt.licious.clearfairy.utils.MessageSender

data class MessageConfig(
    val announcer: String = "&f[&dClearfairy&f]",
    val timeCheck: String = "&aThe next clear will be in &e%time%",
    val nextClear: String = "&aAnother clear will happen in 5 minutes!",
    val force: String = "&aA clear was forced by an admin!",
    val cleared: String = "&aA total of &e%total%&a entities have been cleared, &e%pixelmon%&a being non special Pokémon and &e%items%&a items.",
    val timed: LinkedHashMap<Int, NotificationHolder> = linkedMapOf(
        270 to NotificationHolder("&aA clear will happen in 4 minutes and 30 seconds!", null),
        180 to NotificationHolder("&aA clear will happen in 3 minutes!", null),
        60 to NotificationHolder("&aA clear will happen in 1 minute!", null),
        30 to NotificationHolder("&eA clear will happen in 30 seconds!", NotificationHolder.SoundHolder("block.anvil.place", 1F, 1F)),
        10 to NotificationHolder("&eA clear will happen in 10 seconds!", NotificationHolder.SoundHolder("block.anvil.place", 1F, 1F)),
        5 to NotificationHolder("&cA clear will happen in 5 seconds!", NotificationHolder.SoundHolder("block.anvil.place", 1F, 1.8F))
    )
) {

    fun checkForNotification(seconds: Int) {
        timed[seconds]?.let { notification ->
            notification.message?.let { message ->
                if (message.isNotBlank())
                    MessageSender.broadcast(message)
            }
            notification.sound?.run { broadcastSound() }
        }
    }

    data class NotificationHolder(val message: String?, val sound: SoundHolder?) {

        data class SoundHolder(val registryName: String, val volume: Float, val pitch: Float) {

            fun broadcastSound() {
                FMLCommonHandler.instance().minecraftServerInstance.playerList.players.forEach { player ->
                    player.connection.sendPacket(SPacketCustomSound(registryName, SoundCategory.MASTER, player.posX, player.posY, player.posZ, volume, pitch))
                }
            }

        }

    }

}