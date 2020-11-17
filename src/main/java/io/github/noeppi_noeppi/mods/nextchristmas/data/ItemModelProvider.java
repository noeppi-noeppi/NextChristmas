package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends ItemModelProviderBase {

	public static final ResourceLocation SPAWN_EGG_TEMPLATE = new ResourceLocation("minecraft", "item/template_spawn_egg");

	public ItemModelProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
		super(mod, generator, fileHelper);
	}

	@Override
	protected void setup() {

	}

	@Override
	protected void defaultItem(ResourceLocation id, Item item) {
		if (item instanceof SpawnEggItem) {
			this.withExistingParent(id.getPath(), SPAWN_EGG_TEMPLATE);
		} else {
			super.defaultItem(id, item);
		}
	}
}
