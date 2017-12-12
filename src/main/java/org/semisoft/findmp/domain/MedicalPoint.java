package org.semisoft.findmp.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class MedicalPoint
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private ArrayList<MedicalPointUnit> units;
    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;

    public MedicalPoint(String name, Adress adress, String type)
    {
        this.name = name;
        this.adress = adress;
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
    public Adress getAdress()
    {
        return adress;
    }
    public void setAdress(Adress adress)
    {
        this.adress = adress;
    }
}
