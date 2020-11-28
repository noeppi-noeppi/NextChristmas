package io.github.noeppi_noeppi.mods.nextchristmas.biscuit;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.util.ItemStackRenderer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class BlockBakingSheet extends BlockTE<TileEntityBase> {

    private final ItemStack fillStack;
    private final boolean big;

    private static final DirectionShape shape = new DirectionShape(VoxelShapes.or(
            makeCuboidShape(2.5, 0, 4, 13.5, 0.25, 13.5),
            makeCuboidShape(2, 0, 13.5, 14, 0.75, 14),
            makeCuboidShape(2, 0, 4, 2.5, 0.75, 13.5),
            makeCuboidShape(13.5, 0, 4, 14, 0.75, 13.5)
    ));

    public BlockBakingSheet(ModX mod, IItemProvider fillStack, boolean big) {
        this(mod, new ItemStack(fillStack), big);
    }

    public BlockBakingSheet(ModX mod, ItemStack fillStack, boolean big) {
        this(mod, fillStack, big, new Item.Properties());
    }

    public BlockBakingSheet(ModX mod, IItemProvider fillStack, boolean big, Item.Properties itemProperties) {
        this(mod, new ItemStack(fillStack), big, itemProperties);
    }

    public BlockBakingSheet(ModX mod, ItemStack fillStack, boolean big, Item.Properties itemProperties) {
        super(mod, TileEntityBase.class, AbstractBlock.Properties.create(Material.IRON), withContainerAndRender(!fillStack.isEmpty(), itemProperties));
        this.fillStack = fillStack;
        this.big = big;
    }

    @Override
    @OnlyIn(Dist.CLIENT) // Weird but without it it crashed event though Registration#registerClient is `onlyIn`
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
        if (!this.fillStack.isEmpty()) {
            ClientRegistry.bindTileEntityRenderer(this.getTileType(), dispatcher -> new RenderBakingSheet(dispatcher, this));
            ItemStackRenderer.addRenderTile(this.getTileType());
        }
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

    public ItemStack getFillStack() {
        return this.fillStack;
    }

    public boolean isBig() {
        return this.big;
    }

    private static Item.Properties withContainerAndRender(boolean full, Item.Properties properties) {
        if (full) {
            Item container;
            try {
                Field field = BlockBase.class.getDeclaredField("item");
                field.setAccessible(true);
                container = (Item) field.get(ModBlocks.bakingSheet);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Could not access ItemBase#item via reflection to set baking sheet container item.", e);
            }
            return properties.setISTER(() -> ItemStackRenderer::get).maxStackSize(1).containerItem(container);
        } else {
            return properties.maxStackSize(4);
        }
    }
}
