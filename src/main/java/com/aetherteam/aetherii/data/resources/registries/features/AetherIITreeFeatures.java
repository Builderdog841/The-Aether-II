package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.tree.foliage.AmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.LargeSkyrootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.WisprootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.WisptopFoliagePlacer;
import com.aetherteam.aetherii.world.tree.trunk.AmberootTrunkPlacer;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class AetherIITreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT = AetherIIFeatureUtils.registerKey("skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT = AetherIIFeatureUtils.registerKey("large_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT = AetherIIFeatureUtils.registerKey("wisproot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP = AetherIIFeatureUtils.registerKey("wisptop");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBEROOT = AetherIIFeatureUtils.registerKey("amberoot");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        AetherIIFeatureUtils.register(context, SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, LARGE_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, WISPROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, WISPTOP, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new AmberootTrunkPlacer(8, 5, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new AmberootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
    }
}