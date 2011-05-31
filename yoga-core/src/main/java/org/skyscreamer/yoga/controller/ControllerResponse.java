package org.skyscreamer.yoga.controller;

import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class ControllerResponse
{
    private Selector _selector;
    private Object _data;

    public ControllerResponse( Selector selector, Object data )
    {
        _selector = selector;
        _data = data;
    }

    public Selector getSelector()
    {
        return _selector;
    }

    public Object getData()
    {
        return _data;
    }
}
