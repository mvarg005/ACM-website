package edu.odu.cs.cs350.acmClassifier;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.collections4.functors.CatchAndRethrowClosure;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rometools.utils.IO;

import weka.classifiers.functions.SMO;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.lang.*;
public class TestTrainer
{
	private String[] dictionary = {"a", "b", "c", "d"};
	private Double[] words1 = {0.0, 1.0, 0.0, 0.0};
	private Double[] words2 = {1.0, 0.0, 0.0, 0.0};

    @Test
	void testTrainer() {
		Trainer t = new Trainer(dictionary);
        assertNotNull(t.svm());
    }

	@Test
	void testTrain() {
		Trainer t = new Trainer(dictionary);

		Document[] docs = {new Document(), new Document()};
		docs[0].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words1));
		docs[0].classification = new ACMClass("ACE");
		docs[1].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words2));
		docs[1].classification = new ACMClass();

		t.train(docs);
		ACMClass class1 = t.classify(docs[0]);
		ACMClass class2 = t.classify(docs[1]);

		assertEquals(class1.size(), 3);
		assertEquals(class1.getLetter(0), "A");
		assertEquals(class1.getLetter(1), "C");
		assertEquals(class1.getLetter(2), "E");
		assertEquals(class2.size(), 0);
	}

	/**
	 * @throws IOException
	 */
	@Test
	void testLoadState() throws IOException
	{

		//temp files to make sure t and t2 return load states are equal
		File file_t=null;
		File file_t2=null;
		try {
			file_t = File.createTempFile("tempf",null);
			file_t2 = File.createTempFile("tempf2", null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}




		
		Trainer t = new Trainer(dictionary);
		Trainer t2 = new Trainer(dictionary);
		
		Document[] docs = {new Document(), new Document()};
		docs[0].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words1));
		docs[0].classification = new ACMClass("ACE");
		docs[1].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words2));
		docs[1].classification = new ACMClass();

		//check that svm being read is not null since train was called
		t.train(docs);
		t2.train(docs);

		assertNotNull(t.svm());
		assertNotNull(t2.svm());
		

		//save the t and t2 states to their respective temp files
		String t_path = file_t.getAbsolutePath();
		String t2_path = file_t2.getAbsolutePath();
		
		t.saveState(t_path);
		t2.saveState(t2_path);

		//make sure load state has equal states for t and t2 
		
		
		try {
			
			SMO t_svm = t.loadState(t_path);
			SMO t2_svm = t2.loadState(t2_path);
			assertNotNull(t_svm);
			assertNotNull(t2_svm);

			assertEquals(t.svm(),t_svm);
			assertEquals(t2.svm(),t2_svm);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		


		
		file_t.deleteOnExit();
		file_t2.deleteOnExit();
	}

	
	/**
	 * @throws Exception
	 * 
	 */
	@Test
	void testSaveState() throws Exception {
		//check if smo object to be save is not null
		Trainer t = new Trainer(dictionary);
		Document[] docs = {new Document(), new Document()};
		docs[0].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words1));
		docs[0].classification = new ACMClass("ACE");
		docs[1].normalizedWordCounts = new ArrayList<Double>(Arrays.asList(words2));
		docs[1].classification = new ACMClass();
		//check that svm being read is not null since train was called
		assertNotNull(t.svm());

		t.train(docs);

		File file_t = File.createTempFile("tempf",null);
		
		
		String t_path = file_t.getAbsolutePath();
		assertEquals(t_path, file_t.getAbsolutePath());
		t.saveState(t_path);
		SMO t_svm = t.loadState(t_path);
		
		
		assertEquals(t.svm(),t_svm);

	

		file_t.deleteOnExit();
	}

	
	
	@Test
	void testClone() 
	{
		
		Trainer t = new Trainer(dictionary);
		Trainer copy = (Trainer)t.clone();

		assertThat(copy, is(not(sameInstance(t))));

		
	}
}