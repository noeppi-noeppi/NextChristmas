package io.github.noeppi_noeppi.mods.nextchristmas.data.recipes;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.data.CraftingHelper2;
import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class MillRecipeBuilder {

    private final ItemStack output;
    private Ingredient input = null;
    private int millClicks = 1;

    public MillRecipeBuilder(IItemProvider output) {
        this(new ItemStack(output));
    }

    public MillRecipeBuilder(ItemStack output) {
        this.output = Objects.requireNonNull(output);
    }

    public MillRecipeBuilder setInput(IItemProvider input) {
        return this.setInput(Ingredient.fromItems(input));
    }

    public MillRecipeBuilder setInput(Ingredient input) {
        this.input = input;
        return this;
    }

    public MillRecipeBuilder setMillClicks(int millClicks) {
        this.millClicks = millClicks;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, this.output.getItem().getRegistryName());
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        if (this.input == null)
            throw new IllegalStateException("Tried to build unfinished NextChristmas Mill Recipe.");
        consumerIn.accept(new FinishedRecipe(new ResourceLocation(id.getNamespace(), NextChristmas.getInstance().modid + "_mill/" + id.getPath())));
    }

    private class FinishedRecipe implements IFinishedRecipe {

        private final ResourceLocation id;

        private FinishedRecipe(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serialize(@Nonnull JsonObject json) {
            json.add("output", CraftingHelper2.serializeItemStack(MillRecipeBuilder.this.output, true));
            json.add("input", MillRecipeBuilder.this.input.serialize());
            json.addProperty("millClicks", MillRecipeBuilder.this.millClicks);
        }

        @Nonnull
        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipes.MILL_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
