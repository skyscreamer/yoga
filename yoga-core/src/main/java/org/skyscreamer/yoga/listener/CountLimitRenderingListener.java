package org.skyscreamer.yoga.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.skyscreamer.yoga.exceptions.EntityCountExceededException;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.mapper.YogaRequestContext;

/**
 * This is a mechanism that will limit the number of children that are added to
 * a response. This is a security mechanism that will prevent potential yoga
 * induced OutOfMemoryError.
 * 
 * @see RenderingListener
 * @see RenderingListenerRegistry
 * @see YogaRequestContext
 */
public class CountLimitRenderingListener implements RenderingListener
{

    int maxCount = 0;

    public CountLimitRenderingListener( int maxCount )
    {
        setMaxCount(maxCount);
    }

    public void setMaxCount(int maxCount)
    {
        if (maxCount <= 0)
        {
            throw new YogaRuntimeException(
                    "CountLimitRenderingListener expects a positive maxCount" );
        }
        this.maxCount = maxCount;
    }
    
    @Override
    public <T> void eventOccurred( RenderingEvent<T> event )
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
