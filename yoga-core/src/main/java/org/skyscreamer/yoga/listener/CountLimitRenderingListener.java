package org.skyscreamer.yoga.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.skyscreamer.yoga.exceptions.EntityCountExceededException;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.mapper.YogaRequestContext;

/**
 * This is a 
 *
 */
public class CountLimitRenderingListener implements RenderingListener
{

    int maxCount = 0;

    public CountLimitRenderingListener( int maxCount )
    {
        if (maxCount <= 0)
        {
            throw new YogaRuntimeException(
                    "CountLimitRenderingListener expects a positive maxCount" );
        }
        this.maxCount = maxCount;
    }

    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD)
        {
            return;
        }
        YogaRequestContext requestContext = event.getRequestContext();
        AtomicInteger counter = (AtomicInteger) requestContext.getProperty( "child_counter" );
        if (counter == null)
        {
            requestContext.setProperty( "child_counter", counter = new AtomicInteger() );
        }
        if (counter.incrementAndGet() > maxCount)
        {
            throw new EntityCountExceededException("The maximum count of children: " + maxCount + " has been exceeded.");
        }
    }

}
