package pt.licious.clearfairy.clear.filters.common

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter

class ClearSafeFilter : IFilter {

    companion object {

        private const val CLEAR_SAFE_KEY = "ClearSafe"

        /**
         * Applies the clear safe status to an entity.
         *
         * @param entity The entity being targeted.
         * @param wipesToSave How many wipes will it survive? Less then 0 will make it clear immune.
         */
        fun applyClearSafe(entity: Entity, wipesToSave: Int) {
            if (entity is EntityPlayerMP) {
                Clearfairy.LOG.warn("An attempt to apply ClearSafe to an EntityPlayer was made, this doesn't do anything so it was ignored")
                return
            }
            entity.entityData.setInteger(CLEAR_SAFE_KEY, wipesToSave.coerceAtLeast(-1))
        }

    }

    override fun getID() = "${Clearfairy.MOD_ID}.clearsafe"

    override fun shouldKeep(entity: Entity): Boolean {
        val tag = entity.entityData
        var clears = tag.getInteger(CLEAR_SAFE_KEY)
        if (clears == -1)
            return true
        else if (clears == 0)
            return false
        clears--
        tag.setInteger(CLEAR_SAFE_KEY, clears)
        return true
    }
}