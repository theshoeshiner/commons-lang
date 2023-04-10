package org.thshsh.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses smart invocation handler and allows quiet events.
 *
 * @param <L>
 */
public class SmartEventListenerSupport<L> extends org.apache.commons.lang3.event.EventListenerSupport<L> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SmartEventListenerSupport.class);

	private static final long serialVersionUID = 400058127575648418L;

	private transient L quietProxy;

	public static <T> SmartEventListenerSupport<T> create(final Class<T> listenerInterface) {
		return new SmartEventListenerSupport<>(listenerInterface);
	}

	public SmartEventListenerSupport(Class<L> listenerInterface, ClassLoader classLoader) {
		super(listenerInterface, classLoader);
		quietProxy = listenerInterface.cast(Proxy.newProxyInstance(classLoader, new Class[] { listenerInterface }, new SmartInvocationHandler<>(this,true)));
	}

	public SmartEventListenerSupport(Class<L> listenerInterface) {
		super(listenerInterface);
		quietProxy = listenerInterface.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { listenerInterface },new SmartInvocationHandler<>(this,true)));
	}
	
	public SmartEventListenerSupport(Class<L> listenerInterface,Boolean quiet) {
		super(listenerInterface);
		quietProxy = listenerInterface.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { listenerInterface },new SmartInvocationHandler<>(this,quiet)));
	}
	
	protected InvocationHandler createInvocationHandler() {
	       return new SmartInvocationHandler<L>(this);
	 }

	public L fireQuietly() {
		return quietProxy;
	}


}
