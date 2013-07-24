package org.skyscreamer.yoga.metadata;

import org.skyscreamer.yoga.annotations.Core;

public class PropertyMetaData
{
    String name, type, href;
    boolean isCore;

    @Core
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Core
    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    @Core
    public boolean getIsCore()
    {
        return isCore;
    }

    public void setIsCore( boolean isCore )
    {
        this.isCore = isCore;
    }

    @Core
    public String getHref()
    {
        return href;
    }

    public void setHref( String href )
    {
        this.href = href;
    }

}
