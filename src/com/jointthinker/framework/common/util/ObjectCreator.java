package com.jointthinker.framework.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectCreator {
	/**
	   * Private constructor. This class can not be instantiated
	   */
	  private ObjectCreator() {
	  }

	  /**
	   * Instantaite an Object from a given class name
	   * @param className full qualified name of the class
	   * @return the instantaited Object
	   * @exception java.lang.Exception if instantiation failed
	   */
	  public static Object createObject(String className) throws Exception {
	    return createObject(Class.forName(className));
	  }

	  /**
	   * Instantaite an Object instance
	   * @param classObject Class object representing the object type to be instantiated
	   * @return the instantaied Object
	   * @exception java.lang.Exception if instantiation failed
	   */
	  public static Object createObject(Class classObject) throws Exception {
	    Object object = null;
	    return classObject.newInstance();
	  }

	  /**
	   * Instantaite an Object instance, requires a constructor with parameters
	   * @param className full qualified name of the class
	   * @param params an array including the required parameters to instantaite the object
	   * @return the instantaited Object
	   * @exception java.lang.Exception if instantiation failed
	   */
	  public static Object createObject(String className, Object[] params) throws
	      Exception {
	    return createObject(Class.forName(className), params);
	  }

	  /**
	   * Instantaite an Object instance, requires a constractor with parameters
	   * @param classObject, Class object representing the object type to be instantiated
	   * @param params an array including the required parameters to instantaite the object
	   * @return the instantaied Object
	   * @exception java.lang.Exception if instantiation failed
	   */
	  public static Object createObject(Class classObject, Object[] params) throws
	      Exception {
	    Constructor[] constructors = classObject.getConstructors();
	    Object object = null;
	    for (int counter = 0; counter < constructors.length; counter++) {
	      try {
	        object = constructors[counter].newInstance(params);
	      }
	      catch (Exception e) {
	        if (e instanceof InvocationTargetException)
	          ( (InvocationTargetException) e).getTargetException().printStackTrace();
	        //do nothing, try the next constructor
	      }
	    }
	    if (object == null)
	      throw new InstantiationException();
	    return object;
	  }
}
