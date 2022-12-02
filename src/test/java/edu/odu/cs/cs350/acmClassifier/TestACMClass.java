package edu.odu.cs.cs350.acmClassifier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestACMClass {
    @Test
    void testACMClass() {
        ACMClass ac = new ACMClass();
        assertEquals(ac.size(), 0);

        String classStr = "ABCDEFGHIJK";
        ACMClass ac2 = new ACMClass(classStr);
        assertEquals(ac2.size(), classStr.length());
        assertEquals(ac2.getLetter(3), "D");
        assertEquals(ac2.get(4), ACMClass.Classification.DATA);
    }
}