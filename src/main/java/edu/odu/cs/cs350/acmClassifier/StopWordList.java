
package edu.odu.cs.cs350.acmClassifier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StopWordList implements Cloneable {
    private List<String> stopWords = new ArrayList<String>();

    public StopWordList(File file) throws FileNotFoundException, IOException {
        Scanner s = new Scanner(file, "UTF-8");
         while (s.hasNextLine()) {
            stopWords.add(s.nextLine());

        }
        s.close();
    }

    public Object clone() {
        StopWordList sl = null;
        try {
            sl = (StopWordList) super.clone();
            sl.stopWords = new ArrayList<String>(this.stopWords);
            return sl;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean contains(String word) {
        for (int i = 0; i < this.stopWords.size(); i++) {
            if (this.stopWords.get(i).equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.stopWords.size();
    }
}

