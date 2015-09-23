package edu.nitrkl.graphics.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class<?> cls = Class.forName("edu.nitrkl.graphics.components.Polygon2");
		Object obj = cls.newInstance();

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
