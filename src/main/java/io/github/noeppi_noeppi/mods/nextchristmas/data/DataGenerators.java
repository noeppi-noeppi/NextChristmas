package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.data.recipes.RecipeProvider;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent evt) {
		if (evt.includeServer()) {
			evt.getGenerator().addProvider(new BlockLootProvider(NextChristmas.getInstance(), evt.getGenerator()));
			BlockTagProvider blockTagProvider = new BlockTagProvider(NextChristmas.getInstance(), evt.getGenerator(), evt.getExistingFileHelper());
			evt.getGenerator().addProvider(blockTagProvider);
			evt.getGenerator().addProvider(new ItemTagProvider(NextChristmas.getInstance(), evt.getGenerator(), evt.getExistingFileHelper(), blockTagProvider));
			evt.getGenerator().addProvider(new RecipeProvider(NextChristmas.getInstance(), evt.getGenerator()));
			evt.getGenerator().addProvider(new BlockStateProvider(NextChristmas.getInstance(), evt.getGenerator(), evt.getExistingFileHelper()));
			evt.getGenerator().addProvider(new ItemModelProvider(NextChristmas.getInstance(), evt.getGenerator(), evt.getExistingFileHelper()));
		}
	}
}
