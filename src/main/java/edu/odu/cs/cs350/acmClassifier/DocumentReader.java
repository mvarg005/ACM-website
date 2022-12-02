package edu.odu.cs.cs350.acmClassifier;

//All the File handlers
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//All the Tika Components
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

//Exception Handling
import org.xml.sax.SAXException;

public class DocumentReader {

    // handler and contexts
    BodyContentHandler handler = new BodyContentHandler(-1);
    Metadata metadata = new Metadata();
    ParseContext pcontext = new ParseContext();
    FileInputStream inputstream;
    PDFParser pdfparser = new PDFParser();

    // Locates the filepath as inputted pdf file and opens the context and extracts
    // the pdf file
    public void importPDF(String filepath) throws FileNotFoundException {
        inputstream = new FileInputStream(new File(filepath));
        try {
            pdfparser.parse(inputstream, handler, metadata, pcontext);
            
            System.out.println(handler.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

    }

    public String getDocumentText() {
        return handler.toString();
    }

}
