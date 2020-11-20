package io.github.noeppi_noeppi.mods.nextchristmas.jei;

import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.mill.MillRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MillCategory implements IRecipeCategory<MillRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(NextChristmas.getInstance().modid, "mill_category");

    private final IDrawable background;
    private final IDrawable icon;
    private final String name;

    public MillCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(NextJei.RECIPE_GUI_VANILLA, 0, 224, 82, 26);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.grainMill));
        this.name = I18n.format("jei.next_christmas.category.mill");
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Nonnull
    @Override
    public Class<? extends MillRecipe> getRecipeClass() {
        return MillRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return this.name;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(@Nonnull MillRecipe recipe, @Nonnull IIngredients ii) {
        ii.setInputIngredients(recipe.getIngredients());
        ii.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull MillRecipe recipe, @Nonnull IIngredients ii) {
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 4);
        stacks.init(1, false, 60, 4);
        stacks.set(ii);
    }
}
