package pt.licious.clearfairy.clear.filters.pixelmon

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon
import net.minecraft.entity.Entity
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter

class NonWildFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.pixelmon.nonwild"

    override fun shouldKeep(entity: Entity): Boolean {
        if (entity is EntityPixelmon) {
            val pokemon = entity.pokemonData
            return !entity.canDespawn || entity.hasOwner() || entity.battleController != null || pokemon.isInRanch
        }
        return false
    }

}