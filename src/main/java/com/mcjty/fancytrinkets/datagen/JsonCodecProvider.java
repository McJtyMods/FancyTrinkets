package com.mcjty.fancytrinkets.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This code is backported from 1.19 Forge where it is under the LGPL-2.1 license
 * https://github.com/MinecraftForge/MinecraftForge/blob/bdddc8c5d4ae49de37db7fb51d6bd9ca4274ab22/src/main/java/net/minecraftforge/common/data/JsonCodecProvider.java
 */
public class JsonCodecProvider<T> implements DataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final DataGenerator dataGenerator;
    protected final ExistingFileHelper existingFileHelper;
    protected final String modid;
    protected final DynamicOps<JsonElement> dynamicOps;
    protected final PackType packType;
    protected final String directory;
    protected final Codec<T> codec;
    protected final Map<ResourceLocation, T> entries;

    public JsonCodecProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String modid, DynamicOps<JsonElement> dynamicOps, PackType packType,
                             String directory, Codec<T> codec, Map<ResourceLocation, T> entries) {
        // Track generated data so other dataproviders can validate if needed.
        final ExistingFileHelper.ResourceType resourceType = new ExistingFileHelper.ResourceType(packType, ".json", directory);
        for (ResourceLocation id : entries.keySet()) {
            existingFileHelper.trackGenerated(id, resourceType);
        }
        this.dataGenerator = dataGenerator;
        this.existingFileHelper = existingFileHelper;
        this.modid = modid;
        this.dynamicOps = dynamicOps;
        this.packType = packType;
        this.directory = directory;
        this.codec = codec;
        this.entries = entries;
    }

    public static <T> JsonCodecProvider<T> forDatapackRegistry(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String modid,
                                                               RegistryOps<JsonElement> registryOps, ResourceKey<Registry<T>> registryKey, Map<ResourceLocation, T> entries) {
        final ResourceLocation registryId = registryKey.location();
        // Minecraft datapack registry folders are in data/json-namespace/registry-name/
        // Non-vanilla registry folders are data/json-namespace/registry-namespace/registry-name/
        final String registryFolder = "minecraft".equals(registryId.getNamespace())
                ? registryId.getPath()
                : registryId.getNamespace() + "/" + registryId.getPath();
        final Codec<T> codec = (Codec<T>) RegistryAccess.REGISTRIES.get(registryKey).codec();
        return new JsonCodecProvider<>(dataGenerator, existingFileHelper, modid, registryOps, PackType.SERVER_DATA, registryFolder, codec, entries);
    }

    @Override
    public void run(HashCache cache) throws IOException {
        final Path outputFolder = this.dataGenerator.getOutputFolder();
        final String dataFolder = this.packType.getDirectory();
        gather(LamdbaExceptionUtils.rethrowBiConsumer((id, value) -> {
            final Path path = outputFolder.resolve(String.join("/", dataFolder, id.getNamespace(), this.directory, id.getPath() + ".json"));
            JsonElement encoded = this.codec.encodeStart(this.dynamicOps, value)
                    .getOrThrow(false, msg -> LOGGER.error("Failed to encode {}: {}", path, msg));
            DataProvider.save(GSON, cache, encoded, path);
        }));
    }

    protected void gather(BiConsumer<ResourceLocation, T> consumer) {
        this.entries.forEach(consumer);
    }

    @Override
    public String getName() {
        return String.format("%s generator for %s", this.directory, this.modid);
    }
}