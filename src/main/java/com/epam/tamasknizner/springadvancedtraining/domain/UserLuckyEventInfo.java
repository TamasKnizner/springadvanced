package com.epam.tamasknizner.springadvancedtraining.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserLuckyEventInfo {
    private final long userId;
    private final long eventId;
    private final LocalDateTime airDate;

    public UserLuckyEventInfo(long userId, long eventId, LocalDateTime airDate) {
        this.userId = userId;
        this.eventId = eventId;
        this.airDate = airDate;
    }

    public long getUserId() {
        return userId;
    }

    public long getEventId() {
        return eventId;
    }

    public LocalDateTime getAirDate() {
        return airDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLuckyEventInfo that = (UserLuckyEventInfo) o;
        return userId == that.userId &&
                eventId == that.eventId &&
                Objects.equals(airDate, that.airDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId, airDate);
    }
}
