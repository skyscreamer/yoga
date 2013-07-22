package org.skyscreamer.yoga.demo.dao;

import java.util.List;

public interface GenericDao
{
    public <T> T find( Class<T> type, long id );

    public <T> List<T> findAll( Class<T> type );

    public Number getCount( Class<?> type );
}
