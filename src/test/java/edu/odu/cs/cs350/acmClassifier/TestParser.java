package edu.odu.cs.cs350.acmClassifier;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class TestParser {
    





    /**
     * @throws IOException
     */
    @Test
    void testLoadRawText() throws IOException
    {
        DocumentReader d_reader = new DocumentReader();
        String filePath = "/Users/nataliemohun/";
        File f = new File(filePath+"penny.pdf");
        

        d_reader.importPDF(f.getAbsolutePath());
        String pennies = d_reader.getDocumentText();

        Document d = new Document();
        d.filePath = "/Users/nataliemohun/penny.pdf";
        
        Parser p = new Parser();
        p.loadRawText(d);

        assertEquals(d.rawText, pennies);
    }

    /**
     * 
     */
    @Test
    void testLoadToken()
    {
        Document d = new Document();
        d.rawText=",,Hi how are ...........you???";
       
        Parser p = new Parser();

        p.loadToken(d);

        final ArrayList<String> expected= new ArrayList<>();
        expected.add("hi");
        expected.add("how");
        expected.add("are");
        expected.add("you");



        assertEquals(d.tokenizedText,expected);
    }






    /**
     * @throws FileNotFoundException
     */
    @Test
    void testUpdateWordCount() throws FileNotFoundException
    {
        DocumentReader d_reader = new DocumentReader();
        String filePath = "/Users/nataliemohun/";
        File f = new File(filePath+"penny.pdf");


        

        d_reader.importPDF(f.getAbsolutePath());
        Document d = new Document();
        d.filePath = "/Users/nataliemohun/penny.pdf";
        
        Parser p = new Parser();
        p.loadRawText(d);
        p.loadToken(d);

        p.updateWordCounts(d);

        Integer pennyExpected = 2;

        assertEquals(d.wordFreqencyMap.get("penny"), pennyExpected);
    }

    @Test
    void testRawSignature() throws FileNotFoundException
    {
        DocumentReader d_reader = new DocumentReader();
        String filePath = "/Users/nataliemohun/";
        File f = new File(filePath+"penny.pdf");

        d_reader.importPDF(f.getAbsolutePath());
        Document d = new Document();
        d.filePath = "/Users/nataliemohun/penny.pdf";
        
        Parser p = new Parser();
        p.loadRawText(d);
        p.loadToken(d);

        p.updateWordCounts(d);

    for (Map.Entry<String, Integer> entry : d.wordFreqencyMap.entrySet())
    {
        System.out.println("Key: " + entry.getKey()+entry.getValue());

        
    }

        ArrayList<Integer> rawSig = p.getRawSignature(d);


        ArrayList<Integer> rawSigExpected = new ArrayList<Integer>();
        rawSigExpected.add(2);//a
        rawSigExpected.add(1);//earned
        rawSigExpected.add(1);//saved
        rawSigExpected.add(1);//is
        rawSigExpected.add(2);//penny

        assertEquals(d.wordFreqencyMap.get("a"),rawSig.get(0));
        assertEquals(d.wordFreqencyMap.get("earned"),rawSig.get(1));
        assertEquals(d.wordFreqencyMap.get("saved"),rawSig.get(2));
        assertEquals(d.wordFreqencyMap.get("is"), rawSig.get(3));
        assertEquals(d.wordFreqencyMap.get("penny"), rawSig.get(4));
        

        assertEquals(rawSig, rawSigExpected);




    }











    /*
     * expected normalized word counts
     */
    private Double[] normalized1 = {1.0, 1.0, 1.0, 1.0};
    private Double[] normalized2 = {0.0, 0.0, 0.0, 0.0};
    private Double[] normalized3 = {1.0, 1.0, 0.0, 0.0};

    /*
     * sample word counts
     */
    private Integer[] sampleWC1 = {4, 5, 7, 9};
    private Integer[] sampleWC2 = {3, 2, 1, 2};
    private Integer[] sampleWC3 = {6, 5, 1, 1};
    
    
    @Test
    public void testNormalize1(){

        Document test1 = new Document();

        test1.wordCounts = new ArrayList<Integer>(Arrays.asList(sampleWC1));

        Parser.normalize(test1);

        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(normalized1));

        assertEquals(expected, test1.normalizedWordCounts);

    }

    @Test
    public void testNormalize2(){
        Document test2 = new Document();

        test2.wordCounts = new ArrayList<Integer>(Arrays.asList(sampleWC2));

        Parser.normalize(test2);

        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(normalized2));

        assertEquals(expected, test2.normalizedWordCounts);
        
    }
    
    @Test
    public void testNormalize3(){
        Document test3 = new Document();

        test3.wordCounts = new ArrayList<Integer>(Arrays.asList(sampleWC3));

        Parser.normalize(test3);

        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(normalized3));

        assertEquals(expected, test3.normalizedWordCounts);
        
    }




}
