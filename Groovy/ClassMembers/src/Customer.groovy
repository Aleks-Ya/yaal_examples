class Customer {
    //Private fields with public getter and setter:
    Integer id

    //Final private field with public getter (without setter)
    final String name

    //Protected field with public getter and setter
    protected Date dob

    static void main(args) {
        def customer = new Customer(id: 1, name: "Gromit", dob: new Date())
        println("Hello ${customer.name}")
    }
}