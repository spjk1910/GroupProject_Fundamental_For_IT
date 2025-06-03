// If any error occurs during testing, please delete addPerson.txt if it exists,
// as duplicate personIDs can cause issues.
// Additionally, replace the current updatePersonDetails.txt and addDemeritPoints.txt
// files with the versions provided in the Initial Data folder.

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestClassPerson {
    @Test
    void test_addPerson_testCase1() {
        // Test case 1: Test the function with valid inputs

        //Test Case 1 - Test Data 1
        Person personClassTest_testCase1_testData1 = new Person("56s_d%&fAB","John","Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990",null,false);
        //Test Case 1 - Test Data 2
        Person personClassTest_testCase1_testData2 = new Person("23a#b$cDXY","Alice","Wong",
                "100|Smith Street|Geelong|Victoria|Australia",
                "01-01-1985",null,false);
        //Test Case 1 - Test Data 3
        Person personClassTest_testCase1_testData3 = new Person("675x*&bKLM","Bob","Nguyen",
                "45|Sunset Street|Ballarat|Victoria|Australia",
                "29-02-2000",null,false);
        //Test Case 1 - Test Data 4
        Person personClassTest_testCase1_testData4 = new Person("89H#s%_gHJ", "Sarah","Lee",
                "12|King Street|Melbourne|Victoria|Australia",
                "20-05-1994",null,false);
        //Test Case 1 - Test Data 5
        Person personClassTest_testCase1_testData5 = new Person("34k!m}@rCD", "Steve","Chan",
        "56|Queen Street|Melbourne|Victoria|Australia",
                "31-12-1985",null,false);

        String filePath = "addPerson.txt";
        // Asserting that the addPerson method returns true for valid inputs
        assertTrue(personClassTest_testCase1_testData1.addPerson(filePath));
        assertTrue(personClassTest_testCase1_testData2.addPerson(filePath));
        assertTrue(personClassTest_testCase1_testData3.addPerson(filePath));
        assertTrue(personClassTest_testCase1_testData4.addPerson(filePath));
        assertTrue(personClassTest_testCase1_testData5.addPerson(filePath));
    }

    @Test
    void test_addPerson_testCase2() {
        // Test case 2: Test the function with invalid personID

        //Test Case 2 - Test Data 1: Not enough 10 characters
        Person personClassTest_testCase2_testData1 = new Person("56s_d%AB","John","Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990",null,false);
        //Test Case 2 - Test Data 2: First two characters are not numbers between 2-9
        Person personClassTest_testCase2_testData2 = new Person("10a#b$cDXY","Alice","Wong",
                "100|Smith Street|Geelong|Victoria|Australia",
                "01-01-1985",null,false);
        //Test Case 2 - Test Data 3: Missing special characters between 3rd and 8th character
        Person personClassTest_testCase2_testData3 = new Person("675nMKbKLM","Bob","Nguyen",
                "45|Sunset Street|Ballarat|Victoria|Australia",
                "29-02-2000",null,false);
        //Test Case 2 - Test Data 4: Only 1 special character between 3rd and 8th character
        Person personClassTest_testCase2_testData4 = new Person("89(4jd93HJ", "Sarah","Lee",
                "12|King Street|Melbourne|Victoria|Australia",
                "20-05-1994",null,false);
        //Test Case 2 - Test Data 5: Last two characters are not uppercase letters
        Person personClassTest_testCase2_testData5 = new Person("34k!m}@r5D", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria|Australia",
                "31-12-1985",null,false);

        String filePath = "addPerson.txt";
        // Asserting that the addPerson method returns false for invalid personID
        assertFalse(personClassTest_testCase2_testData1.addPerson(filePath));
        assertFalse(personClassTest_testCase2_testData2.addPerson(filePath));
        assertFalse(personClassTest_testCase2_testData3.addPerson(filePath));
        assertFalse(personClassTest_testCase2_testData4.addPerson(filePath));
        assertFalse(personClassTest_testCase2_testData5.addPerson(filePath));
    }

    @Test
    void test_addPerson_testCase3() {
        // Test case 3: Test the function with invalid address

        //Test Case 3 - Test Data 1: State is not Victoria
        Person personClassTest_testCase3_testData1 = new Person("56s_d%&fBB","John","Doe",
                "32|Highland Street|Melbourne|NSW|Australia",
                "15-11-1990",null,false);
        //Test Case 3 - Test Data 2: Not pipe-separated format (separated by '|')
        Person personClassTest_testCase3_testData2 = new Person("23a#b$cKXY","Alice","Wong",
                "100,Smith Street,Geelong,Victoria,Australia",
                "01-01-1985",null,false);
        //Test Case 3 - Test Data 3: Country field is empty
        Person personClassTest_testCase3_testData3 = new Person("675x?&bKLM","Bob","Nguyen",
                "45|Sunset Street|Ballarat|Victoria|",
                "29-02-2000",null,false);
        //Test Case 3 - Test Data 4: Missing pipe character between state and country fields
        Person personClassTest_testCase3_testData4 = new Person("89H#s%_gMJ", "Sarah","Lee",
                "12|King Street|Melbourne|Victoria Australia",
                "20-05-1994",null,false);
        //Test Case 3 - Test Data 5: Missing street field
        Person personClassTest_testCase3_testData5 = new Person("34k!m}@rSD", "Steve","Chan",
                "56|Melbourne|Victoria|Australia",
                "31-12-1985",null,false);

        String filePath = "addPerson.txt";
        // Asserting that the addPerson method returns false for invalid address inputs
        assertFalse(personClassTest_testCase3_testData1.addPerson(filePath));
        assertFalse(personClassTest_testCase3_testData2.addPerson(filePath));
        assertFalse(personClassTest_testCase3_testData3.addPerson(filePath));
        assertFalse(personClassTest_testCase3_testData4.addPerson(filePath));
        assertFalse(personClassTest_testCase3_testData5.addPerson(filePath));
    }

    @Test
    void test_addPerson_testCase4() {
        // Test case 4: Test the function with invalid birthdate format

        //Test Case 4 - Test Data 1: Wrong date format (not DD-MM-YYYY)
        Person personClassTest_testCase4_testData1 = new Person("56s_9%&fAB","John","Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "1990-11-15",null,false);
        //Test Case 4 - Test Data 2: Wrong date format (not separated by '-')
        Person personClassTest_testCase4_testData2 = new Person("23y?b$cDXY","Alice","Wong",
                "100|Smith Street|Geelong|Victoria|Australia",
                "01/01/1985",null,false);
        //Test Case 4 - Test Data 3: Invalid date (29th February on a non-leap year)
        Person personClassTest_testCase4_testData3 = new Person("665x@#bKLM","Bob","Nguyen",
                "45|Sunset Street|Ballarat|Victoria|Australia",
                "29-02-1995",null,false);
        //Test Case 4 - Test Data 4: Invalid day (32nd day of the month)
        Person personClassTest_testCase4_testData4 = new Person("99H#s<_gHJ", "Sarah","Lee",
                "12|King Street|Melbourne|Victoria|Australia",
                "32-01-1994",null,false);
        //Test Case 4 - Test Data 5: Invalid characters in date (199o instead of 1990)
        Person personClassTest_testCase4_testData5 = new Person("34L!m}>rCD", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria|Australia",
                "31-12-199o",null,false);

        String filePath = "addPerson.txt";
        // Asserting that the addPerson method returns false for invalid birthdate formats
        assertFalse(personClassTest_testCase4_testData1.addPerson(filePath));
        assertFalse(personClassTest_testCase4_testData2.addPerson(filePath));
        assertFalse(personClassTest_testCase4_testData3.addPerson(filePath));
        assertFalse(personClassTest_testCase4_testData4.addPerson(filePath));
        assertFalse(personClassTest_testCase4_testData5.addPerson(filePath));
    }

    @Test
    void test_addPerson_testCase5() {
        // Test case 5: Test the function with multiple invalid inputs

        //Test Case 5 - Test Data 1: Invalid personID (not exact 10 characters), address (state is not Victoria),
        // and birthdate (not following format)
        Person personClassTest_testCase5_testData1 = new Person("56_%^%tf&fAB","John","Doe",
                "32|Highland Street|Melbourne|NSW|Australia",
                "1990-11-15",null,false);
        //Test Case 5 - Test Data 2: Invalid personID (not enough 2 special characters between index 3 and 8),
        // address (not separated by '|')
        Person personClassTest_testCase5_testData2 = new Person("23a[b6cDXY","Alice","Wong",
                "100/Smith Street/Geelong/Victoria/Australia",
                "01-01-1985",null,false);
        //Test Case 5 - Test Data 3: Invalid personID (missing special characters), address (country field is empty)
        Person personClassTest_testCase5_testData3 = new Person("675o89bKLM","Bob","Nguyen",
                "45|Sunset Street|Ballarat|Victoria|",
                "29-02-2000",null,false);
        //Test Case 5 - Test Data 4: Invalid address (missing street number field), birthdate (invalid date - month 13)
        Person personClassTest_testCase5_testData4 = new Person("88P#s%_gHJ", "Sarah","Lee",
                "King Street|Melbourne|Victoria|Australia",
                "20-13-1994",null,false);
        //Test Case 5 - Test Data 5: Invalid personID (first character not a number between 2-9)
        // address (missing pipe character between state and country), birthdate incorrect format (not DD-MM-YYYY)
        Person personClassTest_testCase5_testData5 = new Person("04k!m}@rCD", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria Australia",
                "12-31-1985",null,false);

        String filePath = "addPerson.txt";
        // Asserting that the addPerson method returns false for multiple invalid inputs
        assertFalse(personClassTest_testCase5_testData1.addPerson(filePath));
        assertFalse(personClassTest_testCase5_testData2.addPerson(filePath));
        assertFalse(personClassTest_testCase5_testData3.addPerson(filePath));
        assertFalse(personClassTest_testCase5_testData4.addPerson(filePath));
        assertFalse(personClassTest_testCase5_testData5.addPerson(filePath));
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * This is initial data for the test case of the updatePersonDetails method
     * Using this to verify the result (Only for Test Case 1 when input is valid)
     * 56s_d%&fAB,Junkynet,Kangtark,32|Highland Street|Melbourne|Victoria|Australia,28-05-1990  (Use for test data 1)
     * 33a#b$cDXY,Alice,Wong,100|Smith Street|Geelong|Victoria|Australia,01-01-2020             (Use for test data 2)
     * 675x*&bKLM,Keyla,Nguyen,45|Sunset Street|Ballarat|Victoria|Australia,29-02-2000          (Use for test data 3)
     * 89H#s%_gHJ,Sarah,Lee,12|King Street|Melbourne|Victoria|Australia,20-05-1994              (Use for test data 4)
     * 34k!m}@rCD,Steve,Chan,56|Queen Street|Melbourne|Victoria|Australia,31-12-1985            (Use for test data 5)
     **/
    @Test
    void test_updatePersonDetails_testCase1() throws IOException {
        // Test case 1: Test the function with valid inputs

        //Test Case 1 - Test Data 1: Updating birthdate and other details remaining the same
        boolean personClassTest_testCase1_testData1 = Person.updatePersonDetails("56s_d%&fAB",
                "56s_d%&fAB","Junkynet","Kangtark",
                "32|Highland Street|Melbourne|Victoria|Australia", "19-11-1994");
        //Test Case 1 - Test Data 2: Person is under 18 years old, don't change address (also don't change birthdate)
        boolean personClassTest_testCase1_testData2 = Person.updatePersonDetails("33a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "100|Smith Street|Geelong|Victoria|Australia", "01-01-2020");
        //Test Case 1 - Test Data 3: First character of personID is an even,change everything except personID, birthdate
        boolean personClassTest_testCase1_testData3 = Person.updatePersonDetails("675x*&bKLM",
                "675x*&bKLM","Carl","Black",
                "23|Titch Street|Footscray|Victoria|Australia", "29-02-2000");
        //Test Case 1 - Test Data 4: Change only first name and last name
        boolean personClassTest_testCase1_testData4 = Person.updatePersonDetails("89H#s%_gHJ",
                "89H#s%_gHJ", "Josh","Kim",
                "12|King Street|Melbourne|Victoria|Australia","20-05-1994");
        //Test Case 1 - Test Data 5: First character of personID is an odd number, change personID
        boolean personClassTest_testCase1_testData5 = Person.updatePersonDetails("34k!m}@rCD",
                "55R%^!kmGH", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria|Australia", "31-12-1985");

        // Asserting that the updatePersonDetails method returns true for valid inputs
        assertTrue(personClassTest_testCase1_testData1);
        assertTrue(personClassTest_testCase1_testData2);
        assertTrue(personClassTest_testCase1_testData3);
        assertTrue(personClassTest_testCase1_testData4);
        assertTrue(personClassTest_testCase1_testData5);
    }

    /**
     * This is a data after updating for the test case of the updatePersonDetails method
     * Using this to verify the result (For Test Case 2,3,4,5 when input is invalid)
     * 56s_d%&fAB,Junkynet,Kangtark,32|Highland Street|Melbourne|Victoria|Australia,19-11-1994  (Person 1)
     * 23a#b$cDXY,James,Green,100|Smith Street|Geelong|Victoria|Australia,01-01-2020            (Person 2)
     * 675x*&bKLM,Carl,Black,23|Titch Street|Footscray|Victoria|Australia,29-02-2000            (Person 3)
     * 89H#s%_gHJ,Josh,Kim,12|King Street|Melbourne|Victoria|Australia,20-05-1994               (Person 4)
     * 55R%^!kmGH,Steve,Chan,56|Queen Street|Melbourne|Victoria|Australia,31-12-1985            (Person 5)
     **/

    @Test
    void test_updatePersonDetails_testCase2() throws IOException {
        // Test case 2: Test the function with birthdate changed and other fields also changed

        //Test Case 2 - Test Data 1: Updating birthdate and personID (first character is an odd number) - Person 1
        boolean personClassTest_testCase2_testData1 = Person.updatePersonDetails("56s_d%&fAB",
                "22AB%^mjKL","Junkynet","Kangtark",
                "32|Highland Street|Melbourne|Victoria|Australia", "19-10-1994");
        //Test Case 2 - Test Data 2: Updating birthdate and first name - Person 2
        boolean personClassTest_testCase2_testData2 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","Richard","Green",
                "100|Smith Street|Geelong|Victoria|Australia", "01-03-2020");
        //Test Case 2 - Test Data 3: Updating birthdate and address - Person 3
        boolean personClassTest_testCase2_testData3 = Person.updatePersonDetails("675x*&bKLM",
                "675x*&bKLM","Carl","Black",
                "5|Burnewang Street|Albion|Victoria|Australia", "19-02-2002");
        //Test Case 2 - Test Data 4: Updating birthdate last name - Person 4
        boolean personClassTest_testCase2_testData4 = Person.updatePersonDetails("89H#s%_gHJ",
                "89H#s%_gHJ", "Josh","Wilson",
                "12|King Street|Melbourne|Victoria|Australia","20-08-1994");
        //Test Case 2 - Test Data 5: Updating all fields (1st character is of personID is odd) - Person 5
        boolean personClassTest_testCase2_testData5 = Person.updatePersonDetails("55R%^!kmGH",
                "48R%^!kmGH", "Jeniver","Keorind",
                "7|King Street|Melbourne|Victoria|Australia", "25-11-1985");

        // Asserting that the updatePersonDetails method returns false for invalid inputs
        assertFalse(personClassTest_testCase2_testData1);
        assertFalse(personClassTest_testCase2_testData2);
        assertFalse(personClassTest_testCase2_testData3);
        assertFalse(personClassTest_testCase2_testData4);
        assertFalse(personClassTest_testCase2_testData5);
    }

    @Test
    void test_updatePersonDetails_testCase3() throws IOException {
        // Test case 3: Test the function with personID starts with an even number is being updated

        //Test Case 3 - Test Data 1: Person 2: Change 1st of personID to odd number
        boolean personClassTest_testCase3_testData1 = Person.updatePersonDetails("23a#b$cDXY",
                "321#$MNDHJJ","James","Green",
                "100|Smith Street|Geelong|Victoria|Australia","01-01-2020");
        //Test Case 3 - Test Data 2: Person 3: Change 1st of personID to odd number
        boolean personClassTest_testCase3_testData2 = Person.updatePersonDetails("675x*&bKLM",
                "77R%$!ndGH","Carl","Black",
                "23|Titch Street|Footscray|Victoria|Australia","29-02-2000");
        //Test Case 3 - Test Data 3: Person 4: Change 1st of personID to odd number
        boolean personClassTest_testCase3_testData3 = Person.updatePersonDetails("89H#s%_gHJ",
                "36~`tnndJJ","Josh","Kim",
                "12|King Street|Melbourne|Victoria|Australia","20-05-1994");
        //Test Case 3 - Test Data 4: Person 2 - Change personID (1st is still even number)
        boolean personClassTest_testCase3_testData4 = Person.updatePersonDetails("23a#b$cDXY",
                "22A#$MNDHJJ","James","Green",
                "100|Smith Street|Geelong|Victoria|Australia","01-01-2020");
        //Test Case 3 - Test Data 5: Person 4 - Change personID (1st is still even number)
        boolean personClassTest_testCase3_testData5 = Person.updatePersonDetails("89H#s%_gHJ",
                "44~`tnndJJ","Josh","Kim",
                "12|King Street|Melbourne|Victoria|Australia","20-05-1994");

        // Asserting that the updatePersonDetails method returns false for invalid inputs
        assertFalse(personClassTest_testCase3_testData1);
        assertFalse(personClassTest_testCase3_testData2);
        assertFalse(personClassTest_testCase3_testData3);
        assertFalse(personClassTest_testCase3_testData4);
        assertFalse(personClassTest_testCase3_testData5);
    }

    @Test
    void test_updatePersonDetails_testCase4() throws IOException {
        // Test case 4: Test the function when the person under 18 years old and address is changed (Person 2)

        //Test Case 4 - Test Data 1: Updating street number
        boolean personClassTest_testCase4_testData1 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "111|Smith Street|Geelong|Victoria|Australia","01-01-2020");
        //Test Case 4 - Test Data 2: Updating street name
        boolean personClassTest_testCase4_testData2 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "100|Highway Street|Geelong|Victoria|Australia","01-01-2020");
        //Test Case 4 - Test Data 3: Updating City
        boolean personClassTest_testCase4_testData3 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "100|Smith Street|Footscray|Victoria|Australia","01-01-2020");
        //Test Case 4 - Test Data 4: Updating country
        boolean personClassTest_testCase4_testData4 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "100|Smith Street|Geelong|Victoria|New Zealand","01-01-2020");
        //Test Case 4 - Test Data 5: Updating all fields of address (exclude state)
        boolean personClassTest_testCase4_testData5 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$cDXY","James","Green",
                "745|King Street|Albion|Victoria|London","01-01-2020");

        // Asserting that the updatePersonDetails method returns false for invalid inputs
        assertFalse(personClassTest_testCase4_testData1);
        assertFalse(personClassTest_testCase4_testData2);
        assertFalse(personClassTest_testCase4_testData3);
        assertFalse(personClassTest_testCase4_testData4);
        assertFalse(personClassTest_testCase4_testData5);
    }

    @Test
    void test_updatePersonDetails_testCase5() throws IOException {
        // Test case 5: Test the function invalid inputs (not following the condition in addPerson function)

        //Test Case 5 - Test Data 1: Updating birthdate and other details remaining the same (birthdate is not valid)
        //Person 1
        boolean personClassTest_testCase5_testData1 = Person.updatePersonDetails("56s_d%&fAB",
                "56s_d%&fAB","Junkynet","Kangtark",
                "32|Highland Street|Melbourne|Victoria|Australia", "19/11/1994");
        //Test Case 5 - Test Data 2: Person is under 18 years old, don't change address (also don't change birthdate)
        //Change personID to invalid one/not exactly 10 character (1st character is odd number) - Person 2
        boolean personClassTest_testCase5_testData2 = Person.updatePersonDetails("23a#b$cDXY",
                "23a#b$c55DXY","James","Green",
                "100|Smith Street|Geelong|Victoria|Australia", "01-01-2020");
        //Test Case 5 - Test Data 3: First character of personID is an even,change everything except personID, birthdate
        //State of address is not Victoria - Person 3
        boolean personClassTest_testCase5_testData3 = Person.updatePersonDetails("675x*&bKLM",
                "675x*&bKLM","Carl","Black",
                "23|Titch Street|Footscray|NSW|Australia", "29-02-2000");
        //Test Case 5 - Test Data 4: Change address but misisng street number field - Person 4
        boolean personClassTest_testCase5_testData4 = Person.updatePersonDetails("89H#s%_gHJ",
                "89H#s%_gHJ", "Josh","Kim",
                "King Street|Melbourne|Victoria|Australia","20-05-1994");
        //Test Case 5 - Test Data 5: First character of personID is an odd number, change personID
        // Only have 1 special character between index 3 and 8 - Person 5
        boolean personClassTest_testCase5_testData5 = Person.updatePersonDetails("55R%^!kmGH",
                "55R%JJkmGH", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria|Australia", "31-12-1985");

        // Asserting that the updatePersonDetails method returns false for invalid inputs
        assertFalse(personClassTest_testCase5_testData1);
        assertFalse(personClassTest_testCase5_testData2);
        assertFalse(personClassTest_testCase5_testData3);
        assertFalse(personClassTest_testCase5_testData4);
        assertFalse(personClassTest_testCase5_testData5);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * This is initial data for the test case of the addDemeritPoints method
     * Using this to verify the result
     * 9318~~TNFV,Junkynet,Kangtark,5|Titch St|Melbourne|Victoria|Australia,19-11-1994         (Person 1- Over 21 years old)
     * 2627@@@TNC,Richard,Keorind,4|Burnewang Street|Melbourne|Victoria|Australia,19-10-2005   (Person 2- Under 21 years old)
     * 3205~~HPNY,Sun,Harrington,124|La Trobe St|Melbourne|Victoria|Australia,25-03-1989       (Person 3- Over 21 years old)
     * 44!!@@HAND,Keyla,Nelson,113|Little Bourke St|Melbourne|Victoria|Australia,16-03-1998    (Person 4 - Over 21 years old)
     * 99%100%SSS,Elena,Wilson,27|Swanston St|Melbourne|Victoria|Australia,28-01-2006          (Person 5 - Under 21 years old)
     * 123@@@ABCD,Jack,Black,45|Collins St|Melbourne|Victoria|Australia,15-05-2004             (Person 6 - Under 21 years old)
     **/
    @Test
    void test_addDemeritPoints_testCase1() throws IOException {
        // Test case 1: Check the function with person over 21 years old and valid inputs (demerit points > 12)

        //Test Case 1 - Test Data 1: Person 1
        String personClassTest_testCase1_testData1 = Person.addDemeritPoints("9318~~TNFV",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 5);
                    put("01-04-2024", 5);
                    put("01-05-2025", 3);
                }});
        boolean personClassTest_testCase1_testData1_suspended = Person.getIsSuspended("9318~~TNFV");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Success",personClassTest_testCase1_testData1);
        assertTrue(personClassTest_testCase1_testData1_suspended);
    }

    @Test
    void test_addDemeritPoints_testCase2() throws IOException {
        // Test case 2: Check the function with person over 21 years old and valid inputs (demerit points <= 12)

        //Test Case 2 - Test Data 1: Person 3
        String personClassTest_testCase2_testData1 = Person.addDemeritPoints("3205~~HPNY",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 5);
                    put("01-04-2024", 5);
                    put("01-05-2025", 1);
                }});
        boolean personClassTest_testCase2_testData1_suspended = Person.getIsSuspended("3205~~HPNY");
        //Test Case 2 - Test Data 2: Person 4
        String personClassTest_testCase2_testData2 = Person.addDemeritPoints("44!!@@HAND",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 5);
                    put("01-04-2020", 5);
                    put("01-05-2025", 2);
                }});
        boolean personClassTest_testCase2_testData2_suspended = Person.getIsSuspended("44!!@@HAND");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Success",personClassTest_testCase2_testData1);
        assertFalse(personClassTest_testCase2_testData1_suspended);

        assertEquals("Success",personClassTest_testCase2_testData2);
        assertFalse(personClassTest_testCase2_testData2_suspended);
    }

    @Test
    void test_addDemeritPoints_testCase3() throws IOException {
        // Test case 3: Check the function with person under 21 years old and valid inputs (demerit points > 6)

        //Test Case 3 - Test Data 1: Person 2
        String personClassTest_testCase3_testData1 = Person.addDemeritPoints("2627@@@TNC",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 2);
                    put("01-04-2024", 2);
                    put("01-05-2025", 5);
                }});
        boolean personClassTest_testCase3_testData1_suspended = Person.getIsSuspended("2627@@@TNC");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Success",personClassTest_testCase3_testData1);
        assertTrue(personClassTest_testCase3_testData1_suspended);
    }

    @Test
    void test_addDemeritPoints_testCase4() throws IOException {
        // Test case 4: Check the function with person under 21 years old and valid inputs (demerit points < 6)

        //Test Case 4 - Test Data 1: Person 5
        String personClassTest_testCase4_testData1 = Person.addDemeritPoints("99%100%SSS",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 1);
                    put("01-04-2024", 1);
                    put("01-05-2025", 1);
                }});
        boolean personClassTest_testCase4_testData1_suspended = Person.getIsSuspended("99%100%SSS");
        //Test Case 4 - Test Data 2: Person 6 (also include point is a whole number but in float format)
        String personClassTest_testCase4_testData2 = Person.addDemeritPoints("123@@@ABCD",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 2);
                    put("01-04-2020", 2.0);
                    put("01-05-2025", 2);
                }});
        boolean personClassTest_testCase4_testData2_suspended = Person.getIsSuspended("123@@@ABCD");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Success",personClassTest_testCase4_testData1);
        assertFalse(personClassTest_testCase4_testData1_suspended);

        assertEquals("Success",personClassTest_testCase4_testData2);
        assertFalse(personClassTest_testCase4_testData2_suspended);
    }

    @Test
    void test_addDemeritPoints_testCase5() throws IOException {
        // Test case 5: Check the function with invalid date format

        //Test Case 5 - Test Data 1: Person 1
        String personClassTest_testCase5_testData1 = Person.addDemeritPoints("9318~~TNFV",
                new HashMap<String, Number>() {{
                    put("01/01/2024", 5);
                    put("01-04-2024", 5);
                    put("01-05-2025", 3);
                }});
        boolean personClassTest_testCase5_testData1_suspended = Person.getIsSuspended("9318~~TNFV");
        //Test Case 5 - Test Data 2: Person 3
        String personClassTest_testCase5_testData2 = Person.addDemeritPoints("3205~~HPNY",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 5);
                    put("01-04-2020", 5);
                    put("01/05/2025", 3);
                }});
        boolean personClassTest_testCase5_testData2_suspended = Person.getIsSuspended("3205~~HPNY");
        //Test Case 5 - Test Data 3: Person 4
        String personClassTest_testCase5_testData3 = Person.addDemeritPoints("44!!@@HAND",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 4);
                    put("01-04-2024", 4);
                    put("12-23-2025", 3);
                }});
        boolean personClassTest_testCase5_testData3_suspended = Person.getIsSuspended("44!!@@HAND");
        //Test Case 5 - Test Data 4: Person 2
        String personClassTest_testCase5_testData4 = Person.addDemeritPoints("2627@@@TNC",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 5);
                    put("01-04-2024", 5);
                    put("2025-05-01", 3);
                }});
        boolean personClassTest_testCase5_testData4_suspended = Person.getIsSuspended("2627@@@TNC");
        //Test Case 5 - Test Data 5: Person 5
        String personClassTest_testCase5_testData5 = Person.addDemeritPoints("99%100%SSS",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 1);
                    put("01,04,2020", 4);
                    put("01-05-2025", 3);
                }});
        boolean personClassTest_testCase5_testData5_suspended = Person.getIsSuspended("99%100%SSS");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Failed",personClassTest_testCase5_testData1);
        assertTrue(personClassTest_testCase5_testData1_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase5_testData2);
        assertFalse(personClassTest_testCase5_testData2_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase5_testData3);
        assertFalse(personClassTest_testCase5_testData3_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase5_testData4);
        assertTrue(personClassTest_testCase5_testData4_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase5_testData5);
        assertFalse(personClassTest_testCase5_testData5_suspended); // Keep old suspended status
    }

    @Test
    void test_addDemeritPoints_testCase6() throws IOException {
        // Test case 6: Check the function with invalid points (not a whole number between 1 and 6)

        //Test Case 6 - Test Data 1: Not a whole number but in range between 1 and 6 - Person 1
        String personClassTest_testCase6_testData1 = Person.addDemeritPoints("9318~~TNFV",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 5.5);
                    put("01-04-2024", 5);
                    put("01-05-2025", 3);
                }});
        boolean personClassTest_testCase6_testData1_suspended = Person.getIsSuspended("9318~~TNFV");
        //Test Case 6 - Test Data 2: A whole number but not in between 1 and 6 - Person 3
        String personClassTest_testCase6_testData2 = Person.addDemeritPoints("3205~~HPNY",
                new HashMap<String, Number>() {{
                    put("01-01-2020", 5);
                    put("01-04-2020", 5);
                    put("01-05-2025", 0);
                }});
        boolean personClassTest_testCase6_testData2_suspended = Person.getIsSuspended("3205~~HPNY");
        //Test Case 6 - Test Data 3: Not a whole number and not in range between 1 and 6 - Person 4
        String personClassTest_testCase6_testData3 = Person.addDemeritPoints("44!!@@HAND",
                new HashMap<String, Number>() {{
                    put("01-01-2024", 4);
                    put("01-04-2024", 8.7);
                    put("12-23-2025", 3);
                }});
        boolean personClassTest_testCase6_testData3_suspended = Person.getIsSuspended("44!!@@HAND");

        // Asserting that the addDemeritPoints method returns the expected results
        assertEquals("Failed",personClassTest_testCase6_testData1);
        assertTrue(personClassTest_testCase6_testData1_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase6_testData2);
        assertFalse(personClassTest_testCase6_testData2_suspended); // Keep old suspended status

        assertEquals("Failed",personClassTest_testCase6_testData3);
        assertFalse(personClassTest_testCase6_testData3_suspended); // Keep old suspended status
    }
}
