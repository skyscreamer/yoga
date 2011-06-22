package org.skyscreamer.yoga.controller;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class ControllerResponse
{
    private Object _data;

    public ControllerResponse( Object data )
    {
        _data = data;
    }

    public Object getData()
    {
        return _data;
    }
}
