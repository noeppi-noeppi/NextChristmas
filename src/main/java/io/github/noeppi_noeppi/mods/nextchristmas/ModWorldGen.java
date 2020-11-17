package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.biome.ChristmasTree;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;

public class ModWorldGen {

    public static final ChristmasTree christmasTree = new ChristmasTree();

    public static final Biome christmasForest = new Biome.Builder()
            .scale(0.1f)
            .temperature(0)
            .withTemperatureModifier(Biome.TemperatureModifier.FROZEN)
            .category(Biome.Category.FOREST)
            .depth(0.2f)
            .precipitation(Biome.RainType.SNOW)
            .downfall(1)
            .withMobSpawnSettings(
                    new MobSpawnInfoBuilder(MobSpawnInfo.EMPTY)
                            .withCreatureSpawnProbability(0.5f)
                            .withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntities.reindeer, 200, 10, 20))
                            .copy()
            ).setEffects(
                    new BiomeAmbience.Builder()
                            .setWaterColor(4159204)
                            .setWaterFogColor(329011)
                            .setFogColor(12638463)
                            .withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.0F))
                            .build()
            ).withGenerationSettings(
                    new BiomeGenerationSettings.Builder()
                            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j)
                            .withStructure(StructureFeatures.VILLAGE_SNOWY)
                            .withStructure(StructureFeatures.IGLOO)
                            .withStructure(StructureFeatures.STRONGHOLD)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIRT)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRAVEL)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRANITE)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIORITE)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_ANDESITE)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_COAL)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_IRON)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_REDSTONE)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIAMOND)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_LAPIS)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.DISK_SAND)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.DISK_CLAY)
                            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.DISK_GRAVEL)
                            .withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER)
                            .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, christmasTree.configuredFeature)
                            .build()
            ).build();

    public static void register() {
        NextChristmas.getInstance().register("christmas_tree", christmasTree);
        NextChristmas.getInstance().register("christmas_forest", christmasForest);
    }
}
