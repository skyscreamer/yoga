package org.skyscreamer.yoga.model;

import org.dom4j.Element;

public class XhtmlHierarchyModel implements HierarchicalModel
{
    Element element;
    String childName = null;
    Element a = null;

    public XhtmlHierarchyModel(Element element)
    {
        this.element = element;
    }

    public XhtmlHierarchyModel(Element element, String childName)
    {
        super();
        this.element = element;
        this.childName = childName;
    }

    @Override
    public void addSimple(String name, Object result)
    {
        String elementName = childName == null ? name : childName;
        if (elementName.equals( "href" ))
        {
            a = element.addElement( "a" );
            a.addAttribute( "href", result.toString() );
        }
        else if (a != null)
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
    public HierarchicalModel createChild(String property)
    {
        return new XhtmlHierarchyModel( element.addElement( "div" )
                .addAttribute( "class", property ) );
    }

    @Override
    public HierarchicalModel createList(String property)
    {
        Element div = element.addElement( "div" ).addAttribute( "class", property );
        return new XhtmlHierarchyModel( div, property );
    }
}
