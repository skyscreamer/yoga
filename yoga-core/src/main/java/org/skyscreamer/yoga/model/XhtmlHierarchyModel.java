package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XhtmlHierarchyModel implements HierarchicalModel<Element>
{
    Element element;
    String childName = null;
    Element a = null;

    public XhtmlHierarchyModel( Element element )
    {
        this.element = element;
    }

    @Override
    public void addProperty( String name, Object result )
    {
        String elementName = childName == null ? name : childName;
        if ( elementName.equals( "href" ) )
        {
            a = element.addElement( "a" );
            a.addAttribute( "href", result.toString() );
        }
        else if ( a != null )
        {
            a.setText( result.toString() );
        }
        else
        {
            element.addElement( "span" ).addAttribute( "class", elementName )
                    .setText( result.toString() );
        }
    }

    @Override
    public HierarchicalModel<Element> createChildMap( String property )
    {
        return new XhtmlHierarchyModel( element.addElement( "div" )
                .addAttribute( "class", property ) );
    }

    @Override
    public HierarchicalModel<Element> createChildMap()
    {
        return new XhtmlHierarchyModel( element.addElement( "div" ) );
    }

    @Override
    public HierarchicalModel<Element> createChildList( String property )
    {
        Element div = element.addElement( "div" ).addAttribute( "class", property );
        return new XhtmlHierarchyModel( div );
    }

    @Override
    public void addValue( Object instance )
    {
        if ( childName != null )
            element.addElement( childName ).setText( instance.toString() );
        else
            throw new YogaRuntimeException( "childName was never set" );
    }

    @Override
    public HierarchicalModel<?> createSimple( String property )
    {
        return new XmlTextElementHierarchyModel( element.addElement( property ) );
    }

    @Override
    public Element getUnderlyingModel()
    {
        return this.element;
    }
}
