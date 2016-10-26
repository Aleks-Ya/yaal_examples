package subtype.resolver;

public class Parent {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return Parent.class.getSimpleName() + "{" + "name=" + name + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Parent) {
			Parent o = (Parent) obj; 
			return name != null ? name.equals(o.name) : false;
		}
		return false;
	}
}
