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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ItemStackRenderer extends ItemStackTileEntityRenderer {

    private static final ItemStackRenderer INSTANCE = new ItemStackRenderer();

    private static final List<TileEntityType<?>> types = new LinkedList<>();
    private static final Map<TileEntityType<?>, LazyValue<TileEntity>> tiles = new HashMap<>();

    private ItemStackRenderer() {
        super();
    }

    public static <T extends TileEntity> void addRenderTile(TileEntityType<T> teType) {
        types.add(teType);
        tiles.put(teType, new LazyValue<>(teType::create));
    }

    @Override
    public void func_239207_a_(ItemStack stack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            for (TileEntityType<?> teType : types) {
                if (teType.isValidBlock(block)) {
                    TileEntity tile = tiles.get(teType).getValue();
                    TileEntityRenderer<TileEntity> renderer = TileEntityRendererDispatcher.instance.getRenderer(tile);
                    if (renderer != null) {
                        if (Minecraft.getInstance().world != null) {
                            tile.setWorldAndPos(Minecraft.getInstance().world, BlockPos.ZERO);
                        }
                        tile.cachedBlockState = block.getDefaultState();

                        matrixStack.push();

                        rotateView(matrixStack, type);

                        BlockState state = block.getDefaultState();
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

    public static void rotateView(MatrixStack matrixStack, ItemCameraTransforms.TransformType type) {
        if (type == ItemCameraTransforms.TransformType.GUI || type == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND
                || type == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                || type == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND
                || type == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
            matrixStack.translate(0.5, 0, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
            matrixStack.translate(-0.5, 0, -0.5);
        }
    }

    public static ItemStackRenderer get() {
        return INSTANCE;
    }
}
