package edu.odu.cs.cs350.acmClassifier;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TestDocument {
    @Test
    void testDocument() {
        Document d = new Document();
        assertEquals(d.classification.size(), 0);
        assertEquals(d.rawText.length(), 0);
        assertEquals(d.wordCounts.size(), 0);
        assertEquals(d.normalizedWordCounts.size(), 0);
        assertEquals(d.tokenizedText.size(), 0);
    }

    @Test 
    void testLoadFilePath() throws IOException
    {
        Document d = new Document();
        final File tempF = File.createTempFile("tempPDF", ".pdf");
        String tempPath = tempF.getAbsolutePath();

        assertEquals(tempF.getAbsolutePath(), tempPath);
        assertEquals(tempF.exists(),true);
    

        d.setDocPath(tempPath);

        assertEquals(d.filePath,tempPath);

        tempF.deleteOnExit();
    }
}