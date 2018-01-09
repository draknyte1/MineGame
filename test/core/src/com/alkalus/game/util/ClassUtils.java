package com.alkalus.game.util;

import java.lang.reflect.*;

public class ClassUtils {

	/*
	 * @ if (isPresent("com.optionaldependency.DependencyClass")) { // This
	 * block will never execute when the dependency is not present // There is
	 * therefore no more risk of code throwing NoClassDefFoundException.
	 * executeCodeLinkingToDependency(); }
	 */
	public static boolean isPresent(final String className) {
		try {
			Class.forName(className);
			return true;
		} catch (final Throwable ex) {
			// Class or one of its dependencies is not present...
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Method getMethodViaReflection(final Class<?> lookupClass, final String methodName,
			final boolean invoke) throws Exception {
		final Class<? extends Class> lookup = lookupClass.getClass();
		final Method m = lookup.getDeclaredMethod(methodName);
		m.setAccessible(true);// Abracadabra
		if (invoke) {
			m.invoke(lookup);// now its OK
		}
		return m;
	}

	public static Class<?> getNonPublicClass(final String className) {
		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (final ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// full package name --------^^^^^^^^^^
		// or simpler without Class.forName:
		// Class<package1.A> c = package1.A.class;

		if (null != c) {
			// In our case we need to use
			Constructor<?> constructor = null;
			try {
				constructor = c.getDeclaredConstructor();
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// note: getConstructor() can return only public constructors
			// so we needed to search for any Declared constructor

			// now we need to make this constructor accessible
			if (null != constructor) {
				constructor.setAccessible(true);// ABRACADABRA!

				try {
					final Object o = constructor.newInstance();
					return (Class<?>) o;
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
