package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.ItemBiscuit;
import io.github.noeppi_noeppi.mods.nextchristmas.player.ItemSweater;
import io.github.noeppi_noeppi.mods.nextchristmas.util.Colored;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
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
        new MillRecipeBuilder(ModItems.crushedNut).setInput(ModItems.hazelnut).setMillClicks(2).build(consumer);

        this.addColoredRecipes(ModBlocks.christmasBall, (result, color) -> {
            Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getString() + "_glazed_terracotta"));
            ShapedRecipeBuilder.shapedRecipe(result)
                    .patternLine("c")
                    .patternLine("i")
                    .key('c', Items.CHAIN)
                    .key('i', input)
                    .addCriterion("has_item0", hasItem(Items.CHAIN))
                    .addCriterion("has_item1", hasItem(input))
                    .build(consumer);
        });

        this.addColoredRecipes(ModBlocks.candle, (result, color) -> {
            ShapedRecipeBuilder.shapedRecipe(result)
                    .patternLine("s")
                    .patternLine("h")
                    .patternLine("i")
                    .key('s', Items.STRING)
                    .key('h', Items.HONEYCOMB)
                    .key('i', color.getTag())
                    .addCriterion("has_item0", hasItem(Items.STRING))
                    .addCriterion("has_item1", hasItem(Items.HONEYCOMB))
                    .addCriterion("has_item2", hasItem(color.getTag()))
                    .build(consumer);
        });

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.vanillaCrescent.dough, 8)
                .addIngredient(ModItems.flour)
                .addIngredient(ModItems.flour)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .addIngredient(ModItems.vanilla)
                .addIngredient(ModItems.vanilla)
                .addIngredient(Items.MILK_BUCKET)
                .addCriterion("has_item0", hasItem(ModItems.flour))
                .addCriterion("has_item1", hasItem(Items.SUGAR))
                .addCriterion("has_item2", hasItem(Items.EGG))
                .addCriterion("has_item3", hasItem(ModItems.vanilla))
                .addCriterion("has_item4", hasItem(Items.MILK_BUCKET))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.gingerbread.dough, 8)
                .addIngredient(ModItems.flour)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .addIngredient(Items.EGG)
                .addIngredient(ModItems.hazelnut)
                .addIngredient(ModItems.hazelnut)
                .addIngredient(Items.MILK_BUCKET)
                .addCriterion("has_item0", hasItem(ModItems.flour))
                .addCriterion("has_item1", hasItem(Items.SUGAR))
                .addCriterion("has_item2", hasItem(Items.EGG))
                .addCriterion("has_item3", hasItem(ModItems.hazelnut))
                .addCriterion("has_item4", hasItem(Items.MILK_BUCKET))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.gingerbreadHouse)
                .patternLine(" g ")
                .patternLine("gsg")
                .patternLine("ggg")
                .key('g', ModItems.gingerbread)
                .key('s', Items.SUGAR)
                .addCriterion("has_item0", hasItem(ModItems.gingerbread))
                .addCriterion("has_item1", hasItem(Items.SUGAR))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.santaHat)
                .patternLine(" w ")
                .patternLine("rrr")
                .patternLine("wrw")
                .key('r', Blocks.RED_WOOL)
                .key('w', Blocks.WHITE_WOOL)
                .addCriterion("has_item0", hasItem(Blocks.RED_WOOL))
                .addCriterion("has_item1", hasItem(Blocks.WHITE_WOOL))
                .build(consumer);

        this.addSweaterRecipe(ModItems.sweaterTree, Items.RED_WOOL, Items.GREEN_WOOL, Items.GREEN_WOOL, consumer);
        this.addSweaterRecipe(ModItems.sweaterReindeer, Items.RED_WOOL, Items.BLACK_WOOL, Items.BROWN_WOOL, consumer);
        this.addSweaterRecipe(ModItems.sweaterSnowflake, Items.GREEN_WOOL, Items.WHITE_WOOL, Items.GREEN_WOOL, consumer);
        this.addSweaterRecipe(ModItems.sweaterSnowman, Items.GREEN_WOOL, Items.ORANGE_WOOL, Items.WHITE_WOOL, consumer);
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

    private <T extends IItemProvider> void addColoredRecipes(Colored<T> result, BiConsumer<T, DyeColor> builder) {
        for (DyeColor dc : DyeColor.values()) {
            builder.accept(result.get(dc), dc);
        }
    }

    private void addSweaterRecipe(ItemSweater sweater, Item baseColor, Item wool1, Item wool2, @Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(sweater)
                .patternLine("c c")
                .patternLine("cac")
                .patternLine("cbc")
                .key('a', wool1)
                .key('b', wool2)
                .key('c', baseColor)
                .addCriterion("has_item", hasItem(baseColor))
                .build(consumer);
    }
}
