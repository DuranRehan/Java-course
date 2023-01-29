package atlg.g56055.mentoring.dto;

public class StudentDto extends Dto<Integer> {

    private String firstName;
    private String lastName;

    public StudentDto(Integer matricule, String firstName, String lastName) {
        this.key = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return this.getKey() + "," + this.getFirstName() + "," + this.getLastName();
    }

}
