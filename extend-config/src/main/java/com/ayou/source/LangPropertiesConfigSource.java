package com.ayou.source;

import io.quarkus.runtime.configuration.ApplicationPropertiesConfigSourceLoader;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSourceProvider;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LangPropertiesConfigSource extends ApplicationPropertiesConfigSourceLoader implements ConfigSourceProvider {
    @Override
    public Iterable<ConfigSource> getConfigSources(ClassLoader classLoader) {
        List<ConfigSource> sourceList = new ArrayList<>();
        // ordinal 为优先级 越高越优先加载
        sourceList.addAll(this.loadConfigSources("lang.properties",110,classLoader));
        sourceList.addAll(this.loadConfigSources(Paths.get(System.getProperty("user.dir"),"config","lang.properties").toUri().toString(),120,classLoader));
        return sourceList;
    }
}
