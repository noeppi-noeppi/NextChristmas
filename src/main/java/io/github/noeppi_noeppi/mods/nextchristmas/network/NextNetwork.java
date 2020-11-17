package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;

public class NextNetwork extends NetworkX {

    public NextNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected void registerPackets() {

    }

    @Override
    protected String getProtocolVersion() {
        return "1";
    }
}
