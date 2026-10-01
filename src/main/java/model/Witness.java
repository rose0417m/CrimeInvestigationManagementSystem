package model;

public class Witness extends Person {

    private String statement;

    public Witness() {
    }

    public Witness(int personId, String name, String phone,
                   String email, String address,
                   String statement) {

        super(personId, name, phone, email, address);

        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String getRoleDescription() {
        return "Witness";
    }
}

