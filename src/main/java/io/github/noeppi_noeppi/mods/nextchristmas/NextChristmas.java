package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import io.github.noeppi_noeppi.mods.nextchristmas.data.DataGenerators;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Reindeer;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.ReindeerRender;
import io.github.noeppi_noeppi.mods.nextchristmas.network.NextNetwork;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.ScreenOven;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.Collections;

@Mod("next_christmas")
public class NextChristmas extends ModXRegistration {

    private static NextChristmas instance;
    private static NextNetwork network;

    public NextChristmas() {
        super("next_christmas", new ItemGroup("next_christmas") {
            @Nonnull
            @Override
            public ItemStack createIcon() {
                return new ItemStack(Items.BARRIER);
            }
        });

        instance = this;
        network = new NextNetwork(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);

        this.addRegistrationHandler(ModBlocks::register);
        this.addRegistrationHandler(ModItems::register);
        this.addRegistrationHandler(ModEntities::register);
        this.addRegistrationHandler(ModWorldGen::register);
        this.addRegistrationHandler(ModRecipes::register);

        DistExecutor.unsafeRunForDist(() -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModModels::registerModels);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModModels::bakeModels);
            this.addRegistrationHandler(ModModels::register);
            return null;
        }, () -> () -> null);
    }

    @Override
    protected void setup(FMLCommonSetupEvent fmlCommonSetupEvent) {
        //noinspection ConstantConditions
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ModWorldGen.christmasForest.getRegistryName()), 10));

        GlobalEntityTypeAttributes.put(ModEntities.reindeer, Reindeer.defaultAttributes());
        EntitySpawnPlacementRegistry.register(ModEntities.reindeer, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Reindeer::canSpawnAt);
    }

    @Override
    protected void clientSetup(FMLClientSetupEvent fmlClientSetupEvent) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.reindeer, ReindeerRender::new);
    }

    @Nonnull
    public static NextChristmas getInstance() {
        return instance;
    }

    @Nonnull
    public static NextNetwork getNetwork() {
        return network;
    }
}

