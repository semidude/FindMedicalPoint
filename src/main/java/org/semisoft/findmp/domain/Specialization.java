package org.semisoft.findmp.domain;

import java.util.Set;

public class Specialization
{
    private String name;
    private Set<String> keywords;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Set<String> getKeywords()
    {
        return keywords;
    }
    public void setKeywords(Set<String> keywords)
    {
        this.keywords = keywords;
    }
}
