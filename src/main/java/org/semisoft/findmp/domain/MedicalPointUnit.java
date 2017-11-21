package org.semisoft.findmp.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class MedicalPointUnit
{
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private Specialization specialization;
    @OneToOne
    private MedicalPoint medicalPoint;

    public Long getId()
    {
        return Id;
    }
    public void setId(Long id)
    {
        Id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Specialization getSpecialization()
    {
        return specialization;
    }
    public void setSpecialization(Specialization specialization)
    {
        this.specialization = specialization;
    }
    public MedicalPoint getMedicalPoint()
    {
        return medicalPoint;
    }
    public void setMedicalPoint(MedicalPoint medicalPoint)
    {
        this.medicalPoint = medicalPoint;
    }
}
