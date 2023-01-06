package com.ayou.mapping;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

/**
 * 更多使用方法可见文档: https://quarkus.io/guides/config-mappings
 */
@ConfigMapping(prefix = "lang")
public interface Lang{
    String test();
    @WithName("name")
    String name();
}
