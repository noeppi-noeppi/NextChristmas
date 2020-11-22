package io.github.noeppi_noeppi.mods.nextchristmas.decoration;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.util.BlockColored;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockCandle extends BlockColored {

    private static final VoxelShape shape = VoxelShapes.or(
            makeCuboidShape(5.75, 0, 5.5, 10.5, 12, 10.5),
            makeCuboidShape(7.75, 12, 7.75, 8.25, 14.5, 8.25)
    );

    public BlockCandle(ModX mod, DyeColor color, Properties properties) {
        super(mod, color, "block.next_christmas.candle", properties.setLightLevel(state -> state.get(BlockStateProperties.LIT) ? 15 : 0));
    }

    public BlockCandle(ModX mod, DyeColor color, Properties properties, Item.Properties itemProperties) {
        super(mod, color, "block.next_christmas.candle", properties.setLightLevel(state -> state.get(BlockStateProperties.LIT) ? 15 : 0), itemProperties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.LIT, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return shape;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL) {
            if (!state.get(BlockStateProperties.LIT)) {
                world.setBlockState(pos, state.with(BlockStateProperties.LIT, true), 3);
                ItemStack stack = player.getHeldItem(hand);
                stack.damageItem(1, player, t -> {});
                player.swing(hand, true);
            }
            return ActionResultType.CONSUME;
        } else {
            return super.onBlockActivated(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (state.get(BlockStateProperties.LIT)) {
            world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0);
            world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0);
        }
    }
}
