package io.github.noeppi_noeppi.mods.nextchristmas.mill;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
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

public class MillRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient input;
    private final int millClicks;

    public MillRecipe(ResourceLocation id, ItemStack output, Ingredient input, int millClicks) {
        this.id = Objects.requireNonNull(id);
        this.output = Objects.requireNonNull(output);
        this.input = Objects.requireNonNull(input);
        this.millClicks = millClicks;
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

    public int getMillClicks() {
        return this.millClicks;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.MILL;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MillRecipe> {

        @Nonnull
        @Override
        public MillRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
            Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            int millClicks = JSONUtils.getInt(json, "millClicks");
            return new MillRecipe(recipeId, output, input, millClicks);
        }

        @Nullable
        @Override
        public MillRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            ItemStack output = buffer.readItemStack();
            Ingredient input = Ingredient.read(buffer);
            int millClicks = buffer.readInt();
            return new MillRecipe(recipeId, output, input, millClicks);
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, @Nonnull MillRecipe recipe) {
            buffer.writeItemStack(recipe.output);
            recipe.input.write(buffer);
            buffer.writeInt(recipe.millClicks);
        }
    }
}
