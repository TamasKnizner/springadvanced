package com.epam.tamasknizner.springadvancedtraining.domain;

import com.epam.tamasknizner.springadvancedtraining.domain.request.deserializer.EventRatingDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="event")
public class Event extends DomainObject {

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="basePrice")
    private double basePrice;

    @XmlElement(name="ticketPrice")
    private double ticketPrice;

    @JsonDeserialize(using = EventRatingDeserializer.class)
    @XmlElement(name="rating")
    private EventRating rating;

    @JsonIgnore
    @XmlTransient
    private Map<LocalDateTime, Auditorium> auditoriums = new HashMap<>();

    /**
     * Checks if event is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     *
     * @param dateTime   Date and time of aired event for which to assign
     * @param auditorium Auditorium that should be assigned
     * @return <code>true</code> if successful, <code>false</code> if event is
     * not aired on that date
     */
    public boolean assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
        return auditoriums.put(dateTime, auditorium) != null;
    }

    /**
     * Removes auditorium assignment from event
     *
     * @param dateTime Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     * removed
     */
    public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    /**
     * Add date and time of event air
     *
     * @param dateTime Date and time to add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime) {
        return auditoriums.put(dateTime, new Auditorium()) != null;
    }

    /**
     * Adding date and time of event air and assigning auditorium to that
     *
     * @param dateTime   Date and time to add
     * @param auditorium Auditorium to add if success in date time add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime, Auditorium auditorium) {
        return auditoriums.put(dateTime, auditorium) != null;
    }

    /**
     * Removes the date and time of event air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     *
     * @param dateTime Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    public boolean removeAirDateTime(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    /**
     * Checks if event airs on particular date and time
     *
     * @param dateTime Date and time to check
     * @return <code>true</code> event airs on that date and time
     */
    public boolean airsOnDateTime(LocalDateTime dateTime) {
        return getAirDates().stream().anyMatch(dt -> dt.equals(dateTime));
    }

    /**
     * Checks if event airs on particular date
     *
     * @param date Date to ckeck
     * @return <code>true</code> event airs on that date
     */
    public boolean airsOnDate(LocalDate date) {
        return getAirDates().stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    /**
     * Checking if event airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     *
     * @param from Start date to check
     * @param to   End date to check
     * @return <code>true</code> event airs on dates
     */
    public boolean airsOnDates(LocalDate from, LocalDate to) {
        return getAirDates().stream().anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<LocalDateTime> getAirDates() {
        return new TreeSet<>(auditoriums.keySet());
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public Map<LocalDateTime, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(Map<LocalDateTime, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(final double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return Double.compare(event.basePrice, basePrice) == 0 && Double.compare(event.ticketPrice, ticketPrice) == 0 &&
                (name != null ? name.equals(event.name) : event.name == null) && rating == event.rating;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ticketPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
