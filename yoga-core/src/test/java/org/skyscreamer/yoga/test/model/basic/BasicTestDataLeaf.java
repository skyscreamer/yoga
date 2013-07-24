package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import java.util.List;

@URITemplate("/basic-leaf/{id}")
public class BasicTestDataLeaf
{

    private int id;
    private String name;
    private String other;
    private List<String> randomStrings;

    public List<String> getRandomStrings()
    {
        return randomStrings;
    }

    public void setRandomStrings( List<String> randomStrings )
    {
        this.randomStrings = randomStrings;
    }

    @Core
    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    @Core
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther( String other )
    {
        this.other = other;
    }

}
