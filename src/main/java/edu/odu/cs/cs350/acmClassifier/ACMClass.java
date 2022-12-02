package edu.odu.cs.cs350.acmClassifier;

import java.util.ArrayList;

/** 
 * A collection of ACM classifications for a single document.
 */
public class ACMClass implements Cloneable {
    /**
     * An enum of the various ACM classifications.
     */
    enum Classification {
        GEN_LIT,                    //A. General Literature
        HARDWARE,                   //B. Hardware
        COMP_SYS_ORG,               //C. Computer systems organization
        SOFTWARE,                   //D. Software
        DATA,                       //E. Data
        THEORY_OF_COMP,             //F. Theory of computation
        MATH_OF_COMP,               //G. Mathematics of computing
        INFO_SYS,                   //H. Information systems
        COMP_AND_METHODOLOGIES,     //I. Computing and methodologies
        COMP_APPLICATIONS,          //J. Computer Applications
        COMP_MILIEUX                //K. Computing Milieux
    }

    /** 
     * The collection of classifications.
     * If the size of this is 0, then "(no classes)" should be displayed when toString is called.
     */
    private ArrayList<Classification> classifications;

    /** 
     * Construct an ACMClass with no classifications.
     */
    public ACMClass() {
        classifications = new ArrayList<Classification>();
    }

    /**
     * Copy constructor.
     * 
     * @param acm the ACMClass to copy from
     */
    public ACMClass(ACMClass acm) {
        //TODO
    }
    
    @Override
    public ACMClass clone()
    {
    	return new ACMClass(this);
    }

    /**
     * Construct an ACMClass with classifications according to the given string.
     * 
     * @param str list of classifications, each represented by a letter A-K
     */
    public ACMClass(String str) {
        classifications = new ArrayList<Classification>();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 'A':
                    classifications.add(Classification.GEN_LIT);
                    break;
                case 'B':
                    classifications.add(Classification.HARDWARE);
                    break;
                case 'C':
                    classifications.add(Classification.COMP_SYS_ORG);
                    break;
                case 'D':
                    classifications.add(Classification.SOFTWARE);
                    break;
                case 'E':
                    classifications.add(Classification.DATA);
                    break;
                case 'F':
                    classifications.add(Classification.THEORY_OF_COMP);
                    break;
                case 'G':
                    classifications.add(Classification.MATH_OF_COMP);
                    break;
                case 'H':
                    classifications.add(Classification.INFO_SYS);
                    break;
                case 'I':
                    classifications.add(Classification.COMP_AND_METHODOLOGIES);
                    break;
                case 'J':
                    classifications.add(Classification.COMP_APPLICATIONS);
                    break;
                case 'K':
                    classifications.add(Classification.COMP_MILIEUX);
                    break;
            }
        }
    }

    /**
     * Get a single classification.
     * 
     * @param index the index of the classification
     * @return a classification
     */
    public Classification get(int index) {
        return classifications.get(index);
    }

    /**
     * Get a single classification, represented as a letter A-L.
     * 
     * @param index the index of the classification
     * @return a classification letter
     */
    public String getLetter(int index) {
        Classification c = classifications.get(index);
        switch (c) {
            case GEN_LIT:
                return "A";
            case HARDWARE:
                return "B";
            case COMP_SYS_ORG:
                return "C";
            case SOFTWARE:
                return "D";
            case DATA:
                return "E";
            case THEORY_OF_COMP:
                return "F";
            case MATH_OF_COMP:
                return "G";
            case INFO_SYS:
                return "H";
            case COMP_AND_METHODOLOGIES:
                return "I";
            case COMP_APPLICATIONS:
                return "J";
            case COMP_MILIEUX:
                return "K";
            default:
                //Should never happen
                return "L";
        }
    }

    /**
     * Get the amount of classifications.
     * 
     * @return the size of the classifications array
     */
    public int size() {
        return classifications.size();
    }

    @Override
    public String toString() {
        if (classifications.size() == 0) {
            return "(no classes)";
        }

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < classifications.size(); i++) {
            output.append(getLetter(i));
        }
        return output.toString();
    }
}
