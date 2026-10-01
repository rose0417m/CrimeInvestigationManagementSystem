package model;

public class Suspect extends Person {

    private int age;
    private String gender;

    public Suspect() {
    }

    public Suspect(int personId, String name, String phone,
                   String email, String address,
                   int age, String gender) {

        super(personId, name, phone, email, address);

        this.age = age;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getRoleDescription() {
        return "Suspect";
    }
}