package org.skyscreamer.yoga.test.data;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

@URITemplate("/basic-leaf/{id}")
public class BasicTestDataLeaf
{

    private int id;
    private String name;
    private String other;

    @Core
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Core
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

}
