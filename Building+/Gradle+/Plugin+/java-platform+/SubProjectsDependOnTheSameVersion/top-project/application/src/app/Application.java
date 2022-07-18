package app;

import org.apache.commons.lang3.tuple.Pair;
import lib.StringLib;

public class Application {
	public static void main(String[] args) throws ClassNotFoundException {
		String className = "org.postgresql.Driver";
		Class<?> clazz = Class.forName(className);
		System.out.println(clazz);
		if (!clazz.getName().equals(className)) {
			throw new AssertionError();
		}
	}

	public Pair stringInfo(String s) {
		return Pair.of(StringLib.format(s), s.length());
	}
}