package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XmlHierarchyModel implements HierarchicalModel
{
    Element element;

    public XmlHierarchyModel( Element element )
    {
        this.element = element;
    }

    @Override
    public void addSimple( String name, Object result )
    {
        if ( name.equals( "href" ) )
        {
            element.addAttribute( name, result.toString() );
        }
        else
        {
            element.addElement( name ).setText( result.toString() );
        }
    }

    @Override
    public HierarchicalModel createChild( String name )
    {
        return new XmlHierarchyModel( element.addElement( name ) );
    }

    @Override
    public HierarchicalModel createChild()
    {
        return this;
    }


    @Override
    public HierarchicalModel createList( String name )
    {
        return this;
    }

    @Override
    public HierarchicalModel createSimple( String property )
    {
        return new XmlTextElementHierarchyModel( element.addElement( property ) );
    }

    @Override
    public void addSimple( Object instance )
    {
        throw new YogaRuntimeException( "You must call createSimple first" );
    }
}
