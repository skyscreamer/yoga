package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XmlHierarchyModel implements HierarchicalModel<Element>
{
    protected Element element;
    protected String defaultName;

    public XmlHierarchyModel( Element element )
    {
        this.element = element;
    }

    public XmlHierarchyModel(Element element, String defaultName)
    {
        super();
        this.element = element;
        this.defaultName = defaultName;
    }

    @Override
    public void addProperty( String name, Object result )
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
    public HierarchicalModel<Element> createChildMap( String name )
    {
        return new XmlHierarchyModel( element.addElement( name ) );
    }

    @Override
    public HierarchicalModel<Element> createChildMap()
    {
        if (defaultName != null)
        {
            return new XmlHierarchyModel( element.addElement( defaultName ) );
        }
        else
        {
            return this;
        }
    }

    @Override
    public HierarchicalModel<Element> createChildList( String name )
    {
        return new XmlHierarchyModel( element, name );
    }

    @Override
    public HierarchicalModel<?> createSimple( String property )
    {
        return new XmlTextElementHierarchyModel( element.addElement( property ) );
    }

    @Override
    public void addValue( Object instance )
    {
        if (defaultName != null)
        {
            addProperty( defaultName, instance );
        }
        else
        {
            throw new YogaRuntimeException( "You must call createSimple first" );
        }
    }

    @Override
    public Element getUnderlyingModel()
    {
        return element;
    }
}
