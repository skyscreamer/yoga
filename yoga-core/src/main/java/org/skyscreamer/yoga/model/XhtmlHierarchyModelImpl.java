package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XhtmlHierarchyModelImpl implements MapHierarchicalModel<Element>, ListHierarchicalModel<Element>
{
    Element element;
    String childName = null;
    Element a = null;

    public XhtmlHierarchyModelImpl( Element element )
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
    public MapHierarchicalModel<Element> createChildMap( String property )
    {
        return new XhtmlHierarchyModelImpl( element.addElement( "div" )
                .addAttribute( "class", property ) );
    }
    
    @Override
    public MapHierarchicalModel<Element> createChildMap()
    {
        return new XhtmlHierarchyModelImpl( element.addElement( "div" ) );
    }

    @Override
    public ListHierarchicalModel<Element> createChildList( String property )
    {
        Element div = element.addElement( "div" ).addAttribute( "class", property );
        return new XhtmlHierarchyModelImpl( div );
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
    public Element getUnderlyingModel()
    {
        return this.element;
    }

    public void finished()
    {
    }

}
