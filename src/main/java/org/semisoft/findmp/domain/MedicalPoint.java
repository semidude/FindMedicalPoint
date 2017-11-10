package org.semisoft.findmp.domain;

public class MedicalPoint
{
    private String name;
    private Adress adress;
    private String type;

    public MedicalPoint(String name, Adress adress, String type)
    {
        this.name = name;
        this.adress = adress;
        this.type = type;
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
