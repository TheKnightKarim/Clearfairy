package pt.licious.clearfairy.clear.filters.pixelmon

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon
import net.minecraft.entity.Entity
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter

class LegendaryFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.pixelmon.legendary"

    override fun shouldKeep(entity: Entity) = entity is EntityPixelmon && entity.pokemonData.isLegendary

}