package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.oven.OvenRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;

public class ModRecipes {

    public static final IRecipeType<OvenRecipe> OVEN = IRecipeType.register(NextChristmas.getInstance().modid + "_oven");

    public static final IRecipeSerializer<OvenRecipe> OVEN_SERIALIZER = new OvenRecipe.Serializer();

    public static void register() {
        NextChristmas.getInstance().register(OVEN.toString(), OVEN_SERIALIZER);
    }
}
