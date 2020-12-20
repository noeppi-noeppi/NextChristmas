package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.ItemBiscuit;
import io.github.noeppi_noeppi.mods.nextchristmas.player.ItemSweater;
import io.github.noeppi_noeppi.mods.nextchristmas.util.Colored;
import io.github.noeppi_noeppi.mods.nextchristmas.util.EnumValues;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "CodeBlock2Expr"})
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
        new MillRecipeBuilder(ModItems.cinnamon).setInput(ModItems.cinnamonBark).build(consumer);

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
                .addIngredient(ModItems.crushedNut)
                .addIngredient(ModItems.crushedNut)
                .addIngredient(Items.MILK_BUCKET)
                .addCriterion("has_item0", hasItem(ModItems.flour))
                .addCriterion("has_item1", hasItem(Items.SUGAR))
                .addCriterion("has_item2", hasItem(Items.EGG))
                .addCriterion("has_item3", hasItem(ModItems.crushedNut))
                .addCriterion("has_item4", hasItem(Items.MILK_BUCKET))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.speculaas.dough, 8)
                .addIngredient(ModItems.flour)
                .addIngredient(ModItems.flour)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .addIngredient(ModItems.cinnamon)
                .addIngredient(ModItems.cinnamon)
                .addIngredient(ModItems.vanilla)
                .addIngredient(Items.MILK_BUCKET)
                .addCriterion("has_item0", hasItem(ModItems.flour))
                .addCriterion("has_item1", hasItem(Items.SUGAR))
                .addCriterion("has_item2", hasItem(Items.EGG))
                .addCriterion("has_item3", hasItem(ModItems.hazelnut))
                .addCriterion("has_item4", hasItem(Items.MILK_BUCKET))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.cinnamonStar.dough, 8)
                .addIngredient(ModItems.flour)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .addIngredient(ModItems.cinnamon)
                .addIngredient(ModItems.cinnamon)
                .addIngredient(ModItems.crushedNut)
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

        this.addEnumRecipes(ModItems.sledge, (result, type) -> {
            ShapedRecipeBuilder.shapedRecipe(result)
                    .patternLine("ppp")
                    .patternLine("sse")
                    .patternLine("eee")
                    .key('p', type.material)
                    .key('s', Items.STICK)
                    .key('e', Tags.Items.INGOTS_IRON)
                    .addCriterion("has_item0", hasItem(type.material))
                    .addCriterion("has_item1", hasItem(Items.STICK))
                    .addCriterion("has_item2", hasItem(Tags.Items.INGOTS_IRON))
                    .build(consumer);
        });

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.oven)
                .patternLine("win")
                .patternLine("ter")
                .patternLine("jam")
                .key('w', Blocks.BLACK_CONCRETE)
                .key('i', Blocks.BLACK_CONCRETE)
                .key('n', Blocks.BLACK_CONCRETE)
                .key('t', Blocks.WHITE_CONCRETE)
                .key('e', Tags.Items.GLASS_PANES_COLORLESS)
                .key('r', Blocks.WHITE_CONCRETE)
                .key('j', Blocks.WHITE_CONCRETE)
                .key('a', Ingredient.fromItems(Items.CAMPFIRE, Items.SOUL_CAMPFIRE))
                .key('m', Blocks.WHITE_CONCRETE)
                .addCriterion("has_item0", hasItem(Blocks.BLACK_CONCRETE))
                .addCriterion("has_item1", hasItem(Blocks.WHITE_CONCRETE))
                .addCriterion("has_item2", hasItem(Tags.Items.GLASS_PANES))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.grainMill)
                .patternLine("hgh")
                .patternLine("psp")
                .patternLine("p p")
                .key('p', ItemTags.PLANKS)
                .key('h', ItemTags.WOODEN_SLABS)
                .key('s', Items.STICK)
                .key('g', Tags.Items.GLASS_COLORLESS)
                .addCriterion("has_item0", hasItem(ItemTags.PLANKS))
                .addCriterion("has_item1", hasItem(ItemTags.WOODEN_SLABS))
                .addCriterion("has_item2", hasItem(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.bakingSheet, 4)
                .patternLine("ccc")
                .key('c', Blocks.BLACK_CONCRETE)
                .addCriterion("has_item0", hasItem(Blocks.BLACK_CONCRETE))
                .build(consumer);
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

    @SuppressWarnings("SameParameterValue")
    private <E extends Enum<E>, T extends IItemProvider> void addEnumRecipes(EnumValues<E, T> result, BiConsumer<T, E> builder) {
        for (E enumValue : result.map.keySet()) {
            builder.accept(result.map.get(enumValue), enumValue);
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
