package edu.odu.cs.cs350.acmClassifier;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



 import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;


public class TestStopWordList{
    @Test
    public void testContains() throws IOException {
        File file = new File("src/main/resources/stop_words.txt");
        StopWordList words = new StopWordList(file);
        assertEquals(words.size(), 6);
        assertTrue(words.contains("The"));
        assertTrue(words.contains("World"));
        assertTrue(words.contains("Is"));
        assertTrue(words.contains("Nothing"));
        assertTrue(words.contains("but"));
        assertTrue(words.contains("builds generosity"));
    }
}

