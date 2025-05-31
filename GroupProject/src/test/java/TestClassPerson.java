import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        boolean personClassTest_testCase5_testData2 = Person.updatePersonDetails("33a#b$cDXY",
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
        boolean personClassTest_testCase5_testData5 = Person.updatePersonDetails("34k!m}@rCD",
                "55R%JJkmGH", "Steve","Chan",
                "56|Queen Street|Melbourne|Victoria|Australia", "31-12-1985");

        // Asserting that the updatePersonDetails method returns false for invalid inputs
        assertFalse(personClassTest_testCase5_testData1);
        assertFalse(personClassTest_testCase5_testData2);
        assertFalse(personClassTest_testCase5_testData3);
        assertFalse(personClassTest_testCase5_testData4);
        assertFalse(personClassTest_testCase5_testData5);
    }
}
