package edu.odu.cs.cs350.acmClassifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.txt.TXTParser;

import org.xml.sax.SAXException;

public class TextFileReader {
   BodyContentHandler handler = new BodyContentHandler();
   Metadata metadata = new Metadata();
   FileInputStream inputstream;
   ParseContext pcontext = new ParseContext();
   TXTParser TexTParser = new TXTParser();

   public void textOutput(String filepath) throws Exception {
      inputstream = new FileInputStream(new File(filepath));

      TexTParser.parse(inputstream, handler, metadata, pcontext);
      System.out.println("Contents of the document:" + handler.toString());

      System.out.println("Metadata of the document:");
      String[] metadataNames = metadata.names();

      for (String name : metadataNames) {
         System.out.println(name + " : " + metadata.get(name));
      }
   }
   /*
    * public String getFileText() {
    * return "Text Content:\n" + handler.toString();
    * }
    */
}
