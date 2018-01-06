package org.semisoft.findmp.domain;

import javax.persistence.*;

@Entity
public class MedicalPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    private Specialization specialization;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @OneToOne(cascade = CascadeType.MERGE)
    private Sector sector;

    public MedicalPoint(String name, Specialization specialization, Address address) {
        this.name = name;
        this.specialization = specialization;
        this.address = address;
    }

    public MedicalPoint(String name) {
        this.name = name;
    }

    public MedicalPoint(MedicalPoint other) {
        this.name = other.getName();
        this.specialization = other.getSpecialization();
        this.address = other.getAddress();
        this.location = other.getLocation();
        this.sector = other.getSector();
    }

    public MedicalPoint() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
        //TODO do something with that...
//        location = Location.fromAddress(address);
//        sector = Sector.fromLocation(location);
    }
    public Specialization getSpecialization() {
        return specialization;
    }
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Sector getSector() {
        return sector;
    }
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return name + ", " + address + " (" + specialization + ")";
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
