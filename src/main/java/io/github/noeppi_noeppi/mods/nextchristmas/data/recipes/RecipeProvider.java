package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {

    }
}
