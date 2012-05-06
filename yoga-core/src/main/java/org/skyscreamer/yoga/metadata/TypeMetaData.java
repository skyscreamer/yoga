package org.skyscreamer.yoga.metadata;

import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.yoga.annotations.Core;

public class TypeMetaData
{
    String name;
    List<PropertyMetaData> propertyMetaData = new ArrayList<PropertyMetaData>();

    @Core
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Core
    public List<PropertyMetaData> getPropertyMetaData()
    {
        return propertyMetaData;
    }

    public void setPropertyMetaData(List<PropertyMetaData> propertyMetaData)
    {
        this.propertyMetaData = propertyMetaData;
    }

}
