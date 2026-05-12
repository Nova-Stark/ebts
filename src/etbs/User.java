package etbs;

public class User {
    private String name;
    private long phone;
    private int age;

    public User(String name, long phone, int age) {
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public long getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }
}