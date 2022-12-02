package edu.odu.cs.cs350.acmClassifier;
//import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {

    

    /**
     * 
     **/
    public void Parse(){
        //using apache tika read in file and output text
    }

    
    
    public void loadRawText(Document d)
    {
        DocumentReader d_reader = new DocumentReader();
        try {
            d_reader.importPDF(d.filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        d.rawText=d_reader.getDocumentText();
    }



    /**
     * @param d a document
     */
    public void loadToken(Document d)
    {
        //ensure raw text has been loaded if null
        if(d.rawText ==null)
            this.loadRawText(d);

        String words = d.rawText.replaceAll("[^a-zA-Z ]", "").toLowerCase();
        

        
        //Split raw text 

        d.tokenizedText= new ArrayList<String>(Arrays.asList(words.split(" ")));

        
    }


    public void updateWordCounts(Document d)
    {
        //ensure load token has been called
        d.wordFreqencyMap = new HashMap<String,Integer>();

        for (String i : d.tokenizedText) {
            Integer j = d.wordFreqencyMap.get(i);
            if (j==null)
            {
                d.wordFreqencyMap.put(i,1);
            }
            else
            {
                d.wordFreqencyMap.put(i, d.wordFreqencyMap.get(i)+1);
            }
        }
       
        

    }
    /**
     * @param d d, a document
     */
    public void loadRawSignature(Document d)
    {
        //make sure update word counts has been called 
        if (d.wordFreqencyMap==null)
            this.updateWordCounts(d);
        //iterate over each key and load value into the word count list
        for (HashMap.Entry<String,Integer> word : d.wordFreqencyMap.entrySet())
        {
            d.wordCounts.add(word.getValue());
        }
    }

   

    /**
     * @param d  a document
     * @return the document'snraw signature cotaining each word count
     */
    public ArrayList<Integer> getRawSignature(Document d)
    {
        //call all of document's functions to set up raw signature list, wordcounts
        this.loadRawSignature(d);
        return d.wordCounts;
    }


    public static void normalize(Document d){
        for (int i = 0; i < d.wordCounts.size(); i++){
            if (d.wordCounts.get(i) >= 4){
                d.normalizedWordCounts.add(1.0);
            }   
            else{
                d.normalizedWordCounts.add(0.0);
            }
        }
    }

    
    
}
