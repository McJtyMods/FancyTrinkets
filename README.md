# FancyTrinkets

Configurable trinkets, Curios effects, and an experience-powered crafter.

This branch targets Minecraft 26.3, NeoForge 26.3.0.83, and Java 25.
McJtyLib and Curios are required. The One Probe and JEI are optional integrations.

The build uses [MultiWorkspace's published 26.3 script](https://github.com/McJtyMods/MultiWorkspace/blob/26.3_neo/gradletools.gradle).
It resolves McJtyLib `26.3-11.0.0.3` and TOP `26.3_neo-16.0.0-1`;
McJtyLib's minimum mod dependency remains `26.3-11.0.0`.
Curios is pinned to `17.0.0-beta.3+26.3`.

Build with `./gradlew build` and regenerate resources with `./gradlew runClientData`.
Run the opt-in server port checks with `./gradlew -PportTests runGameTestServer`.
Interactive client checks are available through `./gradlew -PportTests -PclientSmoke runClient`.
The server checks cover legacy data, all 34 XP recipes, Curios effects, and loot conditions.
