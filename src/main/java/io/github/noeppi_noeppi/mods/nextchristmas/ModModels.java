package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.mill.RenderMill;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.RenderOven;
import io.github.noeppi_noeppi.mods.nextchristmas.player.RenderSantaHat;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModModels {

    private static final Map<ResourceLocation, IBakedModel> models = new HashMap<>();

    public static void register() {
        registerModel(RenderOven.DOOR_MODEL);
        registerModel(RenderMill.CRANK_MODEL);
        registerModel(RenderSantaHat.HAT_MODEL);
        registerModel(RenderSantaHat.ITEM_MODEL);
    }

    private static void registerModel(ResourceLocation location) {
        if (!models.containsKey(location)) {
            models.put(location, null);
        }
    }

    public static IBakedModel getModel(ResourceLocation location) {
        if (models.containsKey(location)) {
            if (models.get(location) == null) {
                throw new IllegalStateException("Special Model not loaded: " + location);
            } else {
                return models.get(location);
            }
        } else {
            throw new IllegalStateException("Special Model not registered: " + location);
        }
    }

    public static void registerModels(ModelRegistryEvent event) {
        for (ResourceLocation location : models.keySet()) {
            ModelLoader.addSpecialModel(location);
        }
    }

    public static void bakeModels(ModelBakeEvent event) {
        Set<ResourceLocation> locations = new HashSet<>(models.keySet());
        for (ResourceLocation location : locations) {
            models.put(location, event.getModelRegistry().get(location));
        }
    }
}
