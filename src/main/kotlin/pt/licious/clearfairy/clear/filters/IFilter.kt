package pt.licious.clearfairy.clear.filters

import net.minecraft.entity.Entity

interface IFilter {

    fun getID(): String

    fun shouldKeep(entity: Entity): Boolean

}