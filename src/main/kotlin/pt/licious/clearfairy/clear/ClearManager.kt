package pt.licious.clearfairy.clear

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.FMLCommonHandler
import pt.licious.clearfairy.Clearfairy
import pt.licious.clearfairy.clear.filters.IFilter
import pt.licious.clearfairy.clear.filters.common.ClearSafeFilter
import pt.licious.clearfairy.clear.filters.item.ItemAgeFilter
import pt.licious.clearfairy.clear.filters.pixelmon.*
import pt.licious.clearfairy.config.ConfigManager
import pt.licious.clearfairy.utils.MessageSender

object ClearManager {

    private val targetEntities = hashMapOf<Class<out Entity>, (Entity) -> Unit>()
    private val filters = hashSetOf<IFilter>()

    internal fun onPostInit() {
        // Common
        registerFilter(Clearfairy.MOD_ID, ClearSafeFilter())
        // EntityPixelmon
        registerTarget(Clearfairy.MOD_ID, EntityPixelmon::class.java) { entity -> (entity as EntityPixelmon).unloadEntity() }
        registerFilter(Clearfairy.MOD_ID, BossFilter())
        registerFilter(Clearfairy.MOD_ID, LegendaryFilter())
        registerFilter(Clearfairy.MOD_ID, NonWildFilter())
        registerFilter(Clearfairy.MOD_ID, SpecFilter())
        registerFilter(Clearfairy.MOD_ID, UltraBeastFilter())
        // Items
        registerTarget(Clearfairy.MOD_ID, EntityItem::class.java)
        registerFilter(Clearfairy.MOD_ID, ItemAgeFilter())
    }

    var ticks = ConfigManager.config.secondsBetweenClear * 20
        private set

    internal fun tick() {
        ticks--
        if (ticks == 0)
            performClear()
        else if (ticks % 20 == 0)
            ConfigManager.config.messageConfig.checkForNotification(ticks / 20)
    }

    /**
     * Force start a clear.
     *
     * @param reschedule Should the existing clear be rescheduled? (Default: True)
     * @param excludedDims The dimension ID's that will be skipped. (Default: None)
     */
    fun performClear(reschedule: Boolean = true, vararg excludedDims: Int) {
        var totalCleared = 0
        var totalPokemon = 0
        var totalItems = 0
        FMLCommonHandler.instance().minecraftServerInstance.worlds.forEach { world ->
            val dim = world.provider.dimension
            if (excludedDims.none { it == dim }) {
                var dimCleared = 0
                world.loadedEntityList.forEach { entity ->
                    if (targetEntities.containsKey(entity::class.java) && filters.none { it.shouldKeep(entity) }) {
                        if (entity is EntityPixelmon)
                            totalPokemon++
                        else if (entity is EntityItem)
                            totalItems++
                        targetEntities[entity::class.java]?.invoke(entity)
                        dimCleared++
                        totalCleared++
                    }
                }
                Clearfairy.LOG.info("Cleared $dimCleared entities in dimension $dim")
            }
        }
        if (reschedule) {
            ticks = ConfigManager.config.secondsBetweenClear * 20
            MessageSender.broadcast(ConfigManager.config.messageConfig.nextClear)
        }
        MessageSender.broadcast(ConfigManager.config.messageConfig.cleared.replace("%total%", totalCleared.toString()).replace("%pixelmon%", totalPokemon.toString()).replace("%items%", totalItems.toString()))
        Clearfairy.LOG.info("Cleared $totalCleared entities")
    }


    /**
     * For mod compatibility purposes.
     * Registers an [Entity] to listen for.
     * Duplicates will be rejected.
     *
     * @param sourceModID The Mod registering a filter.
     * @param entityClazz The [Entity] class.
     * @param removalStrategy How should the entity be unloaded? By default it will use [World.removeEntity], some mods may have specific methods however such as Pixelmon with [EntityPixelmon.unloadEntity].
     * @return Was the registry successful?
     */
    fun registerTarget(sourceModID: String, entityClazz: Class<out Entity>, removalStrategy: (Entity) -> Unit = { entity -> entity.world.removeEntity(entity) }): Boolean {
        if (entityClazz is EntityPlayer) {
            Clearfairy.LOG.error("'$sourceModID' tried to register 'EntityPlayer' as a target, this will cause issues so it was skipped")
            return false
        }
        else if (targetEntities.containsKey(entityClazz)) {
            Clearfairy.LOG.error("Tried to register a duplicate entity listener for '${entityClazz.simpleName}' from '$sourceModID'")
            return false
        }
        targetEntities[entityClazz] = removalStrategy
        Clearfairy.LOG.info("Registered a listener for '${entityClazz.simpleName}' from '$sourceModID'")
        return true
    }

    /**
     * Registers an [IFilter].
     * Duplicates will be rejected.
     * Best practice when implementing your own is setting the id to modID.shortName
     *
     * @param sourceModID The Mod registering a filter.
     * @param filter The implemented [IFilter]
     * @return Was the registry successful?
     */
    fun registerFilter(sourceModID: String, filter: IFilter): Boolean {
        val id = filter.getID()
        if (filters.any { it.getID() == id }) {
            Clearfairy.LOG.error("Tried to register a duplicate filter with the ID '$id' from the mod '$sourceModID'")
            return false
        }
        filters.add(filter)
        Clearfairy.LOG.info("Registered the filter '$id' from the Mod '$sourceModID'")
        return true
    }

}