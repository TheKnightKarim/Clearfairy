---
description: The default filters implemented by this plugin and their effect.
---

# Default Filters

| Filter | Target Entity | Condition |
| :--- | :--- | :--- |
| Clear Safe | Any | If the Entity has the NBT tag set to -1 or still has "lifes" left it will be immune to the clear. 1 "life" is consumed per clear it survives. -1 is a permanent safekeep. |
| Item Age | EntityItem | If the dropped item is younger then the minimal clear age specified in the config |
| Boss | EntityPixelmon | If the Pokémon is a Boss |
| Legendary | EntityPixelmon | If the Pokémon is a Legendary \(In Pixelmon mythicals are legends\) |
| Non Wild | EntityPixelmon | If the Pokémon is owned by a player, is currently battling or set to not despawn internally. |
| Spec | EntityPixelmon | If any Spec provided in the config matches this Pokémon. |
| Ultra Beast | EntityPixelmon | If the Pokémon is an Ultra Beast |

