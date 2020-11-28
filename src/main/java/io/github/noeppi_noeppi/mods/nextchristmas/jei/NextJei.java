package io.github.noeppi_noeppi.mods.nextchristmas.jei;

import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.ScreenOven;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@JeiPlugin
public class NextJei implements IModPlugin {

    public static final ResourceLocation ID = new ResourceLocation(NextChristmas.getInstance().modid, "jeiplugin");

    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

    private static IJeiRuntime runtime = null;

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new OvenCategory(registration.getJeiHelpers().getGuiHelper()),
                new MillCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager rm = Objects.requireNonNull(world).getRecipeManager();

        registration.addRecipes(rm.getRecipesForType(ModRecipes.OVEN), OvenCategory.ID);
        registration.addRecipes(rm.getRecipesForType(ModRecipes.MILL), MillCategory.ID);

        registration.addIngredientInfo(new ItemStack(ModItems.vanillaFruits), VanillaTypes.ITEM, "jei.next_christmas.description.vanilla_fruits");
        registration.addIngredientInfo(new ItemStack(ModItems.hazelnut), VanillaTypes.ITEM, "jei.next_christmas.description.hazelnut");
        registration.addIngredientInfo(new ItemStack(ModItems.cinnamonBark), VanillaTypes.ITEM, "jei.next_christmas.description.cinnamon_bark");
        registration.addIngredientInfo(new ItemStack(ModBlocks.star), VanillaTypes.ITEM, "jei.next_christmas.description.star");
    }

    @Override
    public void registerGuiHandlers(@Nonnull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ScreenOven.class, 79, 19, 24, 17, OvenCategory.ID);
        registration.addRecipeClickArea(ScreenOven.class, 79, 50, 24, 17, OvenCategory.ID);
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.oven), OvenCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.grainMill), MillCategory.ID);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }

    public static void run(Consumer<IJeiRuntime> action) {
        if (runtime != null) {
            action.accept(runtime);
        }
    }

    public static <T> T run(Function<IJeiRuntime, T> action) {
        if (runtime != null) {
            return action.apply(runtime);
        } else {
            return null;
        }
    }
}
