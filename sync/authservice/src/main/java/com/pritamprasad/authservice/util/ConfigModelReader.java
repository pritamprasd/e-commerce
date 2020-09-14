package com.pritamprasad.authservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.pritamprasad.authservice.models.ConfigModel;
import com.pritamprasad.authservice.models.ExceptionConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConfigModelReader {

    private static Map<String, ExceptionConfig> configMap = new HashMap<>();

    static {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource("exceptions.yaml");
        if(resource == null) {
            throw new Error("Loading of exceptions.yaml failed");
        };
        File file = new File(resource.getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            ConfigModel configs = om.readValue(file, ConfigModel.class);
            configs.getExceptions().forEach(c -> configMap.put(c.getId(),c));
        } catch (IOException e) {
            throw new Error("Loading of exceptions.yaml failed",e);
        }
    }

    public static synchronized Optional<ExceptionConfig> getExceptionForId(String id){
        return Optional.ofNullable(configMap.get(id));
    }

}
