package pt.licious.clearfairy.clear.filters.pixelmon

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon
import com.pixelmonmod.pixelmon.enums.EnumSpecies
import net.minecraft.entity.Entity
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter

class UltraBeastFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.pixelmon.ultrabeast"

    override fun shouldKeep(entity: Entity) = entity is EntityPixelmon && EnumSpecies.ultrabeasts.contains(entity.species.pokemonName)

}