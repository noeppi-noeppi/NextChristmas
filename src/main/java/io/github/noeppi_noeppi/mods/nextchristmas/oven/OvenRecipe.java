package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OvenRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient input;
    private final int cookTime;

    public OvenRecipe(ResourceLocation id, ItemStack output, Ingredient input, int cookTime) {
        this.id = Objects.requireNonNull(id);
        this.output = Objects.requireNonNull(output);
        this.input = Objects.requireNonNull(input);
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(@Nonnull IInventory inv, @Nonnull World worldIn) {
        return RecipeHelper.matches(this, IntStream.range(0, inv.getSizeInventory()).boxed().map(inv::getStackInSlot).collect(Collectors.toList()), false);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.OVEN_SERIALIZER;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(Ingredient.EMPTY, this.input);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
        return this.output;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.OVEN;
    }

    @Nonnull
    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.oven);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<OvenRecipe> {

        @Nonnull
        @Override
        public OvenRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
            Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            int cookTime = JSONUtils.getInt(json, "cookTime");
            return new OvenRecipe(recipeId, output, input, cookTime);
        }

        @Nullable
        @Override
        public OvenRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            ItemStack output = buffer.readItemStack();
            Ingredient input = Ingredient.read(buffer);
            int cookTime = buffer.readInt();
            return new OvenRecipe(recipeId, output, input, cookTime);
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, @Nonnull OvenRecipe recipe) {
            buffer.writeItemStack(recipe.output);
            recipe.input.write(buffer);
            buffer.writeInt(recipe.cookTime);
        }
    }
}
