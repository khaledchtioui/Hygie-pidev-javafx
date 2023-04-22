/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.gui;

/**
 *
 * @author Khaled
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;

public class PDFGenerator {
    public static void generatePDF(Stage stage) {
        // Create a new document
        Document document = new Document();
        try {
            // Create a file chooser to allow the user to select a save location for the PDF file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);
            
            if (file != null) {
                // Create a new PDF file and open it for writing
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                
                // Add content to the PDF file
                document.add(new Paragraph("Hello, world!"));
                
                // Close the document
                document.close();
                
                // Display a success message to the user
                System.out.println("PDF file created and saved successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

