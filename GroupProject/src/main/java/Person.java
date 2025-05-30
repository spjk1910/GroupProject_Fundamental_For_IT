import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<String, Integer> demeritPoints;
    private boolean isSuspended;

    public Person(String personID, String firstName, String lastName, String address, String birthdate,
                  HashMap<String, Integer> demeritPoints, boolean isSuspended) {
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

        if (isPersonIDExist(personID)) {
            System.out.println("Person ID already exists.");
            return false;
        }

        try (FileWriter fileWriter = new FileWriter("person.txt", true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate);
            fileWriter.write(System.lineSeparator());
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePersonDetails(String currentPersonID, String newPersonID,
                                       String newFirstName, String newLastName,
                                       String newAddress, String newBirthdate) throws IOException {

        List<String> allLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("person.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    allLines.add(line);
                    continue;
                }

                String personID = data[0];
                String firstName = data[1];
                String lastName = data[2];
                String address = data[3];
                String birthdate = data[4];

                if (personID.equals(currentPersonID)) {
                    if (getAge(birthdate) < 18 && !address.equals(newAddress)) {
                        return false;
                    }

                    if (!birthdate.equals(newBirthdate) &&
                            (!address.equals(newAddress) ||
                                    !personID.equals(newPersonID) ||
                                    !firstName.equals(newFirstName) ||
                                    !lastName.equals(newLastName))) {
                        return false;
                    }

                    if (((personID.charAt(0) - '0') % 2 == 0) && !this.personID.equals(personID)) {
                        return false;
                    }

                    if (!isValidPerson(newPersonID, newAddress, newBirthdate)) {
                        return false;
                    }

                    if (isPersonIDExist(newPersonID) && !newPersonID.equals(currentPersonID)) {
                        return false;
                    }

                    // update line
                    line = newPersonID + "," + newFirstName + "," + newLastName + "," + newAddress + "," + newBirthdate;
                    found = true;
                }

                allLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return false;
        }

        if (!found) {
            System.out.println("Person ID not found.");
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("person.txt", false))) {
            for (String l : allLines) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public String addDemeritPoints(String currentPersonID, HashMap<String, Integer> newDemeritPoints) {
        List<String> allLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("person.txt"))) {
            StringBuilder line;
            while ((line = new StringBuilder(bufferedReader.readLine())) != null) {
                String[] data = line.toString().split(",");
                if (data.length < 5) {
                    allLines.add(line.toString());
                    continue;
                }

                String personID = data[0];
                String birthdate = data[4];
                String demeritPointsStr = data.length > 5 ? data[5] : "";

                HashMap<String, Integer> demeritPoints = new HashMap<>();
                if (!demeritPointsStr.isEmpty()) {
                    String[] demeritPointsArray = demeritPointsStr.split(";");
                    for (String demeritPoint : demeritPointsArray) {
                        String[] parts = demeritPoint.split(":");
                        if (parts.length == 2) {
                            demeritPoints.put(parts[0], Integer.parseInt(parts[1]));
                        }
                    }
                }

                if (personID.equals(currentPersonID)) {
                    found = true;

                    for (Map.Entry<String, Integer> entry : newDemeritPoints.entrySet()) {
                        String offenseDate = entry.getKey();
                        int pts = entry.getValue();

                        if (!isValidDate(offenseDate)) return "Failed";

                        if (pts < 1 || pts > 6) return "Failed";
                    }

                    demeritPoints.putAll(newDemeritPoints);
                    int age = getAge(birthdate);
                    isSuspended = false;
                    int totalPoints = 0;

                    for (int i = 0; i < demeritPoints.size(); i++) {
                        for (int j = i; j < demeritPoints.size(); j++) {
                            if (isWithin2Year(demeritPoints.keySet().toArray()[i].toString(),
                                    demeritPoints.keySet().toArray()[j].toString())) {
                                totalPoints += demeritPoints.get(demeritPoints.keySet().toArray()[i]) +
                                        demeritPoints.get(demeritPoints.keySet().toArray()[j]);
                            }
                        }

                        if ((age < 21 && totalPoints > 6) || (age >= 21 && totalPoints > 12)) isSuspended = true;
                    }
                }
            }
            line = new StringBuilder(currentPersonID + "," + firstName + "," + lastName + "," + address + "," +
                    birthdate + ",");
            for (Map.Entry<String, Integer> entry : demeritPoints.entrySet()) {
                line.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
            }

            allLines.add(line.toString());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return "Failed";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!found) {
            System.out.println("Person ID not found.");
            return "Failed";
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("person.txt", false))) {
            for (String l : allLines) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return "Failed";
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
        if (addressParts.length != 5 || Arrays.stream(addressParts).anyMatch(String::isEmpty)
                || !addressParts[3].equals("Victoria")) {
            return false;
        }

        if (!isValidDate(birthdate)) {
            return false;
        }

        return true;
    }

    public boolean isValidDate(String dateStr) {
        if (!dateStr.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
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

    private boolean isPersonIDExist(String personID) {
        try (BufferedReader br = new BufferedReader(new FileReader("person.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(personID + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    public boolean isWithin2Year(String firstDate, String secondDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse(firstDate, formatter);
        LocalDate date2 = LocalDate.parse(secondDate, formatter);

        long yearsBetween = Math.abs(ChronoUnit.YEARS.between(date1, date2));

        return yearsBetween < 2;
    }

    public static void main(String[] args) {
        Person personClassTest_testCase1_testData1 = new Person("56s_d%&fAB","John","Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990",null,false);
        boolean result = personClassTest_testCase1_testData1.addPerson();
        if (result) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }
    }
}

