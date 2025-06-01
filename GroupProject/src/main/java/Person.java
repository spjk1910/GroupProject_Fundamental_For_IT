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
    private HashMap<String, Number> demeritPoints;
    private boolean isSuspended;

    public Person(String personID, String firstName, String lastName, String address, String birthdate,
                  HashMap<String, Number> demeritPoints, boolean isSuspended) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = demeritPoints;
        this.isSuspended = isSuspended;
    }

    public boolean addPerson(String filePath) {
        if (!isValidPerson(personID, address, birthdate)) {
            return false;
        }

        if (isPersonIDExist(personID, filePath)) {
            System.out.println("Person ID already exists.");
            return false;
        }

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate);
            fileWriter.write(System.lineSeparator());
            return true;
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePersonDetails(String currentPersonID, String newPersonID,
                                       String newFirstName, String newLastName,
                                       String newAddress, String newBirthdate) throws IOException {

        List<String> allLines = new ArrayList<>();
        boolean found = false;
        String filePath = "updatePersonDetails.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
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

                    if (((personID.charAt(0) - '0') % 2 == 0) && !personID.equals(newPersonID)) {
                        return false;
                    }

                    if (!isValidPerson(newPersonID, newAddress, newBirthdate)) {
                        return false;
                    }

                    if (isPersonIDExist(newPersonID,filePath) && !newPersonID.equals(currentPersonID)) {
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

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false))) {
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

    public static String addDemeritPoints(String currentPersonID, HashMap<String, Number> newDemeritPoints) {
        List<String> allLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("addDemeritPoints.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    allLines.add(line);
                    continue;
                }

                String personID = data[0];
                if (!personID.equals(currentPersonID)) {
                    allLines.add(line);
                    continue;
                }

                found = true;

                String firstName = data[1];
                String lastName = data[2];
                String address = data[3];
                String birthdate = data[4];
                HashMap<String, Number> demeritPoints = new HashMap<>();

                if (data.length > 5 && !data[5].isEmpty()) {
                    String[] demeritPointsArray = data[5].split(";");
                    for (String dp : demeritPointsArray) {
                        String[] parts = dp.split(":");
                        if (parts.length == 2) {
                            demeritPoints.put(parts[0], Integer.parseInt(parts[1]));
                        }
                    }
                }

                for (Map.Entry<String, Number> entry : newDemeritPoints.entrySet()) {
                    String offenseDate = entry.getKey();
                    Number points = entry.getValue();

                    if ((points instanceof Float || points instanceof Double) && points.doubleValue() != points.intValue()) {
                        return "Failed";
                    }
                    if (!(points instanceof Integer) && !(points instanceof Float) && !(points instanceof Double)) {
                        return "Failed";
                    }

                    if (!isValidDate(offenseDate) || points.intValue() < 1 || points.intValue() > 6) {
                        return "Failed";
                    }

                    demeritPoints.put(offenseDate, points);
                }

                // Determine suspension
                int age = getAge(birthdate);
                boolean isSuspended = false;
                List<String> sortedDates = new ArrayList<>(demeritPoints.keySet());
                Collections.sort(sortedDates);

                for (int i = 0; i < sortedDates.size(); i++) {
                    String date1 = sortedDates.get(i);
                    int sum = 0;
                    for (int j = i; j < sortedDates.size(); j++) {
                        String date2 = sortedDates.get(j);
                        if (isWithin2Year(date1, date2)) {
                            sum += demeritPoints.get(date2).intValue();
                        }
                    }
                    if ((age < 21 && sum > 6) || (age >= 21 && sum > 12)) {
                        isSuspended = true;
                        break;
                    }
                }

                StringBuilder newLine = new StringBuilder();
                newLine.append(personID).append(",")
                        .append(firstName).append(",")
                        .append(lastName).append(",")
                        .append(address).append(",")
                        .append(birthdate).append(",");

                for (Map.Entry<String, Number> entry : demeritPoints.entrySet()) {
                    newLine.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
                }
                if (newLine.charAt(newLine.length() - 1) == ';') {
                    newLine.setLength(newLine.length() - 1);
                }

                newLine.append(",").append(isSuspended ? "true" : "false");
                allLines.add(newLine.toString());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return "Failed";
        }

        if (!found) {
            System.out.println("Person ID not found.");
            return "Failed";
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("addDemeritPoints.txt", false))) {
            for (String l : allLines) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return "Failed";
        }

        return "Success";
    }

    private static boolean isValidPerson(String personID, String address, String birthdate) {
        if (personID == null || personID.length() != 10
                || !(personID.charAt(0) >= '2' && personID.charAt(0) <= '9')
                || !(personID.charAt(1) >= '2' && personID.charAt(1) <= '9')
                || !(personID.charAt(8) >= 'A' && personID.charAt(8) <= 'Z')
                || !(personID.charAt(9) >= 'A' && personID.charAt(9) <= 'Z')) {
            return false;
        }

        String specialCharacter = "~!@#$%^&*(){}[]|;':\"<>,./?`";
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

    public static boolean isValidDate(String dateStr) {
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

    public static int getAge(String birthdate) {
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

    private static boolean isPersonIDExist(String personID, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    public static boolean isWithin2Year(String firstDate, String secondDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse(firstDate, formatter);
        LocalDate date2 = LocalDate.parse(secondDate, formatter);

        long yearsBetween = Math.abs(ChronoUnit.YEARS.between(date1, date2));

        return yearsBetween < 2;
    }

    public static boolean getIsSuspended(String personID) throws IOException {
        File file = new File("addDemeritPoints.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(personID + ",")) {
                String[] parts = line.split(",");
                String suspendedPart = parts[parts.length - 1].trim();

                reader.close();

                return suspendedPart.equalsIgnoreCase("true");
            }
        }

        reader.close();
        throw new IllegalArgumentException("Person ID not found: " + personID);
    }

    public static void main(String[] args) throws IOException {
        String personClassTest_testCase4_testData2 = Person.addDemeritPoints("123@@@ABCD",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 2);
                    put("01-04-2020", 2.0);
                    put("01-05-2025", 2);
                }});
        boolean personClassTest_testCase1_testData2_suspended = Person.getIsSuspended("123@@@ABCD");
        System.out.println("Test Case 2: " + personClassTest_testCase4_testData2);
        System.out.println("Is Suspended: " + personClassTest_testCase1_testData2_suspended);
    }
}

