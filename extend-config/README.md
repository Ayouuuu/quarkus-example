# 说明
本篇 `Demo` 将会告诉你如何自定义一个 `配置文件` 数据源，你可以查看以下代码获得灵感!  

## 如何使用

- [Properties 源码](src/main/java/com/ayou/source/LangPropertiesConfigSource.java)
- [Yaml 源码](src/main/java/com/ayou/source/LangYamlConfigSource.java)

单元测试 [CustomSourceTest](src/test/java/com/ayou/CustomSourceTest.java)
## 注册数据源
当你添加了数据源的代码后，必须要注册数据源!  
你需要在 `resources` 中创建文件 `META-INF/service/org.eclipse.microprofile.config.spi.ConfigSourceProvider`  
参考 [ConfigSourceProvider](src/main/resources/META-INF/services/org.eclipse.microprofile.config.spi.ConfigSourceProvider)

## 配置文件

- [application.properties](src/main/resources/application.properties)
- [lang.properties](src/main/resources/lang.properties)
- [lang.yml](src/main/resources/lang.yml)

Maven 引入 [quarkus-config-yaml](https://quarkus.io/guides/config-yaml) 用来解析 `yml`

```xml

<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-config-yaml</artifactId>
</dependency>
```

## Map 映射

```java
package com.ayou.mapping;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

/**
 * 更多使用方法可见文档: https://quarkus.io/guides/config-mappings
 */
@ConfigMapping(prefix = "lang")
public interface Lang {
    String test();

    @WithName("name")
    String name();
}
```

### 参考资料

- https://quarkus.io/guides/config-reference
- https://quarkus.io/guides/config-yaml
- https://quarkus.io/guides/config-extending-support
- https://quarkus.io/guides/config-mappings