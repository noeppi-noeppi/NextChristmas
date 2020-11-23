package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.state.properties.BlockStateProperties;

public class BlockLootProvider extends BlockLootProviderBase {

	public BlockLootProvider(ModX mod, DataGenerator generator) {
		super(mod, generator);
	}

	@Override
	protected void setup() {
		this.customLootTable(ModBlocks.gingerbreadHouse, LootTable.builder().addLootPool(
				LootPool.builder()
						.name("main")
						.rolls(ConstantRange.of(1))
						.acceptCondition(BlockStateProperty.builder(ModBlocks.gingerbreadHouse).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(BlockStateProperties.BITES_0_6, 0)))
						.acceptCondition(SurvivesExplosion.builder())
						.addEntry(ItemLootEntry.builder(ModBlocks.gingerbreadHouse))
		));
	}
}
