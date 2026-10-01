package model;

public class Detective extends Person {

    private String specialization;

    public Detective() {
    }

    public Detective(int personId, String name, String phone,
                     String email, String address,
                     String specialization) {

        super(personId, name, phone, email, address);

        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String getRoleDescription() {
        return "Detective";
    }
}