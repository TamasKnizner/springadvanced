package com.epam.tamasknizner.springadvancedtraining.repository.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public abstract class JsonLoader<T> implements ObjectLoader<T> {
    private File file;
    private String json;

    public JsonLoader(File file) {
        this.file = file;
    }

    public JsonLoader(String json) {
        this.json = json;
    }

    @Override
    public T load() {
        Type type = getType();
        Gson gson = createGson();
        if (!StringUtils.isEmpty(json)) {
            return gson.fromJson(json, type);
        }
        try (InputStream stream = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)
        ) {
            return gson.fromJson(reader, type);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Type getType();

    protected Gson createGson() {
        return new GsonBuilder().create();
    }

    public static File classpathToFile(String filePath) {
        try {
            return new File(JsonLoader.class.getResource(filePath).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
