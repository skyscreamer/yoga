package org.skyscreamer.yoga.demo.test;

public interface BeanContext
{

    <T> T getBean( Class<T> type );

}
