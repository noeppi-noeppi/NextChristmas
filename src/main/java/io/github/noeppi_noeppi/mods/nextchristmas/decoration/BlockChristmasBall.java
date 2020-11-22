package io.github.noeppi_noeppi.mods.nextchristmas.decoration;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.util.BlockColored;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockChristmasBall extends BlockColored {

    private static final DirectionShape shape = new DirectionShape(VoxelShapes.or(
            makeCuboidShape(7.75, 13.5, 7.75, 8.25, 16, 8.25),
            makeCuboidShape(7.75, 12.75, 8.25, 8.25, 13.75, 8.75),
            makeCuboidShape(7.75, 12.75, 7.25, 8.25, 13.75, 7.75),
            makeCuboidShape(7.75, 13.75, 8.25, 8.25, 14, 8.5),
            makeCuboidShape(7.75, 13.75, 7.5, 8.25, 14, 7.75),
            makeCuboidShape(6, 12, 6, 10, 12.75, 10),
            makeCuboidShape(6, 5.25, 6, 10, 6, 10),
            makeCuboidShape(11, 7, 6, 11.75, 11, 10),
            makeCuboidShape(6, 7, 11, 10, 11, 11.75),
            makeCuboidShape(4.25, 7, 6, 5, 11, 10),
            makeCuboidShape(6, 7, 4.25, 10, 11, 5),
            makeCuboidShape(10, 6, 5, 11, 11, 6),
            makeCuboidShape(10, 6, 10, 11, 11, 11),
            makeCuboidShape(5, 6, 5, 6, 11, 6),
            makeCuboidShape(5, 6, 10, 6, 11, 11),
            makeCuboidShape(5, 11, 5, 11, 12, 6),
            makeCuboidShape(6, 6, 5, 10, 7, 6),
            makeCuboidShape(5, 11, 6, 6, 12, 11),
            makeCuboidShape(5, 6, 6, 6, 7, 10),
            makeCuboidShape(10, 11, 6, 11, 12, 11),
            makeCuboidShape(10, 6, 6, 11, 7, 10),
            makeCuboidShape(6, 11, 10, 10, 12, 11),
            makeCuboidShape(6, 11, 11, 10, 11.5, 11.5),
            makeCuboidShape(5.5, 7, 11, 6, 11, 11.5),
            makeCuboidShape(5.5, 7, 4.5, 6, 11, 5),
            makeCuboidShape(6, 6.5, 11, 10, 7, 11.5),
            makeCuboidShape(10, 7, 11, 10.5, 11, 11.5),
            makeCuboidShape(10, 7, 4.5, 10.5, 11, 5),
            makeCuboidShape(4.5, 11, 6, 5, 11.5, 10),
            makeCuboidShape(11, 6.5, 6, 11.5, 7, 10),
            makeCuboidShape(6, 11, 4.5, 10, 11.5, 5),
            makeCuboidShape(5.5, 6.5, 4.5, 10, 7, 5),
            makeCuboidShape(11, 11, 6, 11.5, 11.5, 10),
            makeCuboidShape(4.5, 6.5, 5.5, 5, 7, 10),
            makeCuboidShape(6, 12, 10, 10, 12.5, 10.5),
            makeCuboidShape(4.5, 7, 10, 5, 11, 10.5),
            makeCuboidShape(4.5, 7, 5.5, 5, 11, 6),
            makeCuboidShape(6, 5.5, 10, 10, 6, 10.5),
            makeCuboidShape(11, 7, 10, 11.5, 11, 10.5),
            makeCuboidShape(11, 7, 5.5, 11.5, 11, 6),
            makeCuboidShape(5.5, 12, 6, 6, 12.5, 10),
            makeCuboidShape(10, 5.5, 6, 10.5, 6, 10),
            makeCuboidShape(6, 12, 5.5, 10, 12.5, 6),
            makeCuboidShape(6, 5.5, 5.5, 10, 6, 6),
            makeCuboidShape(10, 12, 6, 10.5, 12.5, 10),
            makeCuboidShape(5.5, 5.5, 6, 6, 6, 10),
            makeCuboidShape(6, 6, 10, 10, 7, 11)
    ));

    public BlockChristmasBall(ModX mod, DyeColor color, Properties properties) {
        super(mod, color, "block.next_christmas.christmas_ball", properties);
    }

    public BlockChristmasBall(ModX mod, DyeColor color, Properties properties, Item.Properties itemProperties) {
        super(mod, color, "block.next_christmas.christmas_ball", properties, itemProperties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
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
}
