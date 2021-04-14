package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockTagProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagProvider extends BlockTagProviderBase {

	public BlockTagProvider(ModX mod, DataGenerator generatorIn, ExistingFileHelper fileHelper) {
		super(mod, generatorIn, fileHelper);
	}

	@Override
	protected void setup() {
		this.getOrCreateBuilder(ModTags.Blocks.sledgeSurface).add(
				Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.ICE, Blocks.PACKED_ICE,
				Blocks.BLUE_ICE, Blocks.FROSTED_ICE
		);
	}
}
