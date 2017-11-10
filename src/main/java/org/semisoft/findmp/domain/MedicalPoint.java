package org.semisoft.findmp.domain;

import javax.persistence.*;

@Entity
public class MedicalPoint
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;
    private String type;

    public MedicalPoint() {}

    public MedicalPoint(String name, Adress adress, String type)
    {
        this.name = name;
        this.adress = adress;
        this.type = type;
    }

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
    public Adress getAdress()
    {
        return adress;
    }
    public void setAdress(Adress adress)
    {
        this.adress = adress;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
}
