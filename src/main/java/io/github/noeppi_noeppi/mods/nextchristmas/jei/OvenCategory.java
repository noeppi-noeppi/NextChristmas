package io.github.noeppi_noeppi.mods.nextchristmas.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.OvenRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class OvenCategory implements IRecipeCategory<OvenRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(NextChristmas.getInstance().modid, "oven_category");

    private final IDrawable background;
    private final IDrawable icon;
    private final String name;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated flame;

    public OvenCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(NextJei.RECIPE_GUI_VANILLA, 0, 114, 82, 54);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.oven));
        this.name = I18n.format("jei.next_christmas.category.oven");
        this.arrow = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(NextJei.RECIPE_GUI_VANILLA, 82, 128, 24, 17), 300, IDrawableAnimated.StartDirection.LEFT, false);
        this.flame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(NextJei.RECIPE_GUI_VANILLA, 82, 114, 14, 14), 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Nonnull
    @Override
    public Class<? extends OvenRecipe> getRecipeClass() {
        return OvenRecipe.class;
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
    public void setIngredients(@Nonnull OvenRecipe recipe, @Nonnull IIngredients ii) {
        ii.setInputIngredients(recipe.getIngredients());
        ii.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull OvenRecipe recipe, @Nonnull IIngredients ii) {
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 0);
        stacks.init(1, false, 60, 18);
        stacks.set(ii);
    }

    @Override
    public void draw(@Nonnull OvenRecipe recipe, @Nonnull MatrixStack matrixStack, double mouseX, double mouseY) {
        this.flame.draw(matrixStack, 1, 20);
        this.arrow.draw(matrixStack, 24, 18);

        int cookTime = recipe.getCookTime() / 20;
        int seconds = cookTime % 60;
        int minutes = cookTime / 60;
        String text;
        if (seconds == 0) {
            text = minutes + " min";
        } else {
            if (minutes == 0) {
                text = seconds + " s";
            } else {
                text = minutes + " min " + seconds + " s";
            }
        }
        IFormattableTextComponent tc = new StringTextComponent(text);

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = fontRenderer.getStringPropertyWidth(tc);
        fontRenderer.func_243248_b(matrixStack, tc, this.background.getWidth() - width, 45, 0xFF808080);
    }
}
