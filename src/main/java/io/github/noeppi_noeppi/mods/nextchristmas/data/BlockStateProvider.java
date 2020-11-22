package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockStateProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.BlockBakingSheet;
import io.github.noeppi_noeppi.mods.nextchristmas.decoration.BlockCandle;
import io.github.noeppi_noeppi.mods.nextchristmas.decoration.BlockChristmasBall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class BlockStateProvider extends BlockStateProviderBase {

    public static final ResourceLocation BAKING_SHEET_PARENT = new ResourceLocation(NextChristmas.getInstance().modid, "block/base/baking_sheet");
    public static final ResourceLocation CHRISTMAS_BALL_PARENT = new ResourceLocation(NextChristmas.getInstance().modid, "block/base/christmas_tree_ball");
    public static final ResourceLocation CANDLE_PARENT = new ResourceLocation(NextChristmas.getInstance().modid, "block/base/candle");

    public BlockStateProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        this.manualModel(ModBlocks.oven);
        this.manualModel(ModBlocks.grainMill);
        this.manualModel(ModBlocks.star, models().getBuilder(Objects.requireNonNull(ModBlocks.star.getRegistryName()).getPath()).texture("particle", new ResourceLocation("minecraft", "block/gold_block")));
    }

    @Override
    protected ModelFile defaultModel(ResourceLocation id, Block block) {
        if (block instanceof BlockBakingSheet) {
            return this.models().withExistingParent(id.getPath(), BAKING_SHEET_PARENT);
        } else if (block instanceof BlockChristmasBall) {
            return this.models().withExistingParent(id.getPath(), CHRISTMAS_BALL_PARENT)
                    .texture("color", new ResourceLocation("minecraft", "block/" + ((BlockChristmasBall) block).color.getString() + "_glazed_terracotta"));
        } else if (block instanceof BlockCandle) {
            return this.models().withExistingParent(id.getPath(), CANDLE_PARENT)
                    .texture("color", new ResourceLocation("minecraft", "block/" + ((BlockCandle) block).color.getString() + "_concrete"));
        } else {
            return super.defaultModel(id, block);
        }
    }
}