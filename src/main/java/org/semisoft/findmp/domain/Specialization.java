package org.semisoft.findmp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Specialization {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Specialization(String name) {
        this.name = name;
    }

    public Specialization() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        Specialization other = (Specialization) o;
        return name.equalsIgnoreCase(other.getName());
    }
}
