import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints;
    private boolean isSuspended;

    public Person(String personID, String firstName, String lastName, String address, String birthdate,
                  HashMap<Date, Integer> demeritPoints, boolean isSuspended) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = demeritPoints;
        this.isSuspended = isSuspended;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean addPerson() {
        if (!isValidPerson(personID, address, birthdate)) {
            return false;
        }

        try (FileWriter fileWriter = new FileWriter("person.txt", true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate + "\n");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonDetails() {

        return true;
    }

    public String addDemeritPoints() {
        return "Demerit points added successfully.";
    }

    private boolean isValidPerson(String personID, String address, String birthdate) {
        if (personID == null || personID.length() != 10
                || !(personID.charAt(0) >= '2' && personID.charAt(0) <= '9')
                || !(personID.charAt(1) >= '2' && personID.charAt(1) <= '9')
                || !(personID.charAt(8) >= 'A' && personID.charAt(8) <= 'Z')
                || !(personID.charAt(9) >= 'A' && personID.charAt(9) <= 'Z')) {
            return false;
        }

        String specialCharacter = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        int countSpecialCharacter = 0;
        for (int i = 2; i <= 7; i++) {
            if (specialCharacter.contains(String.valueOf(personID.charAt(i)))) {
                countSpecialCharacter++;
            }
        }
        if (countSpecialCharacter < 2) {
            return false;
        }

        String[] addressParts = address.split("\\|");
        if (addressParts.length != 5 || !addressParts[3].equals("Victoria")) {
            return false;
        }

        return birthdate.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }
}
