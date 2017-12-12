package org.semisoft.findmp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class MedicalPoint
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<MedicalPointUnit> units;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    private Specialization specialization;
    @OneToOne(cascade = CascadeType.ALL)
    private Sector sector;

    public MedicalPoint(String name, Specialization specialization, Address address)
    {
        this.name = name;
        this.specialization = specialization;
        this.address = address;
        sector = Sector.fromLocation(Location.fromAddress(address));
    }

    public MedicalPoint() {}

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Address getAddress()
    {
        return address;
    }
    public void setAddress(Address address)
    {
        this.address = address;
    }
    public List<MedicalPointUnit> getUnits() {
        return units;
    }
    public void addUnit(MedicalPointUnit unit) {
        units.add(unit);
    }
    public Specialization getSpecialization()
    {
        return specialization;
    }
    public void setSpecialization(Specialization specialization)
    {
        this.specialization = specialization;
    }
    public Sector getSector()
    {
        return sector;
    }
    public void setSector(Sector sector)
    {
        this.sector = sector;
    }
}
