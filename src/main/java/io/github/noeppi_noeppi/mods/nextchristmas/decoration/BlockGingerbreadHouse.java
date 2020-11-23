package io.github.noeppi_noeppi.mods.nextchristmas.decoration;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.block.CakeBlock.BITES;

public class BlockGingerbreadHouse extends BlockBase {

    private static final VoxelShape[] SHAPES = new VoxelShape[] {
            makeCuboidShape(2, 0, 2, 14, 22, 14),
            makeCuboidShape(2, 0, 2, 14, 12, 14),
            makeCuboidShape(2, 0, 2, 14, 12, 14),
            makeCuboidShape(2, 0, 2, 14, 12, 14),
            makeCuboidShape(2, 0, 2, 14, 12, 14),
            makeCuboidShape(2, 0, 2, 14, 7.25, 14),
            makeCuboidShape(2, 0, 2, 14, 1, 14)
    };

    public BlockGingerbreadHouse(ModX mod, Properties properties) {
        super(mod, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
    }

    public BlockGingerbreadHouse(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, properties, itemProperties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.BITES_0_6, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);
            if (this.eatPart(world, pos, state, player).isSuccessOrConsume()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eatPart(world, pos, state, player);
    }

    private ActionResultType eatPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            player.addStat(Stats.EAT_CAKE_SLICE);
            player.getFoodStats().addStats(2, 0.1F);
            int i = state.get(BITES);
            if (i < 6) {
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                world.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPES[state.get(BITES)];
    }

    @SuppressWarnings("deprecation")
    public int getComparatorInputOverride(BlockState state, @Nonnull World world, @Nonnull BlockPos pos) {
        return (7 - state.get(BlockStateProperties.BITES_0_6)) * 2;
    }

    @SuppressWarnings("deprecation")
    public boolean hasComparatorInputOverride(@Nonnull BlockState state) {
        return true;
    }
}
