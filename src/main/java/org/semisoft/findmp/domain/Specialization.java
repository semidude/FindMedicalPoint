package org.semisoft.findmp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Specialization
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany
    private Set<Keyword> keywords;

    public Specialization(String name)
    {
        this.name = name;
    }

    public Specialization()
    {
        this.name = "";
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Set<Keyword> getKeywords()
    {
        return keywords;
    }
    public void addKeyword(Keyword keyword)
    {
        keywords.add(keyword);
    }
    public void addAllKeywords(Set<Keyword> keywords)
    {
        this.keywords.addAll(keywords);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialization)) return false;

        Specialization that = (Specialization) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + keywords.hashCode();
        return result;
    }
}
