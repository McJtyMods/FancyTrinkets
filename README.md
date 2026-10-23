FancyTrinkets for Fabric 26.3
===========================

Configurable rings, belts, charms and other magical equipment, using
[Trinkets Updated](https://modrinth.com/mod/trinkets-updated/version/4.2.1%2B26.3) 4.2.1+26.3.
Requires Fabric Loader 0.19.5 or later, Fabric API 0.164.0+26.3 or later,
Forge Config API Port 26.3.1 or later, and McJtyLib 11.0.0 for Fabric 26.3.
The One Probe 16.0.0 and JEI integrations use their Fabric 26.3 releases.

Use Java 25 and the Gradle 9.6.0 wrapper. Build with `./gradlew build` and install
`build/libs/fancytrinkets-fabric-26.3-6.0.0-fabric.jar` with its dependencies.
The sibling `Multi_fabric` workspace
includes this checkout automatically and supplies `gradletools.gradle`.
A standalone checkout uses the published shared script from the `26.3_fabric`
branch. No project-local script is needed.

Standalone builds select Jenkins artifacts
`mcjtylib-fabric-26.3-11.0.0-fabric.1.jar` and
`theoneprobe-26.3-16.0.0-fabric-1.jar`. Their build suffixes do not raise the
minimum mod versions. Workspace builds use the sibling McJtyLib and TOP
projects when present.

McJtyLib supplies base blocks/items, the Experience Crafter's menu, widgets,
commands, inventory, attachments, and client synchronization. Trinkets Updated
provides equipment slots and equip/tick/unequip callbacks. Existing trinket
components, XP recipes, slot definitions, player effects, and saved crafter XP
are retained. Key mappings use Minecraft 26.3's SDL input API.

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

For a standalone checkout, use `./gradlew -PportTests runGameTestServer`.
For its optional client scene, copy `runs/gameTestServer/world` to
`run-fabric/saves/fancytrinkets-port-smoke`, complete accessibility onboarding,
then run `./gradlew -PportTests -PclientSmoke runClient`. The scene checks item
models, JEI recipes, XP crafting, and equipment hotkeys, then saves screenshots
and exits. It modifies only that disposable test world.
