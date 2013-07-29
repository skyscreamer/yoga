package org.skyscreamer.yoga.demo.test;

import org.springframework.context.ApplicationContext;

public class SpringBeanContext implements BeanContext
{

    ApplicationContext context;


    public SpringBeanContext( ApplicationContext context )
    {
        this.context = context;
    }


    @Override
    public <T> T getBean( Class<T> type )
    {
        return context.getBean( type );
    }

}
