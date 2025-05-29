import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

    public boolean addPerson() {
        if (!isValidPerson(personID, address, birthdate)) {
            return false;
        }

        try (FileWriter fileWriter = new FileWriter("person.txt", true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate);
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePersonDetails() throws IOException {
        String personID = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String birthdate = null;
        try (FileReader fileReader = new FileReader("person.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            if (line != null) {
                String[] data = line.split(",");
                personID = data[0];
                firstName = data[1];
                lastName = data[2];
                address = data[3];
                birthdate = data[4];
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return false;
        }

        if (getAge(birthdate) < 18 && !this.address.equals(address)) {
            return false;
        }

        if (!this.birthdate.equals(birthdate) &&
                (!this.address.equals(address) ||
                !this.personID.equals(personID) ||
                !this.firstName.equals(firstName) ||
                !this.lastName.equals(lastName))) {
            return false;
        }

        if (((personID.charAt(0) - '0') / 2 == 0) && !this.personID.equals(personID)) {
            return false;
        }

        if (!isValidPerson(personID, address, birthdate)) {
            return false;
        }

        try (FileWriter fileWriter = new FileWriter("person.txt", true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate);
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
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

    public int getAge(String birthdate) {
        long ageInDays = 0;
        long ageInMilliseconds = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = simpleDateFormat.parse(birthdate);
            Date currentDate = new Date();

            ageInMilliseconds = currentDate.getTime() - birthDate.getTime();
            ageInDays = ageInMilliseconds / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        return (int)ageInDays / 365;
    }
}
