package pt.licious.clearfairy.config.data

import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs
import net.minecraft.init.Items
import net.minecraft.item.Item

data class ConfigHolder(
    val secondsBetweenClear: Int = 300,
    val messageConfig: MessageConfig = MessageConfig(),
    val minItemAge: Int = 20,
    val immuneSpecs: Set<PokemonSpec> = setOf(PokemonSpec("Mimikyu"), PokemonSpec("Pidgey")),
    val immuneWorldDims: Set<Int> = setOf(0)
)
