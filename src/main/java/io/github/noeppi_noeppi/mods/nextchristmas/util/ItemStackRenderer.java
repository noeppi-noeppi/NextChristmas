package io.github.noeppi_noeppi.mods.nextchristmas.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ItemStackRenderer extends ItemStackTileEntityRenderer {

    private static final ItemStackRenderer INSTANCE = new ItemStackRenderer();

    private static final List<TileEntityType<?>> types = new LinkedList<>();
    private static final Map<TileEntityType<?>, Pair<LazyValue<TileEntity>, Boolean>> tiles = new HashMap<>();
    private static final Map<TileEntityType<?>, CompoundNBT> defaultTags = new HashMap<>();

    private ItemStackRenderer() {
        super();
    }

    public static <T extends TileEntity> void addRenderTile(TileEntityType<T> teType) {
        types.add(teType);
        tiles.put(teType, Pair.of(new LazyValue<>(teType::create), false));
    }
    
    public static <T extends TileEntity> void addRenderTile(TileEntityType<T> teType, boolean readBlockEntityTag) {
        types.add(teType);
        tiles.put(teType, Pair.of(new LazyValue<>(teType::create), readBlockEntityTag));
    }

    @Override
    public void func_239207_a_(ItemStack stack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            for (TileEntityType<?> teType : types) {
                if (teType.isValidBlock(block)) {
                    Pair<LazyValue<TileEntity>, Boolean> pair = tiles.get(teType);
                    BlockState state = block.getDefaultState();
                    TileEntity tile = pair.getLeft().getValue();
                    
                    TileEntityRenderer<TileEntity> renderer = TileEntityRendererDispatcher.instance.getRenderer(tile);
                    if (renderer != null) {

                        if (pair.getRight()) {
                            if (!defaultTags.containsKey(teType)) {
                                setWorldPosState(tile, state);
                                defaultTags.put(teType, tile.write(new CompoundNBT()));
                            }
                            
                            CompoundNBT nbt = stack.getTag();
                            setWorldPosState(tile, state);
                            tile.read(state, defaultTags.get(teType));
                            if (nbt != null && nbt.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
                                CompoundNBT blockTag = nbt.getCompound("BlockEntityTag");
                                tile.read(state, blockTag);
                            }
                        }
                        
                        if (Minecraft.getInstance().world != null) {
                            tile.setWorldAndPos(Minecraft.getInstance().world, BlockPos.ZERO);
                        }
                        tile.cachedBlockState = state;

                        matrixStack.push();
                        
                        if (state.getRenderType() != BlockRenderType.ENTITYBLOCK_ANIMATED) {
                            //noinspection deprecation
                            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(block.getDefaultState(), matrixStack, buffer, light, overlay);
                        }
                        renderer.render(tile, Minecraft.getInstance().getRenderPartialTicks(), matrixStack, buffer, light, overlay);

                        matrixStack.pop();

                        break;
                    }
                }
            }
        }
    }

    private static void setWorldPosState(TileEntity tile, BlockState state) {
        if (Minecraft.getInstance().world != null) {
            tile.setWorldAndPos(Minecraft.getInstance().world, BlockPos.ZERO);
        }
        tile.cachedBlockState = state;
    }

    public static ItemStackRenderer get() {
        return INSTANCE;
    }
}
