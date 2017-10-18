package com.epam.tamasknizner.springadvancedtraining.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Yuriy_Tkach
 */
@XmlType(name = "EventRating")
@XmlEnum
public enum EventRating {

    LOW,
    MID,
    HIGH

}
