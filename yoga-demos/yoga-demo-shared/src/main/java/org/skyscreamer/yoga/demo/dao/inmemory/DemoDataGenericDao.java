package org.skyscreamer.yoga.demo.dao.inmemory;

import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.yoga.demo.dao.GenericDao;

public class DemoDataGenericDao implements GenericDao
{
    
    private DemoData data;

    public DemoDataGenericDao( DemoData data )
    {
        this.data = data;
    }

    @Override
    public <T> T find( Class<T> type, long id )
    {
        return data.get( type, id );
    }

    @Override
    public <T> List<T> findAll( Class<T> type )
    {
        return new ArrayList<T>( data.getAll( type ) );
    }

    @Override
    public Number getCount( Class<?> type )
    {
        return data.getAll( type ).size();
    }

}
