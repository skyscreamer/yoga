package org.skyscreamer.yoga.util;

/**
 * User: nk
 * Date: 12/17/13
 */
public class ClassUtil {

	public static final boolean jackson2Present =
			classIsPresent("com.fasterxml.jackson.databind.ObjectMapper", ClassUtil.class.getClassLoader()) &&
					classIsPresent("com.fasterxml.jackson.core.JsonGenerator", ClassUtil.class.getClassLoader());

	public static final boolean jacksonPresent =
			classIsPresent("org.codehaus.jackson.map.ObjectMapper", ClassUtil.class.getClassLoader()) &&
					classIsPresent("org.codehaus.jackson.JsonGenerator", ClassUtil.class.getClassLoader());

	public static boolean classIsPresent(String className, ClassLoader classLoader) {
		try {
			if (classLoader == null) {
				Class.forName(className);
			} else {
				classLoader.loadClass(className);
			}
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}
}