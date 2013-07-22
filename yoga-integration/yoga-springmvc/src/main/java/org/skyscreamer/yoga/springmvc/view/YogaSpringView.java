package org.skyscreamer.yoga.springmvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.view.AbstractYogaView;
import org.springframework.web.servlet.View;

/**
 * This MessageConvert gets the selector from the request. Children do the
 * interesting output. NOTE: you have to put in a
 * org.skyscreamer.yoga.springmvc.view.RequestHolder in your web.xml file
 * 
 * @author Solomon Duskis
 */
public class YogaSpringView implements View
{
    private AbstractYogaView yogaView;
    
    public void setYogaView( AbstractYogaView yogaView )
    {
        this.yogaView = yogaView;
    }

    @Override
    public void render( Map<String, ?> model, HttpServletRequest request, HttpServletResponse response )
            throws Exception
    {
        response.setContentType( getContentType() );
        yogaView.render( request, response, model.isEmpty() ? null : model.values().iterator().next(), response.getOutputStream() );
    }

    @Override
    public String getContentType()
    {
        return yogaView.getContentType();
    }
}
