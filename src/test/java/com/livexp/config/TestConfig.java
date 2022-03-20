package com.livexp.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"
})
public interface TestConfig extends Config {
    @Key("url")
    String url();

    @Key("browser")
    String browser();
}