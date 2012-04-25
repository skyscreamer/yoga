package org.skyscreamer.yoga.mapper;

import org.dom4j.Element;

import java.beans.PropertyDescriptor;

public class XmlHierarchyModel implements HierarchicalModel
{
    Element element;
    String childName = null;

    public XmlHierarchyModel( Element element )
    {
        this.element = element;
    }

    public XmlHierarchyModel( Element element, String childName )
    {
        super();
        this.element = element;
        this.childName = childName;
    }

    @Override
    public void addSimple( PropertyDescriptor property, Object result )
    {
        addSimple( property.getName(), result );
    }

    @Override
    public void addSimple( String name, Object result )
    {
        String elementName = childName == null ? name : childName;
        if ( name.equals( "href" ) )
        {
            element.addAttribute( elementName, result.toString() );
        }
        else
        {
            element.addElement( elementName ).setText( result.toString() );
        }
    }

    @Override
    public HierarchicalModel createChild( PropertyDescriptor property )
    {
        return createChild( property.getName() );
    }

    public HierarchicalModel createChild( String name )
    {
        return new XmlHierarchyModel( element.addElement( name ) );
    }

    @Override
    public HierarchicalModel createList( PropertyDescriptor property )
    {
        return createList( property.getName() );
    }

    @Override
    public HierarchicalModel createList( String property )
    {
        return this;
    }

}
