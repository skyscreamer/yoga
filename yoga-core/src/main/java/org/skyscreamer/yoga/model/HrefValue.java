package org.skyscreamer.yoga.model;

public class HrefValue
{
    String link;
    String type;
    Object underlying;

    public HrefValue( String link, String type, Object underlying )
    {
        super();
        this.link = link;
        this.type = type;
        this.underlying = underlying;
    }

    @Override
    public String toString()
    {
        return link;
    }
}
