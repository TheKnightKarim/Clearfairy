package pt.licious.clearfairy.clear.filters.pixelmon

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon
import net.minecraft.entity.Entity
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter
import pt.licious.clearfairy.config.ConfigManager

class SpecFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.pixelmon.spec"

    override fun shouldKeep(entity: Entity) = entity is EntityPixelmon && ConfigManager.config.immuneSpecs.any { it.matches(entity.pokemonData) }

}