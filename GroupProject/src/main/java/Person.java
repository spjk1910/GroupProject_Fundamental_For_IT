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

    // Constructor
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

    // Method to add a person to a file
    public boolean addPerson(String filePath) {
        // Validate person details
        if (!isValidPerson(personID, address, birthdate)) { // Check if person details are valid
            return false; // Invalid person details
        }

        // Check if person ID already exists
        if (isPersonIDExist(personID, filePath)) {
            System.out.println("Person ID already exists.");
            return false; // Person ID already exists
        }

        // Write person details to file
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate); // Write person details
            fileWriter.write(System.lineSeparator()); // Add a new line after writing the person details
            return true; // Successfully added person
        } catch (Exception e) { // Handle any exceptions that occur during file writing
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    // Method to update person details in a file
    public static boolean updatePersonDetails(String currentPersonID, String newPersonID,
                                       String newFirstName, String newLastName,
                                       String newAddress, String newBirthdate) throws IOException {

        List<String> allLines = new ArrayList<>(); // Store all lines from the file so that we can update the specific line
        boolean found = false; // Flag to check if the person ID is found
        String filePath = "updatePersonDetails.txt"; // Path to the file where person details are stored

        // Read the file line by line
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { // Read each line from the file
                String[] data = line.split(","); // Split the line by comma to get individual data fields
                // (format of txt file is that all field are separated by comma)
                if (data.length < 5) { // If the line does not have enough data fields, skip it
                    allLines.add(line); // Add the line as it is to the list
                    continue; // Skip to the next line
                }

                // Extract data fields from the line
                String personID = data[0];
                String firstName = data[1];
                String lastName = data[2];
                String address = data[3];
                String birthdate = data[4];

                // Check if the current line matches the person ID we are looking for
                if (personID.equals(currentPersonID)) {
                    if (getAge(birthdate) < 18 && !address.equals(newAddress)) { // If the person is under 18, they cannot change their address
                        return false;
                    }

                    // If the birthdate is changed, we will check if other fileds are also changed
                    if (!birthdate.equals(newBirthdate) &&
                            (!address.equals(newAddress) ||
                                    !personID.equals(newPersonID) ||
                                    !firstName.equals(newFirstName) ||
                                    !lastName.equals(newLastName))) {
                        return false;  // If the birthdate is changed, we will not allow other fields to be changed
                    }

                    // Check if first character of personID is even
                    // - '0' to convert char to int and % 2 to check if it is even
                    if (((personID.charAt(0) - '0') % 2 == 0) && !personID.equals(newPersonID)) {
                        return false;
                    }

                    // Validate new person details
                    if (!isValidPerson(newPersonID, newAddress, newBirthdate)) {
                        return false;
                    }

                    // Check if new person ID already exists in the file
                    if (isPersonIDExist(newPersonID,filePath) && !newPersonID.equals(currentPersonID)) {
                        return false;
                    }

                    // Update the line with new person details
                    line = newPersonID + "," + newFirstName + "," + newLastName + "," + newAddress + "," + newBirthdate;
                    found = true; // Person ID found, so we will update the line
                }

                // Add the line (either updated or original) to the list of all lines
                allLines.add(line);
            }
        } catch (FileNotFoundException e) { // Handle file not found exception
            System.out.println("File not found: " + e.getMessage());
            return false;
        }

        // If the person ID was not found in the file, we will return false
        if (!found) {
            System.out.println("Person ID not found.");
            return false;
        }

        // Write all lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String l : allLines) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false; // Handle any exceptions that occur during file writing
        }

        return true; // Successfully updated person details
    }

    // Method to add demerit points to a person
    public static String addDemeritPoints(String currentPersonID, HashMap<String, Number> newDemeritPoints) {
        List<String> allLines = new ArrayList<>(); // Store all lines from the file so that we can update the specific line
        boolean found = false; // Flag to check if the person ID is found
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("addDemeritPoints.txt"))) { // Read the file line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(","); // Split the line by comma to get individual data fields
                if (data.length < 5) { // If the line does not have enough data fields, skip it
                    allLines.add(line); // Add the line as it is to the list
                    continue;
                }

                String personID = data[0]; // Extract person ID from the line
                if (!personID.equals(currentPersonID)) { // If the person ID does not match the current person ID, add the line as it is
                    allLines.add(line);
                    continue;
                }

                found = true; // Person ID found, so we will update the line

                // Extract other data fields from the line
                String firstName = data[1];
                String lastName = data[2];
                String address = data[3];
                String birthdate = data[4];
                HashMap<String, Number> demeritPoints = new HashMap<>(); // Store demerit points from the line

                // Parse existing demerit points if available
                if (data.length > 5 && !data[5].isEmpty()) {
                    String[] demeritPointsArray = data[5].split(";"); // Split demerit points by semicolon
                    // Demerit points are in the format "date:points;date2:points2... etc"
                    for (String dp : demeritPointsArray) { // Split each demerit point by colon
                        String[] parts = dp.split(":"); // Split by colon to get date and points
                        if (parts.length == 2) { // If both date and points are present
                            demeritPoints.put(parts[0], Integer.parseInt(parts[1])); // Add to demerit points map
                        }
                    }
                }

                // Validate new demerit points
                for (Map.Entry<String, Number> entry : newDemeritPoints.entrySet()) {
                    String offenseDate = entry.getKey(); // Get the offense date
                    Number points = entry.getValue(); // Get the points for the offense date

                    // Validate offense date and points
                    // Check if points is a whole number (a float/double with no decimal part)
                    if ((points instanceof Float || points instanceof Double) && points.doubleValue() != points.intValue()) {
                        return "Failed";
                    }
                    if (!(points instanceof Integer) && !(points instanceof Float) && !(points instanceof Double)) {
                        return "Failed";
                    }

                    // Check if offense date is valid and points are within the range of 1 to 6
                    if (!isValidDate(offenseDate) || points.intValue() < 1 || points.intValue() > 6) {
                        return "Failed";
                    }

                    demeritPoints.put(offenseDate, points); // Add the new demerit points to the map
                }

                // Determine suspension
                int age = getAge(birthdate); // Calculate age based on birthdate
                boolean isSuspended = false; // Flag to check if the person is suspended
                List<String> sortedDates = new ArrayList<>(demeritPoints.keySet());
                Collections.sort(sortedDates); // Sort the dates in ascending order

                for (int i = 0; i < sortedDates.size(); i++) { // Iterate through sorted dates
                    String date1 = sortedDates.get(i); // Get the first date
                    int sum = 0; // Initialize sum of demerit points for offenses within 2 years
                    for (int j = i; j < sortedDates.size(); j++) { // Iterate through remaining dates
                        String date2 = sortedDates.get(j); // Get the second date
                        if (isWithin2Year(date1, date2)) { // Check if the two dates are within 2 years
                            sum += demeritPoints.get(date2).intValue(); // Add the demerit points for the second date to the sum
                        }
                    }
                    if ((age < 21 && sum > 6) || (age >= 21 && sum > 12)) { // Check if the sum of demerit points exceeds the limit based on age
                        isSuspended = true; // Set suspension flag to true
                        break; // Exit the loop if suspension condition is met
                    }
                }

                // Prepare the new line to be written back to the file
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
        } catch (IOException e) { // Handle any exceptions that occur during file reading
            System.out.println("Error reading file: " + e.getMessage());
            return "Failed";
        }

        if (!found) { // If the person ID was not found in the file, we will return "Failed"
            System.out.println("Person ID not found.");
            return "Failed";
        }

        // Write all lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("addDemeritPoints.txt", false))) {
            for (String l : allLines) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return "Failed";
        }

        return "Success"; // Successfully added demerit points
    }

    // Method to validate person details
    private static boolean isValidPerson(String personID, String address, String birthdate) {
        // Check if personID is valid (length is 10, first two characters are between '2' and '9', 2 last characters are uppercase letters)
        if (personID == null || personID.length() != 10
                || !(personID.charAt(0) >= '2' && personID.charAt(0) <= '9')
                || !(personID.charAt(1) >= '2' && personID.charAt(1) <= '9')
                || !(personID.charAt(8) >= 'A' && personID.charAt(8) <= 'Z')
                || !(personID.charAt(9) >= 'A' && personID.charAt(9) <= 'Z')) {
            return false;
        }

        String specialCharacter = "~!@#$%^&*(){}[]|;':\"<>,./?`"; // List of special characters
        int countSpecialCharacter = 0; // Count of special characters in personID
        for (int i = 2; i <= 7; i++) { // Check characters from index 3 to 8
            if (specialCharacter.contains(String.valueOf(personID.charAt(i)))) { // If character is a special character (in a list above)
                countSpecialCharacter++; // Increment the count
            }
        }
        if (countSpecialCharacter < 2) { // If there are less than 2 special characters, return false
            return false;
        }

        String[] addressParts = address.split("\\|"); // Split address by pipe character
        if (addressParts.length != 5 || Arrays.stream(addressParts).anyMatch(String::isEmpty) // Check if address has 5 parts and none of them are empty
                || !addressParts[3].equals("Victoria")) { // Check if the state of address is "Victoria"
            return false;
        }

        // Check if birthdate is valid (format is DD-MM-YYYY)
        if (!isValidDate(birthdate)) {
            return false;
        }

        return true;
    }

    // Method to validate date format
    public static boolean isValidDate(String dateStr) {
        if (!dateStr.matches("\\d{2}-\\d{2}-\\d{4}")) { // Check if date format is DD-MM-YYYY
            return false;
        }

        // Check if date is valid using SimpleDateFormat
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Create a date format for validation
        dateFormat.setLenient(false); // Set lenient to false to strictly parse the date
        try {
            Date date = dateFormat.parse(dateStr); // Parse the date string
        } catch (ParseException e) { // Catch any parsing exceptions
            System.out.println("Invalid date format: " + e.getMessage());
            return false;
        }

        return true; // Date is valid
    }

    // Method to calculate age based on birthdate
    public static int getAge(String birthdate) {
        long ageInDays = 0; // Calculate age in days
        long ageInMilliseconds = 0; // Calculate age in milliseconds
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Create a date format for parsing the birthdate
            Date birthDate = simpleDateFormat.parse(birthdate); // Parse the birthdate string to a Date object
            Date currentDate = new Date(); // Get the current date

            ageInMilliseconds = currentDate.getTime() - birthDate.getTime(); // Calculate the difference in milliseconds between current date and birthdate
            ageInDays = ageInMilliseconds / (1000 * 60 * 60 * 24); // Convert milliseconds to days
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        return (int) ageInDays / 365; // Convert days to years and return as an integer
    }

    // Method to check if a person ID already exists in the file
    private static boolean isPersonIDExist(String personID, String filePath) {
        // Check if the file exists
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) { // Read each line from the file
                if (line.startsWith(personID + ",")) { // Compare personID in line with the given personID
                    return true; // Person ID exists in the file
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    // Method to check if two dates are within 2 years of each other
    public static boolean isWithin2Year(String firstDate, String secondDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Create a date formatter for parsing the dates
        LocalDate date1 = LocalDate.parse(firstDate, formatter); // Parse the first date string to a LocalDate object
        LocalDate date2 = LocalDate.parse(secondDate, formatter); // Parse the second date string to a LocalDate object

        // Calculate the absolute difference in years between the two dates
        long yearsBetween = Math.abs(ChronoUnit.YEARS.between(date1, date2));

        // Check if the difference is less than 2 years
        return yearsBetween < 2;
    }

    // Method to get the suspension status of a person based on their ID
    public static boolean getIsSuspended(String personID) throws IOException {
        File file = new File("addDemeritPoints.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        // Check if the file exists
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(personID + ",")) { // Compare personID in line with the given personID
                String[] parts = line.split(",");
                String suspendedPart = parts[parts.length - 1].trim(); // Get the last part of the line which indicates suspension status

                reader.close();

                return suspendedPart.equalsIgnoreCase("true"); // Return true if the person is suspended, false otherwise
            }
        }

        reader.close();
        throw new IllegalArgumentException("Person ID not found: " + personID);
    }

    // Main method for testing purposes (using for debugging)
//    public static void main(String[] args) throws IOException {
//        String personClassTest_testCase4_testData2 = Person.addDemeritPoints("123@@@ABCD",
//                new HashMap<String, Number>() {{
//                    put("01-01-2020", 2);
//                    put("01-04-2020", 2.0);
//                    put("01-05-2025", 2);
//                }});
//        boolean personClassTest_testCase1_testData2_suspended = Person.getIsSuspended("123@@@ABCD");
//        System.out.println("Test Case 2: " + personClassTest_testCase4_testData2);
//        System.out.println("Is Suspended: " + personClassTest_testCase1_testData2_suspended);
//    }
}

