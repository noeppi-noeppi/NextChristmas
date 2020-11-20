package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.ItemBiscuit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(i -> this.mod.modid.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
                .forEach(item -> {
                    if (item instanceof ItemBiscuit) {
                        this.addBiscuitRecipes((ItemBiscuit) item, consumer);
                    }
                });

        new MillRecipeBuilder(Items.SUGAR).setInput(Items.SUGAR_CANE).build(consumer);
        new MillRecipeBuilder(ModItems.flour).setInput(Items.WHEAT).setMillClicks(2).build(consumer);
        new MillRecipeBuilder(ModItems.vanilla).setInput(ModItems.vanillaFruits).build(consumer);
    }

    private void addBiscuitRecipes(ItemBiscuit biscuit, @Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(biscuit.dough_sheet)
                .patternLine("ddd")
                .patternLine("ddd")
                .patternLine(" s ")
                .key('d', biscuit.dough)
                .key('s', ModBlocks.bakingSheet)
                .addCriterion("has_item0", hasItem(biscuit.dough))
                .addCriterion("has_item1", hasItem(ModBlocks.bakingSheet))
                .build(consumer);

        new OvenRecipeBuilder(biscuit.baked_sheet).setInput(biscuit.dough_sheet).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(biscuit, 6)
                .addIngredient(biscuit.baked_sheet)
                .addCriterion("has_item", hasItem(biscuit.baked_sheet))
                .build(consumer);
    }
}
