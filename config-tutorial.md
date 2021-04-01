---
description: >-
  This page will explain the different configuration options provided by this
  plugin.
---

# Config Tutorial

| Node | Effect | Placeholders |
| :--- | :--- | :--- |
| secondsBetweenClear | The interval between clears | &lt;none&gt; |
| messageConfig.announcer | The start of every message from this plugin | &lt;none&gt; |
| messageConfig.timeCheck | The reply a user will receive when requesting to check how long until the next clear. | %time% : How long until the next clear |
| messageConfig.nextClear | Broadcast when the next clear is scheduled | &lt;none&gt; |
| messageConfig.force | Broadcast when an admin forces a clear | &lt;none&gt; |
| messageConfig.timed | Seconds : MessageHolder pair, each pair signifies a message and optional sound that will be sent out when the specified seconds remain until the next clear. | &lt;none&gt; |
| minItemAge | How many seconds do dropped items stay immune from clears. | &lt;none&gt; |
| immuneSpecs | Any Pokemon matching a spec inside this list will be immune from clears | &lt;none&gt; |

## Default Config

```text
{
  "secondsBetweenClear": 300,
  "messageConfig": {
    "announcer": "&f[&dClearfairy&f]",
    "timeCheck": "&aThe next clear will be in &e%time%",
    "nextClear": "&aAnother clear will happen in 5 minutes!",
    "force": "&aA clear was forced by an admin!",
    "cleared": "&aA total of &e%total%&a entities have been cleared, &e%pixelmon%&a being non special Pokémon and &e%items%&a items.",
    "timed": {
      "270": {
        "message": "&aA clear will happen in 4 minutes and 30 seconds!"
      },
      "180": {
        "message": "&aA clear will happen in 3 minutes!"
      },
      "60": {
        "message": "&aA clear will happen in 1 minute!"
      },
      "30": {
        "message": "&eA clear will happen in 30 seconds!",
        "sound": {
          "registryName": "block.anvil.place",
          "volume": 1.0,
          "pitch": 1.0
        }
      },
      "10": {
        "message": "&eA clear will happen in 10 seconds!",
        "sound": {
          "registryName": "block.anvil.place",
          "volume": 1.0,
          "pitch": 1.0
        }
      },
      "5": {
        "message": "&cA clear will happen in 5 seconds!",
        "sound": {
          "registryName": "block.anvil.place",
          "volume": 1.0,
          "pitch": 1.8
        }
      }
    }
  },
  "minItemAge": 20,
  "immuneSpecs": [
    "Mimikyu",
    "Pidgey"
  ]
}
```



