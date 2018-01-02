package org.semisoft.findmp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address
{
    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String street;
    private String number;

    public Address() {}

    public Address(String city, String street, String number)
    {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Override
    public String toString()
    {
        return city + " " + street + " " + number;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getStreet()
    {
        return street;
    }
    public void setStreet(String street)
    {
        this.street = street;
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        return number.equals(address.number);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }
}
