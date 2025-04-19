public class Person {


    // Constructor, "Person"
    public Person(String lastName, String firstName, int dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
    }


    // Class variables, aka. attributes, aka. variables
    private final String lastName;
    private final String firstName;
    private final int dateOfBirth;

    // Getters

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    // Return name on form "Lastname, Firstname".
}

