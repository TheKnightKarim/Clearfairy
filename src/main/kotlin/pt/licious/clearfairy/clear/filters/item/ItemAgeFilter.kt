package pt.licious.clearfairy.clear.filters.item

import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter
import pt.licious.clearfairy.config.ConfigManager

class ItemAgeFilter : IFilter {

    override fun getID() = "${Clearfairy.MOD_ID}.item.age"

    override fun shouldKeep(entity: Entity): Boolean {
        if (entity is EntityItem) {
            return entity.ticksExisted <= ConfigManager.config.minItemAge * 20
        }
        return false
    }

}