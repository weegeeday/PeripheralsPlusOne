package com.austinv11.peripheralsplusplus.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class LuaApi implements InvocationHandler {

/**
 * Gets the names that the api can be called by in lua
 */
public abstract String[] getNames();

/**
 * Called on computer startup
 */
public abstract void startup();

/**
 * Called to tick the API
 */
public abstract void advance(double dt);

/**
 * Called on computer shutdown
 */
public abstract void shutdown();

@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
switch (method.getName()) {
case "getNames":
return getNames();
case "startup":
startup();
break;
case "advance":
advance((Double) args[0]);
break;
case "shutdown":
shutdown();
break;
}
return null;
}
}
