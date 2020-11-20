package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        new OvenRecipeBuilder(Items.DIAMOND).setInput(Items.IRON_INGOT).build(consumer);
        new OvenRecipeBuilder(ModBlocks.s1).setInput(ModBlocks.s6).build(consumer);
        new OvenRecipeBuilder(Blocks.DIAMOND_BLOCK).setInput(Blocks.GOLD_BLOCK).build(consumer);
    }
}
