package pt.licious.clearfairy.config.adapter

import com.google.gson.*
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec
import java.lang.reflect.Type

class SpecAdapter : JsonDeserializer<PokemonSpec>, JsonSerializer<PokemonSpec> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = PokemonSpec(json.asString)

    override fun serialize(src: PokemonSpec, typeOfSrc: Type, context: JsonSerializationContext) = JsonPrimitive(src.args.joinToString(separator = " "))

}