package io.github.noeppi_noeppi.mods.nextchristmas.util;

import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Function;

public class EnumValues<E extends Enum<E>, T> implements Registerable {

    public final Map<E, T> map;

    public EnumValues(E[] values, Function<E, T> factory) {
        ImmutableMap.Builder<E, T> builder = ImmutableMap.builder();
        for (E enumValue : values) {
            builder.put(enumValue, factory.apply(enumValue));
        }
        this.map = builder.build();
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters() {
        //noinspection UnstableApiUsage
        return this.map.entrySet().stream().map(entry -> Pair.of(entry.getKey().name().toLowerCase(), entry.getValue())).collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue));
    }
}
