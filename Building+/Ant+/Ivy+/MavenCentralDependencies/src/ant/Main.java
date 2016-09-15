package ant;

import org.apache.commons.lang3.StringUtils;

public class Main {
	public static void main(String[] args) {
		boolean isEquals = StringUtils.equalsIgnoreCase("a", "A");
		System.out.println("Hello, Ant!");
		System.out.println(isEquals);
	}
}