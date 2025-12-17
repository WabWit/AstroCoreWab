package com.astro.core.datagen.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class AstroLangHandler {

    public static void init(RegistrateLangProvider provider) {
        provider.add("testing.duck", "This is a duck");
        provider.add("item.astrogreg.tiny_acorn_dust", "Tiny Acorn Flour");
        provider.add("item.astrogreg.small_acorn_dust", "Small Acorn Flour");
        provider.add("item.astrogreg.acorn_dust", "Acorn Flour");
        provider.add("item.astrogreg.tiny_mana_dust", "Tiny Mana Powder");
        provider.add("item.astrogreg.small_mana_dust", "Small Mana Powder");
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
