package com.alkalus.game.util.reflect;

import java.lang.reflect.*;
import com.alkalus.game.core.engine.objects.Logger;

public class ReflectionUtils {

	public static Field getField(final Class clazz, final String fieldName) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (final NoSuchFieldException e) {
			final Class<?> superClass = clazz.getSuperclass();
			if (superClass == null) {
				Logger.REFLECTION("Failed to get Field from Class. "+fieldName+" does not existing within "+clazz.getCanonicalName()+".");
				throw e;
			}
			Logger.REFLECTION("Failed to get Field from Class. "+fieldName+" does not existing within "+clazz.getCanonicalName()+". Trying super class.");
			return getField(superClass, fieldName);
		}
	}

	public static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) ||
				!Modifier.isPublic(field.getDeclaringClass().getModifiers()))
		{
			field.setAccessible(true);
		}
	}

	//Some Reflection utils - http://stackoverflow.com/questions/14374878/using-reflection-to-set-an-object-property
	@SuppressWarnings("unchecked")
	public static <V> V getField(final Object object, final String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				final Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (V) field.get(object);
			} catch (final NoSuchFieldException e) {
				Logger.REFLECTION("getField("+object.toString()+", "+fieldName+") failed.");
				clazz = clazz.getSuperclass();
			} catch (final Exception e) {
				Logger.REFLECTION("getField("+object.toString()+", "+fieldName+") failed.");
				throw new IllegalStateException(e);
			}
		}
		return null;
	}

	public static boolean setField(final Object object, final String fieldName, final Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				final Field field = clazz.getDeclaredField(fieldName);
				makeAccessible(field);
				field.set(object, fieldValue);
				return true;
			} catch (final NoSuchFieldException e) {
				Logger.REFLECTION("setField("+object.toString()+", "+fieldName+") failed.");
				clazz = clazz.getSuperclass();
			} catch (final Exception e) {
				Logger.REFLECTION("setField("+object.toString()+", "+fieldName+") failed.");
				throw new IllegalStateException(e);
			}
		}
		return false;
	}

	public static boolean doesClassExist(final String classname) {
		boolean exists = true;
		try {
			// Load any class that should be present if driver's available
			Class.forName(classname);
		} catch (final ClassNotFoundException e) {
			// Driver is not available
			exists = false;
		}
		return exists;
	}

	/**
	 * Get the method name for a depth in call stack. <br />
	 * Utility function
	 * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
	 * @return method name
	 */
	public static String getMethodName(final int depth) {
		final StackTraceElement[] ste = new Throwable().getStackTrace();
		//System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
		return ste[depth+1].getMethodName();
	}


	/**
	 * Allows to change the state of an immutable instance. Huh?!?
	 */
	public static void setFieldValue(Class<?> clazz,  String fieldName, Object newValue) throws Exception {
		Field nameField = getField(clazz, fieldName);
		setValue(clazz, nameField, newValue);
	}

	/**
	 * Allows to change the state of final statics. Huh?!?
	 */
	public static void setDefault(Class<?> clazz, String fieldName, Object newValue) throws Exception {
		Field staticField = clazz.getDeclaredField(fieldName);
		setValue(null, staticField, newValue);
	}

	/**
	 * 
	 * Set the value of a field reflectively.
	 */
	protected static void setValue(Object owner, Field field, Object value) throws Exception {
		makeModifiable(field);
		field.set(owner, value);
	}

	/**
	 * Force the field to be modifiable and accessible.
	 */
	protected static void makeModifiable(Field nameField) throws Exception {
		nameField.setAccessible(true);
		int modifiers = nameField.getModifiers();
		Field modifierField = nameField.getClass().getDeclaredField("modifiers");
		modifiers = modifiers & ~Modifier.FINAL;
		modifierField.setAccessible(true);
		modifierField.setInt(nameField, modifiers);
	}

	public static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}


	public static void setByte(Object clazz,  String fieldName, byte newValue) throws Exception {
		Field nameField = getField(clazz.getClass(), fieldName);
		nameField.setAccessible(true);
		int modifiers = nameField.getModifiers();
		Field modifierField = nameField.getClass().getDeclaredField("modifiers");
		modifiers = modifiers & ~Modifier.FINAL;
		modifierField.setAccessible(true);
		modifierField.setInt(nameField, modifiers);
		//Utils.LOG_INFO("O-"+(byte) nameField.get(clazz) + " | "+newValue);
		nameField.setByte(clazz, newValue);
		//Utils.LOG_INFO("N-"+(byte) nameField.get(clazz));

		/*final Field fieldA = getField(clazz.getClass(), fieldName);
		fieldA.setAccessible(true);
		fieldA.setByte(clazz, newValue);*/

	}
	
	public static boolean invoke(Object objectInstance, String methodName, Class[] parameters, Object[] values){
		if (objectInstance == null || methodName == null || parameters == null || values == null){
			Logger.REFLECTION("Null value when trying to Dynamically invoke "+methodName+" on an object of type: "+objectInstance.getClass().getName());
			return false;
		}		
		Class mLocalClass = (objectInstance instanceof Class ? (Class) objectInstance : objectInstance.getClass());
		Logger.REFLECTION("Trying to invoke "+methodName+" on an instance of "+mLocalClass.getCanonicalName()+".");
		try {
			Method mInvokingMethod = mLocalClass.getDeclaredMethod(methodName, parameters);
			if (mInvokingMethod != null){
				Logger.REFLECTION(methodName+" was not null.");
				if ((boolean) mInvokingMethod.invoke(objectInstance, values)){
					Logger.REFLECTION("Successfully invoked "+methodName+".");
					return true;
				}
				else {
					Logger.REFLECTION("Invocation failed for "+methodName+".");
				}
			}
			else {
				Logger.REFLECTION(methodName+" is null.");				
			}
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Logger.REFLECTION("Failed to Dynamically invoke "+methodName+" on an object of type: "+mLocalClass.getName());
		}		

		Logger.REFLECTION("Invoke failed or did something wrong.");		
		return false;
	}



}
