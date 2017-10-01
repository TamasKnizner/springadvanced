package com.epam.tamasknizner.springadvancedtraining.service;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    @Nonnull Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    @Nullable Auditorium getByName(@Nonnull String name);

}
