package com.ayou;

import com.ayou.mapping.Lang;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CustomSourceTest {
    @ConfigProperty(name = "application.name")
    String application;

    @ConfigProperty(name = "lang.name")
    String lang;
    @ConfigProperty(name = "yaml.name")
    String yaml;

    @Inject
    Lang language;

    @Test
    public void configTest(){
        assertEquals("application",application);
        assertEquals("custom",lang);
        assertEquals("yaml",yaml);
    }

    @Test
    public void mappingTest(){
        assertEquals("custom",language.name());
        assertEquals("test",language.test());
    }
}
