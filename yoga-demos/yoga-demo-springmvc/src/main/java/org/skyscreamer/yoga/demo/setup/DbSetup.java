package org.skyscreamer.yoga.demo.setup;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/13/11
 * Time: 6:25 PM
 */
@Component
public class DbSetup
{
    @Autowired private DataSource _datasource;

    @PostConstruct
    public void afterPropertiesSet() throws Exception
    {
        IDatabaseConnection connection = new DatabaseDataSourceConnection( _datasource );
        IDataSet dataSet = new XmlDataSet( getClass().getResourceAsStream( "/sampledb.xml" ) );
        DatabaseOperation.CLEAN_INSERT.execute( connection, dataSet );
    }
}
