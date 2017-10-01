package com.epam.tamasknizner.springadvancedtraining.repository.loaders;

import com.google.gson.reflect.TypeToken;
import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class AuditoriumJsonLoader extends JsonLoader<Collection<Auditorium>> {
    public AuditoriumJsonLoader(File file) {
        super(file);
    }

    public AuditoriumJsonLoader(String json) {
        super(json);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Auditorium>>() {
        }.getType();
    }
}
