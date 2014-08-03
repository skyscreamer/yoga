package org.skyscreamer.yoga.util;

import org.skyscreamer.yoga.view.json.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: nk
 * Date: 12/17/13
 */
public class JacksonLibraryUtil {
	public static final String SYSTEM_PROPERTY = "yoga.jackson-library";
	public static final String JACKSON = "jackson";
	public static final String JACKSON2 = "jackson2";

	public static final boolean jackson2Present =
			classIsPresent("com.fasterxml.jackson.databind.ObjectMapper", JacksonLibraryUtil.class.getClassLoader()) &&
					classIsPresent("com.fasterxml.jackson.core.JsonGenerator", JacksonLibraryUtil.class.getClassLoader());

	public static final boolean jacksonPresent =
			classIsPresent("org.codehaus.jackson.map.ObjectMapper", JacksonLibraryUtil.class.getClassLoader()) &&
					classIsPresent("org.codehaus.jackson.JsonGenerator", JacksonLibraryUtil.class.getClassLoader());

	private static boolean classIsPresent(String className, ClassLoader classLoader) {
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

	public static JsonSerializer selectJacksonSerializer() {
		String property = System.getProperty( SYSTEM_PROPERTY );
		if (jackson2Present && (property == null || property.equals(JACKSON2)))
		{
			return new Jackson2Serializer();
		}
		else if (jacksonPresent && (property == null || property.equals(JACKSON)))
		{
			return new JacksonSerializer();
		}
		else throw new IllegalStateException( "Jackson library not in classpath" );
	}

	public static GeneratorAdapter selectGeneratorAdapter(OutputStream os) throws IOException {
		String property = System.getProperty( SYSTEM_PROPERTY );
		if (jackson2Present && (property == null || property.equals(JACKSON2)))
		{
			return new Jackson2JsonGeneratorAdapter( os );
		}
		else if (jacksonPresent && (property == null || property.equals(JACKSON)))
		{
			return new JacksonJsonGeneratorAdapter( os );
		}
		else throw new IllegalStateException( "Jackson Library not in classpath" );
	}
}