package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;
import com.epam.tamasknizner.springadvancedtraining.repository.AuditoriumRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collection;

@Component
public class AuditoriumRepositoryImpl
        extends MapBasedRepository<Auditorium, String>
        implements AuditoriumRepository {
    public AuditoriumRepositoryImpl() {
        super(Auditorium::getName, Auditorium::setName);
    }

    public AuditoriumRepositoryImpl(@Nonnull Collection<Auditorium> auditoriums) {
        super(Auditorium::getName, Auditorium::setName, auditoriums);
    }

//    public AuditoriumRepositoryImpl(@Nonnull ObjectLoader<Collection<Auditorium>> loader) {
//        super(Auditorium::getName, Auditorium::setName, loader);
//    }
}
