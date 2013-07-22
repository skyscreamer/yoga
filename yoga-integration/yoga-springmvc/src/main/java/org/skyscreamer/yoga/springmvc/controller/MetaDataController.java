package org.skyscreamer.yoga.springmvc.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.metadata.TypeMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/metadata")
public class MetaDataController
{
    @Autowired
    MetaDataRegistry _metaDataRegistry;

    @RequestMapping()
    public Map<String, String> getTypes()
    {
        Map<String, String> response = new TreeMap<String, String>();
        for (String type : _metaDataRegistry.getTypes())
        {
            response.put( type, "/metadata/" + type );
        }
        return response;
    }

    @RequestMapping("/{type}")
    public TypeMetaData getTypeMetaData( @PathVariable("type") String type, HttpServletRequest request )
    {
        String uri = request.getRequestURI();
        String parts[] = uri.split( "\\." );
        return _metaDataRegistry.getMetaData( type, parts[parts.length - 1] );
    }

}
