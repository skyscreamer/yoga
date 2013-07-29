package org.skyscreamer.yoga.demo.test.jersey.standalone;

import org.skyscreamer.yoga.jersey.config.URIExtensionsConfig;

import com.sun.jersey.core.spi.scanning.Scanner;

public class DemoApplication extends URIExtensionsConfig 
{

    @Override
    public void init( Scanner scanner )
    {
        super.init(scanner);

        DemoData demoData = new DemoData();
    }

}
