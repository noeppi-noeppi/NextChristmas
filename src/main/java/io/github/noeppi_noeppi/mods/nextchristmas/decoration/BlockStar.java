package io.github.noeppi_noeppi.mods.nextchristmas.decoration;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.mods.nextchristmas.util.ItemStackRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockStar extends BlockTE<TileEntityBase> {

    public static final VoxelShape shape = makeCuboidShape(1, 1, 1, 15, 15, 15);

    public BlockStar(ModX mod, Properties properties) {
        this(mod, properties, new Item.Properties());
    }

    public BlockStar(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, TileEntityBase.class, properties.setLightLevel(state -> 15), itemProperties.setISTER(() -> ItemStackRenderer::get));
    }

    @Override
    public void registerClient(ResourceLocation id) {
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), RenderStar::new);
        ItemStackRenderer.addRenderTile(this.getTileType());
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return shape;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getRenderShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        return VoxelShapes.empty();
    }
}
