package edu.nitrkl.graphics.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;

import org.json.JSONObject;

public class ReflectTest {

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		Class<?> cls = Class.forName("edu.nitrkl.graphics.components.Polygon2");
		Constructor<?> cons = cls
				.getConstructor(new Class[] { JSONObject.class });

		// Object obj = cls.newInstance();

		// cons.

		System.out.println("required Constructor: " + cons.toString());

		JComponent component = (JComponent) cons.newInstance("");

		System.out.println(component);

		for (Constructor<?> str : cls.getConstructors()) {
			System.out.println("Declared Cons: " + str);
		}

		for (Method str : cls.getMethods()) {
			System.out.println("Declared Methods: " + str);
		}

		for (Annotation str : cls.getAnnotations()) {
			System.out.println("Declared Annotations: " + str);
		}

		for (Class<?> str : cls.getClasses()) {
			System.out.println("Declared Class: " + str);
		}

		for (Field str : cls.getDeclaredFields()) {
			System.out.println("Declared Field: " + str);
		}
	}

}
