import org.junit.jupiter.api.Test;
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

        // Asserting that the addPerson method returns true for valid inputs
        assertTrue(personClassTest_testCase1_testData1.addPerson());
        assertTrue(personClassTest_testCase1_testData2.addPerson());
        assertTrue(personClassTest_testCase1_testData3.addPerson());
        assertTrue(personClassTest_testCase1_testData4.addPerson());
        assertTrue(personClassTest_testCase1_testData5.addPerson());
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

        // Asserting that the addPerson method returns false for invalid personID
        assertFalse(personClassTest_testCase2_testData1.addPerson());
        assertFalse(personClassTest_testCase2_testData2.addPerson());
        assertFalse(personClassTest_testCase2_testData3.addPerson());
        assertFalse(personClassTest_testCase2_testData4.addPerson());
        assertFalse(personClassTest_testCase2_testData5.addPerson());
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

        // Asserting that the addPerson method returns false for invalid address inputs
        assertFalse(personClassTest_testCase3_testData1.addPerson());
        assertFalse(personClassTest_testCase3_testData2.addPerson());
        assertFalse(personClassTest_testCase3_testData3.addPerson());
        assertFalse(personClassTest_testCase3_testData4.addPerson());
        assertFalse(personClassTest_testCase3_testData5.addPerson());
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

        // Asserting that the addPerson method returns false for invalid birthdate formats
        assertFalse(personClassTest_testCase4_testData1.addPerson());
        assertFalse(personClassTest_testCase4_testData2.addPerson());
        assertFalse(personClassTest_testCase4_testData3.addPerson());
        assertFalse(personClassTest_testCase4_testData4.addPerson());
        assertFalse(personClassTest_testCase4_testData5.addPerson());
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

        // Asserting that the addPerson method returns false for multiple invalid inputs
        assertFalse(personClassTest_testCase5_testData1.addPerson());
        assertFalse(personClassTest_testCase5_testData2.addPerson());
        assertFalse(personClassTest_testCase5_testData3.addPerson());
        assertFalse(personClassTest_testCase5_testData4.addPerson());
        assertFalse(personClassTest_testCase5_testData5.addPerson());
    }


}
