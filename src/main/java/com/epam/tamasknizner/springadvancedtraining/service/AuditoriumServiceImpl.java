package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;
import com.epam.tamasknizner.springadvancedtraining.repository.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private final Repository<Auditorium, String> repository;

    public AuditoriumServiceImpl(Repository<Auditorium, String> repository) {
        this.repository = repository;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(repository.getAll());
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return repository.findById(name);
    }
}
