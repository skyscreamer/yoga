package org.skyscreamer.yoga.model;

import org.dom4j.Element;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class XmlTextElementHierarchyModel extends AbstractSimplePropertyHierarchyModel
{
    Element element;

    public XmlTextElementHierarchyModel( Element element )
    {
        if ( element == null )
        {
            throw new YogaRuntimeException( new IllegalAccessException( "element was not set" ) );
        }
        this.element = element;
    }

    @Override
    public void addSimple( Object instance )
    {
        element.addText( instance.toString() );
    }

}
