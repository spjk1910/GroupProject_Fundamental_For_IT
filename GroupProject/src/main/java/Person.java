import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public HashMap<Date, Integer> getDemeritPoints() {
        return demeritPoints;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public boolean addPerson(Set<Person> personSet) throws IOException {
        if (!isValidPerson(personID, address, birthdate)) {
            return false;
        } else if (personSet.stream().anyMatch(p -> p.getPersonID().equals(personID))) {
            return false;
        } else {
            personSet.add(this);
            saveTXT("persons.txt");
            return true;
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

    public void saveTXT(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(getPersonID()).append(",")
                    .append(getFirstName()).append(",")
                    .append(getLastName()).append(",")
                    .append(getAddress()).append(",")
                    .append(getBirthdate()).append(",");

            HashMap<Date, Integer> demeritPoints = getDemeritPoints();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (!demeritPoints.isEmpty()) {
                StringBuilder demeritPointsBuilder = new StringBuilder();
                for (Date date : demeritPoints.keySet()) {
                    demeritPointsBuilder.append(dateFormat.format(date)).append(":")
                            .append(demeritPoints.get(date)).append(";");
                }

                if (!demeritPointsBuilder.isEmpty()) {
                    demeritPointsBuilder.setLength(demeritPointsBuilder.length() - 1);
                }

                lineBuilder.append(demeritPointsBuilder);
            }

            writer.write(lineBuilder.toString());
            writer.newLine();
        }
    }
}
