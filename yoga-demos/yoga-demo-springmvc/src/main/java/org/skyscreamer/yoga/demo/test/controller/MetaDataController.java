package org.skyscreamer.yoga.demo.test.controller;

import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.metadata.TypeMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/metadata")
public class MetaDataController
{
    @Autowired
    MetaDataService metaDataService;

    @RequestMapping("/{type}")
    public TypeMetaData getTypeMetaData( @PathVariable("type") String type, HttpServletRequest request )
    {
        String uri = request.getRequestURI();
        String parts[] = uri.split( "\\." );
        return metaDataService.getMetaData( type, parts[parts.length - 1] );
    }

}
