import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<Map.Entry<Date, Integer>> sortedEntries = demeritPoints.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();

        for (Map.Entry<Date, Integer> entry : sortedEntries) {
            String offenseDate = entry.toString();
            float points = entry.getValue();

            if (!isValidDate(offenseDate)) return ("Failed");

            if (points == (int) points && !(points >= 1 && points <= 6)) {
                return ("Failed");
            }
        }

        for (int i = 0; i < sortedEntries.size(); i++) {
            Date firstDate = sortedEntries.get(i).getKey();
            int totalPoints = sortedEntries.get(i).getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstDate);
            calendar.add(Calendar.YEAR, 2);
            Date endDate = calendar.getTime();

            for (int j = i; j < sortedEntries.size(); j++) {
                Date checkDate = sortedEntries.get(j).getKey();
                if (checkDate.before(endDate) || checkDate.equals(endDate)) {
                    totalPoints += sortedEntries.get(j).getValue();
                } else {
                    break;
                }
            }

            if ((getAge(birthdate) < 21 && totalPoints > 6) ||
                    (getAge(birthdate) >= 21 && totalPoints > 12)) {
                this.isSuspended = true;

            } else this.isSuspended = false;
        }

        return ("Success");
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

        if (!isValidDate(birthdate)) {
            return false;
        }

        return true;
    }

    public boolean isValidDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            simpleDateFormat.setLenient(false);
            Date tempDate = simpleDateFormat.parse(date);
            if (tempDate.after(new Date())) {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return false;
        }

        return true;
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

        return (int) ageInDays / 365;
    }
}
