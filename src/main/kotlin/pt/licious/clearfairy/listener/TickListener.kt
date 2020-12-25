package pt.licious.clearfairy.listener

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import pt.licious.clearfairy.clear.ClearManager

class TickListener {

    @SubscribeEvent
    fun onTick(e: TickEvent.ServerTickEvent) {
        if (e.phase == TickEvent.Phase.START)
            ClearManager.tick()
    }

}