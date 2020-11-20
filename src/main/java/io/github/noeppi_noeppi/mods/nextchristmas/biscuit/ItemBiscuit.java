package io.github.noeppi_noeppi.mods.nextchristmas.biscuit;

import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.item.Item;

import java.util.Map;

public class ItemBiscuit extends ItemBase implements Registerable {

    public final Item dough;
    public final BlockBakingSheet dough_sheet;
    public final BlockBakingSheet baked_sheet;

    public ItemBiscuit(ModX mod, Properties properties) {
        super(mod, properties);

        this.dough = new ItemBase(mod, new Item.Properties());
        this.dough_sheet = new BlockBakingSheet(mod, this.dough, false, new Item.Properties());
        this.baked_sheet = new BlockBakingSheet(mod, this, false, new Item.Properties());
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters() {
        return ImmutableMap.of(
                "dough", this.dough,
                "dough_sheet", this.dough_sheet,
                "baked_sheet", this.baked_sheet
        );
    }
}
