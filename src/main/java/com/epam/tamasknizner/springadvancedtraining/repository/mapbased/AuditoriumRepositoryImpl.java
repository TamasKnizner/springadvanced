package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;
import com.epam.tamasknizner.springadvancedtraining.repository.AuditoriumRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collection;

public class AuditoriumRepositoryImpl extends MapBasedRepository<Auditorium, String> implements AuditoriumRepository {

    public AuditoriumRepositoryImpl() {
        super(Auditorium::getName, Auditorium::setName);
    }

    public AuditoriumRepositoryImpl(@Nonnull Collection<Auditorium> auditoriums) {
        super(Auditorium::getName, Auditorium::setName, auditoriums);
    }
}
