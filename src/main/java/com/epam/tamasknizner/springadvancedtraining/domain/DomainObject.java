package com.epam.tamasknizner.springadvancedtraining.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yuriy_Tkach
 */
public class DomainObject {

    @XmlElement(name="id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
