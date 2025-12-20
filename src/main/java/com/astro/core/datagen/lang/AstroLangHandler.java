package com.astro.core.datagen.lang;

import com.astro.core.AstroCore;
import com.tterrag.registrate.providers.RegistrateLangProvider;

public class AstroLangHandler {

    public static void init(RegistrateLangProvider provider) {
        provider.add("astrogreg.machine.parallel_hatch_mk9.tooltip", "Allows to run up to 1024 recipes in parallel.");

    }

    protected static void multiLang(RegistrateLangProvider provider, String key, String... values) {
        for (var i = 0; i < values.length; i++) {
            provider.add(getSubKey(key, i), values[i]);
        }
    }

    protected static String getSubKey(String key, int index) {
        return key + "." + index;
    }
}
