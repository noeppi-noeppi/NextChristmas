package io.github.noeppi_noeppi.mods.nextchristmas.biome;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class ChristmasTree extends Tree implements Registerable {

    public final Feature<BaseTreeFeatureConfig> feature = new ChristmasTreeFeature(BaseTreeFeatureConfig.CODEC);
    public final ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredFeature = this.feature.withConfiguration(
            (new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(
                            Blocks.SPRUCE_LOG.getDefaultState()
                    ),
                    new SimpleBlockStateProvider(
                            Blocks.SPRUCE_LEAVES.getDefaultState()
                    ),
                    new SpruceFoliagePlacer(
                            FeatureSpread.func_242253_a(2, 1),
                            FeatureSpread.func_242253_a(0, 2),
                            FeatureSpread.func_242253_a(1, 1)
                    ),
                    new StraightTrunkPlacer(5, 2, 1),
                    new TwoLayerFeature(2, 0, 2))
                    .setIgnoreVines()
                    .build()
            ));


    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(@Nonnull Random random, boolean hive) {
        return this.configuredFeature;
    }

    @Override
    public Set<Object> getAdditionalRegisters() {
        return ImmutableSet.of(this.feature);
    }

    private static class ChristmasTreeFeature extends Feature<BaseTreeFeatureConfig> {

        public ChristmasTreeFeature(Codec<BaseTreeFeatureConfig> codec) {
            super(codec);
        }

        @Override
        public boolean generate(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BaseTreeFeatureConfig config) {
            boolean success = false;
            for (int i = 0; i < 6; i++) {
                BlockPos.Mutable mpos = new BlockPos.Mutable(pos.getX() + random.nextInt(16), world.getHeight() - 1, pos.getZ() + random.nextInt(16));
                while ((world.isAirBlock(mpos) || world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos).getBlock() == Blocks.SNOW) && mpos.getY() > 50)
                    mpos.move(0, -1, 0);
                if (this.tryPlantTree(world, generator, random, mpos.toImmutable().up(), random.nextInt(3))) {
                    success = true;
                }
            }
            return success;
        }

        private boolean tryPlantTree(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random random, @Nonnull BlockPos pos, int size) {
            if (Objects.equals(world.getBiome(pos).getRegistryName(), ModWorldGen.christmasForest.getRegistryName())) {
                BlockState floor = world.getBlockState(pos.down());
                if (floor.getMaterial() != Material.EARTH && floor.getMaterial() != Material.ORGANIC) {
                    return false;
                }
                int topHeight = (size >= 2 ? 3 : 4) + (size * 2);
                int sideSpace = size >= 2 ? 2 : 1;
                int stemHeight = size >= 2 ? 3 : 2;
                for (int y = 0; y < stemHeight; y++) {
                    if (!world.isAirBlock(pos.add(0, y, 0)))
                        return false;
                }
                for (int x = 0; x < sideSpace; x++) {
                    for (int y = 0; y < topHeight; y++) {
                        for (int z = 0; z < sideSpace; z++) {
                            if (!world.isAirBlock(pos.add(x, stemHeight + y, z)))
                                return false;
                        }
                    }
                }
                this.plantTree(world, generator, random, pos, size);
                return true;
            } else {
                return false;
            }
        }

        private void plantTree(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random random, @Nonnull BlockPos pos, int size) {
            switch (size) {
                case 0:
                    this.placeLayer(world, pos.up(2), random, 2);
                    this.placeLayer(world, pos.up(3), random, 0);
                    this.placeStem(world, pos, random, 5);
                    break;
                case 1:
                    this.placeLayer(world, pos.up(2), random, 3);
                    this.placeLayer(world, pos.up(3), random, 2);
                    this.placeLayer(world, pos.up(4), random, 1);
                    this.placeLayer(world, pos.up(5), random, 0);
                    this.placeStem(world, pos, random, 7);
                case 2:
                default:
                    this.placeLayer(world, pos.up(3), random, 4);
                    this.placeLayer(world, pos.up(4), random, 3);
                    this.placeLayer(world, pos.up(5), random, 2);
                    this.placeLayer(world, pos.up(6), random, 1);
                    this.placeLayer(world, pos.up(7), random, 0);
                    this.placeStem(world, pos, random, 9);
                    break;
            }
        }

        private void placeStem(@Nonnull ISeedReader world, @Nonnull BlockPos basePos, @Nonnull Random random, int height) {
            BlockState state = Blocks.SPRUCE_LOG.getDefaultState().with(BlockStateProperties.AXIS, Direction.Axis.Y);
            for (int y = 0; y < height; y++) {
                world.setBlockState(basePos.up(y), state, 2);
            }
        }

        private void placeLayer(@Nonnull ISeedReader world, @Nonnull BlockPos basePos, @Nonnull Random random, int layer) {
            switch (layer) {
                case 0:
                    this.placeLeaves(world, basePos.add(-1, 0, 0), random);
                    this.placeLeaves(world, basePos.add(1, 0, 0), random);
                    this.placeLeaves(world, basePos.add(0, 0, -1), random);
                    this.placeLeaves(world, basePos.add(0, 0, 1), random);
                    this.placeLeaves(world, basePos.add(-1, 1, 0), random);
                    this.placeLeaves(world, basePos.add(1, 1, 0), random);
                    this.placeLeaves(world, basePos.add(0, 1, -1), random);
                    this.placeLeaves(world, basePos.add(0, 1, 1), random);
                    this.placeLeaves(world, basePos.add(0, 2, 0), random);
                    this.placeLeaves(world, basePos.add(0, 3, 0), random);
                    if (random.nextInt(500) == 0) {
                        world.setBlockState(basePos.up(4), ModBlocks.star.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, random.nextBoolean() ? Direction.NORTH : Direction.WEST), 2);
                    }
                    break;
                case 1:
                    this.placeLeaves(world, basePos.add(-1, 0, -1), random);
                    this.placeLeaves(world, basePos.add(-1, 0, 0), random);
                    this.placeLeaves(world, basePos.add(-1, 0, 1), random);
                    this.placeLeaves(world, basePos.add(0, 0, -1), random);
                    this.placeLeaves(world, basePos.add(0, 0, 1), random);
                    this.placeLeaves(world, basePos.add(1, 0, -1), random);
                    this.placeLeaves(world, basePos.add(1, 0, 0), random);
                    this.placeLeaves(world, basePos.add(1, 0, 1), random);
                    break;
                case 2:
                    this.placeLeaves(world, basePos.add(-1, 0, -1), random);
                    this.placeLeaves(world, basePos.add(-2, 0, 0), random);
                    this.placeLeaves(world, basePos.add(-1, 0, 1), random);
                    this.placeLeaves(world, basePos.add(0, 0, -2), random);
                    this.placeLeaves(world, basePos.add(0, 0, 2), random);
                    this.placeLeaves(world, basePos.add(1, 0, -1), random);
                    this.placeLeaves(world, basePos.add(2, 0, 0), random);
                    this.placeLeaves(world, basePos.add(1, 0, 1), random);
                    break;
                case 3:
                    this.placeLeaves(world, basePos.add(-2, 0, -1), random);
                    this.placeLeaves(world, basePos.add(-2, 0, 0), random);
                    this.placeLeaves(world, basePos.add(-2, 0, 1), random);
                    this.placeLeaves(world, basePos.add(2, 0, -1), random);
                    this.placeLeaves(world, basePos.add(2, 0, 0), random);
                    this.placeLeaves(world, basePos.add(2, 0, 1), random);
                    this.placeLeaves(world, basePos.add(-1, 0, -2), random);
                    this.placeLeaves(world, basePos.add(0, 0, -2), random);
                    this.placeLeaves(world, basePos.add(1, 0, -2), random);
                    this.placeLeaves(world, basePos.add(-1, 0, 2), random);
                    this.placeLeaves(world, basePos.add(0, 0, 2), random);
                    this.placeLeaves(world, basePos.add(1, 0, 2), random);
                    break;
                case 4:
                    this.placeLeaves(world, basePos.add(-3, 0, -1), random);
                    this.placeLeaves(world, basePos.add(-3, 0, 0), random);
                    this.placeLeaves(world, basePos.add(-3, 0, 1), random);
                    this.placeLeaves(world, basePos.add(3, 0, -1), random);
                    this.placeLeaves(world, basePos.add(3, 0, 0), random);
                    this.placeLeaves(world, basePos.add(3, 0, 1), random);
                    this.placeLeaves(world, basePos.add(-1, 0, -3), random);
                    this.placeLeaves(world, basePos.add(0, 0, -3), random);
                    this.placeLeaves(world, basePos.add(1, 0, -3), random);
                    this.placeLeaves(world, basePos.add(-1, 0, 3), random);
                    this.placeLeaves(world, basePos.add(0, 0, 3), random);
                    this.placeLeaves(world, basePos.add(1, 0, 3), random);
                    this.placeLeaves(world, basePos.add(-2, 0, -2), random);
                    this.placeLeaves(world, basePos.add(-2, 0, 2), random);
                    this.placeLeaves(world, basePos.add(2, 0, -2), random);
                    this.placeLeaves(world, basePos.add(2, 0, 2), random);
                    break;
            }
        }

        private void placeLeaves(@Nonnull ISeedReader world, @Nonnull BlockPos basePos, @Nonnull Random random) {
            BlockState state = Blocks.SPRUCE_LEAVES.getDefaultState().with(BlockStateProperties.PERSISTENT, true);
            BlockState snow = Blocks.SNOW.getDefaultState().with(BlockStateProperties.LAYERS_1_8, 1);
            world.setBlockState(basePos, state, 2);
            if (random.nextInt(2) == 0) {
                world.setBlockState(basePos.up(), snow, 2);
            }
            //noinspection deprecation
            if (world.getBlockState(basePos.down()).isAir() && random.nextInt(10) == 0) {
                world.setBlockState(basePos.down(), ModBlocks.christmasBall.random(random).getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, random.nextBoolean() ? Direction.NORTH : Direction.WEST), 2);
            }
            if (random.nextInt(10) == 0) {
                world.setBlockState(basePos.up(), ModBlocks.candle.random(random).getDefaultState().with(BlockStateProperties.LIT, true), 2);
            }
        }
    }
}
