package edu.odu.cs.cs350.acmClassifier;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.SMO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.functions.supportVector.RBFKernel;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.netlib.util.floatW;


/**
 * A Support Vector Machine (SVM) that trains on and classifies documents.
 */
public class Trainer implements Cloneable {

    /**
     * The internal learning machine, an SVM.
     */
    private SMO svm;

    /**
     * The list of attributes that the learning machine uses.
     */
    private ArrayList<Attribute> attrInfo;

    /**
     * The attribute that represents an ACM classification
     */
    private Attribute classAttr;

    /** 
     * The gamma parameter for the learning machine.
     */
    private double gamma;

    /**
     * The C parameter for the learning machine.
     */
    private double C;

    /** 
     * Construct a Trainer from a given dictionary of words.
     * 
     * @param dictionary the dictionary
     */
    public Trainer(String[] dictionary) {
        svm = new SMO();

        attrInfo = new ArrayList<Attribute>();
        
        for (String s: dictionary) {
            attrInfo.add(new Attribute(s));
        }

        //Letters A-K represent an ACM classification. L represents no classification
        String[] classNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        ArrayList<String> classList = new ArrayList<String>(Arrays.asList(classNames));

        //The classification attribute has a special name to not conflict with words in the dictionary
        classAttr = new Attribute("$ATTR_classification", classList);
        attrInfo.add(classAttr);

        //Initialize gamma and C to default values
        gamma = 0.01;
        C = 1.00;
    }

    /**
     * Copy constructor.
     * 
     * param trainer the trainer to copy from
     */
    public Trainer(Trainer trainer) {
        // TODO
        this.svm = trainer.svm;
        
        this.attrInfo=trainer.attrInfo;
        this.classAttr=trainer.classAttr;
        
        this.C = trainer.C;
        this.gamma= trainer.gamma;
        
    }

    @Override
    public Trainer clone() {
    	return new Trainer(this);
    }

    /** 
     * Get a reference to the learning machine.
     * 
     * @return the SVM
     */
    public SMO svm() {
        return svm;
    }



    /**
     * Classify a document using the learning machine.
     * 
     * @param doc the document to classify
     * @return the document's classification
     */
    public ACMClass classify(Document doc) {
        Instances toBeClassified = new Instances("toBeClassified", attrInfo, 1);
        toBeClassified.setClass(classAttr);

        int instanceSize = attrInfo.size() - 1;

        Instance toClassify = new DenseInstance(instanceSize);
        toClassify.setDataset(toBeClassified);

        for (int i = 0; i < instanceSize; i++) {
            toClassify.setValue(i, doc.normalizedWordCounts.get(i));
        }

        double[] dist;

        try {
            dist = svm.distributionForInstance(toClassify);
        } catch (Exception e) {
            System.out.println("Exception occurred in Trainer.classify()");
            System.out.println(e.toString());
            return new ACMClass();
        }

        //If a probability is equal to or above the threshold, it will be considered a valid classification.
        //The main exception is the null classification (L): because it doesn't make sense for the null
        //classification to be paired with other classifications, null is only output here if its probability
        //is greater than everything else.
        double THRESHOLD = 1.0 / classAttr.numValues();

        double nullProbability = dist[dist.length - 1];
        boolean isNullHighest = true;
        for (int i = 0; i < dist.length - 1; i++) {
            if (nullProbability < dist[i]) {
                isNullHighest = false;
                break;
            }
        }

        if (isNullHighest) {
            return new ACMClass();
        }

        //Null was not found to be the highest probability, so create the classification string
        StringBuffer classString = new StringBuffer();

        for (int i = 0; i < dist.length - 1; i++) {
            if (dist[i] >= THRESHOLD) {
                classString.append((char)('A' + i));
            }
        }

        return new ACMClass(classString.toString());
    }

    /**
     * Train the learning machine with a set of documents.
     * 
     * @param docs the set of documents to train on
     */
    public void train(Document[] docs) {
        //Calculate the size of the training set
        int dataSize = 0;
        for (Document d: docs) {
            dataSize += Math.max(1, d.classification.size());
        }

        Instances training = new Instances("trainingData", attrInfo, dataSize);
        training.setClass(classAttr);

        int instanceSize = attrInfo.size();

        //Construct the full training set
        for (Document d: docs) {
            int classCount = Math.max(1, d.classification.size());
            for (int i = 0; i < classCount; i++) {
                Instance instance = new DenseInstance(instanceSize);
                instance.setDataset(training);
                for (int j = 0; j < instanceSize - 1; j++) {
                    instance.setValue(j, d.normalizedWordCounts.get(j));
                }
                
                String letter;
                if (d.classification.size() == 0) {
                    letter = "L";
                } else {
                    letter = d.classification.getLetter(i);
                }

                instance.setValue(instanceSize - 1, letter);

                training.add(instance);
            }
        }

        //Create the kernel
        svm.setC(C);

        try {
            svm.setKernel(new RBFKernel(training, 25007, gamma));
            svm.buildClassifier(training);
        } catch (Exception e) {
            System.out.println("Exception occurred in Trainer.train()");
            System.out.println(e.toString());
        }
    }

    /**
     * Load the learning machine's state from a file.
     * 
     * @param filePath the file to read from
     * @return the SMO object that was loaded from the file path
     */

    public SMO loadState(String filePath){
        //deserializing or read weka obj from file path and save/return smo obj
        
        //new SMO obj, will be assigned model from file
        File f = new File(filePath);
        //System.out.println(f.exists()+" is : "+filePath);
    

        
        try
        {
           
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.svm = (SMO) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loading SVM from file: "+filePath);
            return this.svm;
        
        }catch(Exception e) {System.out.print(e);}
        
        
        
        //default is error case not valid file path return null
        System.out.println("SVM object is empty");
        return this.svm;
        
    }

    /**
     * Save the learning machine's state to a file.
     * 
     * @param filePath the file to write to
     * 
     *
     */
    public void saveState(String filePath) throws IOException 
    {
        File f = new File(filePath);
        //System.out.println(f.exists()+" is : "+filePath);
        if (f.exists())
        {
            try (FileOutputStream fos = new FileOutputStream(f);
                 ObjectOutputStream oos = new ObjectOutputStream(fos))
                 {
                    //write svm object into file 
                    oos.writeObject(this.svm);

                    //flush obj out stream and close file & obj stream
                    oos.flush();
                    oos.close();
                    fos.close();
                    System.out.println("Saving SVM object to : "+filePath);
                }catch(IOException e) {
                    System.out.println("Stream Error");}
        }
        else{
            System.out.println(filePath+" does not exist");
        }
                   
     }

    @Override
    public String toString() {
        // TODO
        return "I am a Reader";
    }
}
