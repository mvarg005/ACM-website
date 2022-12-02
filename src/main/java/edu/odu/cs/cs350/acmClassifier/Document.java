package edu.odu.cs.cs350.acmClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.codelibs.jhighlight.fastutil.Hash;
/**
 * A generic document, containing all the information Parser and Trainer need.
 */
public class Document implements Cloneable {
    /**
     * The file path String of the Document
     */
    public String filePath;

    /**
     * A Hashmap of each word and its frequency in the document
     */
    public HashMap<String,Integer> wordFreqencyMap;

    /**
     * 
     * The raw text of the document.
     */
    public String rawText;

    /**
     * The tokenized, unpruned text of the document.
     */
    ArrayList<String> tokenizedText;

    /**
     * An ArrayList of how many times each word in the document appears.
     *  
     */
    public ArrayList<Integer> wordCounts;


    

    /**
     * An array of normalized word counts.
     * Normalization has two steps:
     * 1. If word count is greater than 4, set normalized word count to 1.
     * Otherwise, set to 0.
     * 2. Multiply normalized word count by the inverse document frequency.
     */
    public ArrayList<Double> normalizedWordCounts;

    /**
     * The classification of the document.
     */
    public ACMClass classification;

    /**
     * Construct an empty, unclassified Document.
     */
    public Document() {
        rawText = new String("");
        filePath = new String("");
        filePath= new String("");
        tokenizedText = new ArrayList<String>();
        wordCounts=new ArrayList<Integer>();
        normalizedWordCounts = new ArrayList<Double>();
        classification = new ACMClass();
    }

    /**
     * Copy constructor.
     * 
     * @param doc the document to copy from
     */
    public Document(Document doc) {
        // TODO
    }

    @Override
    public Document clone() {
        return new Document(this);
    }
    
    public void setDocPath(String path)
    {
        //if path is valid, pass into this.filepath
        File f = new File(path);
        if (f.exists()==true)
        {
            this.filePath= path;
        }
        else{
            println("invalid path");
        }
    }


    private void println(String string) {
    }

    @Override
    public String toString() {
        // TODO
        return "I am a Document";
    }
}
