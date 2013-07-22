package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XmlHierarchyModelImpl implements MapHierarchicalModel<Element>, ListHierarchicalModel<Element>
{
    protected Element element;
    protected String defaultName;

    public XmlHierarchyModelImpl( Element element )
    {
        this.element = element;
    }

    public XmlHierarchyModelImpl(Element element, String defaultName)
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
    public MapHierarchicalModel<Element> createChildMap( String name )
    {
        return new XmlHierarchyModelImpl( element.addElement( name ) );
    }

    @Override
    public MapHierarchicalModel<Element> createChildMap()
    {
        if (defaultName != null)
        {
            return new XmlHierarchyModelImpl( element.addElement( defaultName ) );
        }
        else
        {
            return this;
        }
    }

    @Override
    public ListHierarchicalModel<Element> createChildList( String name )
    {
        return new XmlHierarchyModelImpl( element, name );
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

    public void finished()
    {
    }

}
