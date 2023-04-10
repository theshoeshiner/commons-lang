package org.thshsh.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.event.EventListenerSupport;

/**
 * Invocation handler that, on failure, throws the underlying exception instead of the InvocationException
 *
 * @param <L>
 */
public class SmartInvocationHandler<L> implements InvocationHandler {

	protected Boolean quiet;
	protected EventListenerSupport<L> eventListenerSupport;
	

	public SmartInvocationHandler(EventListenerSupport<L> eventListenerSupport) {
		this(eventListenerSupport,false);
	}
	
    public SmartInvocationHandler(EventListenerSupport<L> eventListenerSupport,Boolean quiet) {
		super();
		this.quiet = quiet;
		this.eventListenerSupport = eventListenerSupport;
	}


	@Override
    public Object invoke(final Object unusedProxy, final Method method, final Object[] args) throws Throwable {
        for (final L listener : eventListenerSupport.getListeners()) {
        	try {
        		method.invoke(listener, args);
        	}
        	catch(InvocationTargetException t) {
        		if(!quiet && t.getCause()!=null) throw t.getCause();
        	}
        }
        return null;
    }
	
}
