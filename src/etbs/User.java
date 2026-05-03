package etbs;

public class User {
    private String name;
    private int phone;
    private int age;

    public User(String name, int phone, int age) {
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }
}