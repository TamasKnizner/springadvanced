package com.epam.tamasknizner.springadvancedtraining.domain;

import com.epam.tamasknizner.springadvancedtraining.domain.request.deserializer.LocalDateDeserializer;
import com.epam.tamasknizner.springadvancedtraining.domain.request.serializer.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public class User extends DomainObject {

    @JsonIgnore
    private final Set<UserLuckyEventInfo> luckyEvents = new HashSet<>();
    @JsonIgnore
    private final Object luckyEventsLocker = new Object();

    private String firstName;

    private String lastName;

    private String email;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    @JsonIgnore
    private Set<Ticket> tickets = new HashSet<>();

    @JsonIgnore
    private String password;

    @JsonIgnore
    private UserAccount userAccount;

    private String roles;

    public final Set<UserLuckyEventInfo> getLuckyEvents() {
        synchronized (luckyEventsLocker) {
            return new HashSet<>(luckyEvents);
        }
    }

    public void addLuckyEvent(UserLuckyEventInfo luckyEvent) {
        synchronized (luckyEventsLocker) {
            luckyEvents.add(luckyEvent);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(final String roles) {
        this.roles = roles;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!luckyEvents.equals(user.luckyEvents)) return false;
        if (!luckyEventsLocker.equals(user.luckyEventsLocker))
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (tickets != null ? !tickets.equals(user.tickets) : user.tickets != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userAccount != null ? !userAccount.equals(user.userAccount) : user.userAccount != null) return false;
        return roles != null ? roles.equals(user.roles) : user.roles == null;
    }

    @Override
    public int hashCode() {
        int result = luckyEvents.hashCode();
        result = 31 * result + luckyEventsLocker.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", roles='" + roles + '\'' +
                '}';
    }
}
