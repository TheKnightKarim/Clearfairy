package pt.licious.clearfairy.commands

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.event.ClickEvent
import net.minecraft.util.text.event.HoverEvent
import pt.licious.clearfairy.clear.ClearManager
import pt.licious.clearfairy.config.ConfigManager
import pt.licious.clearfairy.utils.MessageSender

class ClearfairyCommands : CommandBase() {

    override fun getName() = "clearfairy"

    override fun getUsage(sender: ICommandSender): String {
        val text = TextComponentString("§f=======[§d§lClearfairy§f]=======\n")
        val hoverBase = TextComponentString("§e/clearfairy")
        hoverBase.style.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clearfairy")
        hoverBase.style.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponentString("Shows you this menu"))
        text.appendSibling(hoverBase)
        val possibleCommands = sortedMapOf(
            "/clearfairy time" to "Shows you how long until the next clear"
        )
        if (hasPermission(sender, "clearfairy.admin.reload"))
            possibleCommands["/clearfairy reload"] = "Reload the plugin"
        if (hasPermission(sender, "clearfairy.admin.force"))
            possibleCommands["/clearfairy forceclear"] = "Forcefully execute a clear"
        possibleCommands.forEach { (command, hover) ->
            text.appendText("\n")
            val hoverCommand = TextComponentString("§e$command")
            hoverCommand.style.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
            hoverCommand.style.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponentString(hover))
            text.appendSibling(hoverCommand)
        }
        sender.sendMessage(text)
        return ""
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        if (args.size == 1) {
            if (args[0].equals("reload", true) && hasPermission(sender, "clearfairy.admin.reload")) {
                ConfigManager.load()
                MessageSender.send(sender, "&aReloaded the config.")
            }
            else if (args[0].equals("forceclear", true) && hasPermission(sender, "clearfairy.admin.force")) {
                val message = ConfigManager.config.messageConfig.force
                ClearManager.performClear()
                if (message.isNotBlank())
                    MessageSender.broadcast(message)
            }
            else if (args[0].equals("time", true)) {
                val totalSecs = ClearManager.ticks / 20
                val hours = totalSecs / 3600
                val minutes = totalSecs % 3600 / 60
                val seconds = totalSecs % 60
                MessageSender.send(sender, ConfigManager.config.messageConfig.timeCheck.replace("%time%", String.format("%02d:%02d:%02d", hours, minutes, seconds)))
            }
            else
                getUsage(sender)
        }
        else
            getUsage(sender)
    }

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender) = true

    private fun hasPermission(sender: ICommandSender, permission: String) = sender.canUseCommand(4, permission)

    override fun getTabCompletions(server: MinecraftServer, sender: ICommandSender, args: Array<String>, targetPos: BlockPos?): MutableList<String> {
        val possibleArgs = mutableListOf<String>()
        return getListOfStringsMatchingLastWord(args, possibleArgs)
    }
}