package com.epam.tamasknizner.springadvancedtraining.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EventRating")
@XmlEnum
public enum EventRating {
    LOW,
    MID,
    HIGH
}
