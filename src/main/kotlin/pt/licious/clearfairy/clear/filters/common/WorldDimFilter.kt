package pt.licious.clearfairy.clear.filters.common

import net.minecraft.entity.Entity
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter
import pt.licious.clearfairy.config.ConfigManager

class WorldDimFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.clearsafe"

    override fun shouldKeep(entity: Entity) = ConfigManager.config.immuneWorldDims.any { it == entity.world.provider.dimension }
}