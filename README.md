FancyTrinkets for Fabric 26.2
===========================

Configurable rings, belts, charms and other magical equipment, using
[Trinkets Updated](https://modrinth.com/mod/trinkets-updated) 4.1.0-rc.1+26.2.
Requires Fabric API, Forge Config API Port and McJtyLib for Fabric. The One Probe
and JEI integrations use their Fabric releases.

Use Java 25 and the Gradle 9.5.1 wrapper. The sibling `Multi_fabric` workspace
includes this checkout automatically and supplies `gradletools.gradle`.
A standalone checkout uses the shared script from the `26.2_fabric` branch;
a local, ignored copy can be used while workspace changes are unpublished.

When using the current sibling McJtyLib source, set `BUILD_NUMBER=3` so its
development version satisfies the shared minimum dependency version.

From `Multi_fabric`:

```
./gradlew :FancyTrinkets:build :SignTastic:build :workspace:classes
./gradlew -PportTests :FancyTrinkets:runGameTestServer
./gradlew -Penable_individual_runs :FancyTrinkets:runDatagen
```

The game tests cover data persistence, all 34 XP recipes, equipment effects,
hotkeys, damage reduction, and loot drops. Add `-PportTests -PclientSmoke
-Penable_individual_runs` to `:FancyTrinkets:runClient` for the disposable client
scene. Port checks are excluded from normal release builds.
