package io.github.noeppi_noeppi.mods.nextchristmas.mill;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockMill extends BlockTE<TileMill> {

    private static final DirectionShape shape = new DirectionShape(VoxelShapes.or(
            makeCuboidShape(4, 0, 3, 12, 1, 12),
            makeCuboidShape(3, 1, 3, 4, 8, 12),
            makeCuboidShape(4, 1, 12, 12, 8, 13),
            makeCuboidShape(12, 1, 3, 13, 8, 12),
            makeCuboidShape(4, 8, 11, 12, 9, 12),
            makeCuboidShape(4, 15, 11, 12, 16, 12),
            makeCuboidShape(4, 22, 4, 12, 22.5, 12),
            makeCuboidShape(4, 8, 4, 12, 9, 5),
            makeCuboidShape(4, 15, 4, 12, 16, 5),
            makeCuboidShape(4, 7, 3, 12, 8, 4),
            makeCuboidShape(4, 8, 5, 5, 9, 11),
            makeCuboidShape(4, 15, 5, 5, 16, 11),
            makeCuboidShape(11, 8, 5, 12, 9, 11),
            makeCuboidShape(11, 15, 5, 12, 16, 11),
            makeCuboidShape(5, 9, 5, 11, 15, 6),
            makeCuboidShape(5, 9, 6, 6, 15, 10),
            makeCuboidShape(5, 9, 10, 11, 15, 11),
            makeCuboidShape(10, 9, 6, 11, 15, 10),
            makeCuboidShape(3.75, 16, 3.75, 4.25, 22, 4.25),
            makeCuboidShape(3.75, 16, 11.75, 4.25, 22, 12.25),
            makeCuboidShape(11.75, 16, 3.75, 12.25, 22, 4.25),
            makeCuboidShape(11.75, 16, 11.75, 12.25, 22, 12.25),
            makeCuboidShape(4.25, 16, 4, 11.75, 22, 4.25),
            makeCuboidShape(11.75, 16, 4.25, 12, 22, 11.75),
            makeCuboidShape(4, 16, 4.25, 4.25, 22, 11.75),
            makeCuboidShape(4.25, 16, 11.75, 11.75, 22, 12)
    ));

    public BlockMill(ModX mod, Class<TileMill> teClass, Properties properties) {
        super(mod, teClass, properties);
    }

    public BlockMill(ModX mod, Class<TileMill> teClass, Properties properties, Item.Properties itemProperties) {
        super(mod, teClass, properties, itemProperties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), RenderMill::new);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return shape.getShape(state.get(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileMill tile = this.getTile(world, pos);
            double height = hit.getHitVec().y - pos.getY();
            if (height < (9 / 16d)) {
                return tile.itemClick(player, hand, TileMill.SLOT_OUT) ? ActionResultType.CONSUME : ActionResultType.CONSUME;
            } else if (height < (15 / 16d)) {
                return tile.startMilling(player, hand) ? ActionResultType.CONSUME : ActionResultType.CONSUME;
            } else {
                return tile.itemClick(player, hand, TileMill.SLOT_IN) ? ActionResultType.CONSUME : ActionResultType.CONSUME;
            }
        } else {
            return ActionResultType.CONSUME;
        }
    }
}
