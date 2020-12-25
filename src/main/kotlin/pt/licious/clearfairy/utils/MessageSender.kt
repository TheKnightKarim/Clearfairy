package pt.licious.clearfairy.utils

import net.minecraft.command.ICommandSender
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.FMLCommonHandler
import pt.licious.clearfairy.config.ConfigManager

object MessageSender {

    fun broadcast(message: String) = FMLCommonHandler.instance().minecraftServerInstance.playerList.sendMessage(TextComponentString("${ConfigManager.config.messageConfig.announcer} $message".replace("(?i)&([0-9a-fk-or])".toRegex(), "\u00a7$1")))

    fun send(receiver: ICommandSender, message: String) = receiver.sendMessage(TextComponentString("${ConfigManager.config.messageConfig.announcer} $message".replace("(?i)&([0-9a-fk-or])".toRegex(), "\u00a7$1")))

}