package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
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

public class BlockOven extends BlockGUI<TileOven, ContainerOven> {

    private static final DirectionShape shape = new DirectionShape(VoxelShapes.or(
            makeCuboidShape(14, 0, 3, 15, 10, 15),
            makeCuboidShape(1, 0, 3, 2, 10, 15),
            makeCuboidShape(2, 0, 14, 14, 10, 15),
            makeCuboidShape(2, 1, 4, 14, 2, 14),
            makeCuboidShape(0, 10, 2, 16, 11, 16),
            makeCuboidShape(2, 3, 4, 2.25, 4, 14),
            makeCuboidShape(13.75, 3, 4, 14, 4, 14),
            makeCuboidShape(13.75, 6, 4, 14, 7, 14),
            makeCuboidShape(2, 6, 4, 2.25, 7, 14)
    ));

    public BlockOven(ModX mod, Class<TileOven> teClass, ContainerType<ContainerOven> container, Properties properties) {
        super(mod, teClass, container, properties);
    }

    public BlockOven(ModX mod, Class<TileOven> teClass, ContainerType<ContainerOven> container, Properties properties, Item.Properties itemProperties) {
        super(mod, teClass, container, properties, itemProperties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
        ScreenManager.registerFactory(this.container, ScreenOven::new);
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), RenderOven::new);
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
