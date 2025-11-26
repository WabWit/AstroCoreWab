package com.astro.core.common.data.configs;

import com.astro.core.AstroCore;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

    @Config(id = AstroCore.MOD_ID)
    public class AstroConfigs {

        public static AstroConfigs INSTANCE;

        public static ConfigHolder<AstroConfigs> CONFIG_HOLDER;

        public static void init() {
            CONFIG_HOLDER = Configuration.registerConfig(AstroConfigs.class, ConfigFormats.yaml());
            INSTANCE = CONFIG_HOLDER.getConfigInstance();
        }

        @Configurable
        public FeatureConfigs features = new FeatureConfigs();

        public static class FeatureConfigs {
        }
    }
