# Garrison API

The public API for **Garrison**, a town-guard plugin for [Towny](https://github.com/TownyAdvanced/Towny): towns hire
guards that hold a post, fight the town's enemies, take orders and train up over time.

Use this API to add new kinds of guards, buttons on a guard's menu, trainable abilities, or support for your own war
plugin. Garrison itself is sold separately <!-- Kad: store link -->; this API is MIT licensed.

## Add it to your project

Through [JitPack](https://jitpack.io/#kadika66/garrison-api). Always `provided`: the Garrison plugin carries these
classes at runtime, so never shade them.

**Maven**
```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>

<dependency>
  <groupId>com.github.kadika66</groupId>
  <artifactId>garrison-api</artifactId>
  <version>1.0.0</version>
  <scope>provided</scope>
</dependency>
```

**Gradle (Kotlin)**
```kotlin
repositories { maven("https://jitpack.io") }
dependencies { compileOnly("com.github.kadika66:garrison-api:1.0.0") }
```

You'll also need `paper-api` and `towny` as provided dependencies (the API mentions Towny's `Town`).

## Two ways to build an addon

| | Plugin | Addons-folder jar |
|---|---|---|
| Goes in | `plugins/` | `plugins/Garrison/addons/` |
| Describes itself with | `plugin.yml` with `depend: [Garrison]` | `garrison-addon.yml` |
| Entry point | `JavaPlugin.onEnable` | `GarrisonAddon.onEnable(AddonContext)` |
| Can use other plugins' code | yes | no (only Paper, Towny, this API, SiegeWar/Contested Ruins) |
| Shows in | `/plugins` | `/garrison addons` |

Use a plugin for hooks into other plugins; use the addons folder for guard types and anything self-contained.

```yaml
# garrison-addon.yml, at the root of the jar
name: MyAddon
version: 1.0.0
author: You
main: com.example.myaddon.MyAddon
depend: []          # plugins that must be enabled, or the addon is skipped
```
```java
public final class MyAddon implements GarrisonAddon {
    @Override
    public void onEnable(AddonContext context) {
        context.getGarrison().registerBehaviour(context.getPlugin(), "lantern", new LanternBehaviour());
    }
}
```

From a plugin:
```java
Garrison garrison = GarrisonAPI.get();
garrison.registerBehaviour(this, "lantern", new LanternBehaviour());
garrison.registerWarProvider(this, new MyRaids());   // guards rally to and fight in your wars
Guard guard = garrison.getGuard(entity);             // is this mob a guard? whose?
```

Server owners then give the behaviour to a guard type in `garrison.yml`:
```yaml
types:
  lantern_bearer:
    entity: HUSK
    behaviour: lantern
```

## What's in it

| | |
|---|---|
| `GuardBehaviour` | `onLoad` (add mob goals), `tick` (once a second), `fights`, `onRemove`, `getMenuButtons` |
| `MenuButton` | an extra button on a guard's gear menu |
| `Guard` | a guard: its town, type, order, mob; `getData`/`setData` keep small strings across restarts |
| `Garrison.damage(guard, target, amount)` | hurt an enemy as the guard: same friendly-fire, kill-credit and outlaw rules as a sword |
| `Garrison.isEnemy` / `isFriendly` | friend or foe, for a guard |
| `Garrison.hasUnlock` / `getTrained` | read the guard's town's training (your own unlocks and settings, prefixed `myaddon:`) |
| `WarProvider` | plug a war system in: guards rally to its battles and fight the other side |

Registrations are dropped automatically when your plugin is disabled. Call the API from the server thread only.

## Versioning

`Garrison#getApiVersion()` reports the API version (currently 1.5.0). Additions raise the minor number; anything an
addon relies on is only removed or changed in a new major version.

## Example

[Elementalist](https://github.com/kadika66/Elementalist-Garrison-addon) is a complete, MIT-licensed addon built only on
this API: a guard that wields fire, water, earth or air.

## Documentation

The [wiki](https://github.com/kadika66/garrison-api/wiki) covers Garrison for server owners and developers.

## Licence

MIT, see [LICENSE](LICENSE). This covers the API only; the Garrison plugin is proprietary.
