package org.semisoft.findmp.domain;

import javax.persistence.*;

@Entity
public class MedicalPoint
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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
    public MedicalPoint(String name, Specialization specialization, Address address, boolean sector)
    {
        this.name = name;
        this.specialization = specialization;
        this.address = address;
    }
    public MedicalPoint(String name)
    {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        MedicalPoint other = (MedicalPoint) o;
        return name.equals(other.getName()) &&
                specialization.equals(other.getSpecialization()) &&
                address.equals(other.getAddress()) &&
                sector.equals(other.getSector());
    }
}
