---
description: Some basic examples on how to use the API.
---

# API Usage

{% hint style="info" %}
Both the plugin and these examples will be in Kotlin other JVM languages can also use this API.
{% endhint %}

## Forcing a clear

| Param | Description | Type | Default |
| :--- | :--- | :--- | :--- |
| reschedule | Should the existing clear be rescheduled? | Boolean | true |
| excludedDims | The dimension ID's that will be skipped. | vararg Int | Empty |

```kotlin
fun foo() {
    // I don't wanna reschedule
    ClearManager.performClear(false)
    // I don't wanna clear the Overworld
    ClearManager.performClear(true, 0)
}
```

## Example filter implementation \(Kotlin\)

```kotlin
class AntiKarpFilter : IFilter {

    companion object {
    
        private const val KEY = "AntiKarp"

        fun register() {
            ClearManager.registerTarget(KEY, EntityStatue::class.java)
            ClearManager.registerFilter(KEY, AntiKarpFilter())
        }

    }

    override fun getID() = "${MyMod.MOD_ID}.karpstatue"

    // A "dirty" example that should make it clearer
    override fun shouldKeep(entity: Entity): Boolean {
        if (entity is EntityStatue) {
            val statue = entity as EntityStatue
            // Lets only keep it if isn't a Magikarp statue
            if (statue.species == EnumSpecies.Magikarp)
                return false
            else
                return true
        }
        return false
    }

    // The cleaner example
    override fun shouldKeep(entity: Entity) = entity is EntityStatue && entity.species != EnumSpecies.Magikarp

}
```

To register your custom filter simply call your register method at any point after PosInit on your mod.

