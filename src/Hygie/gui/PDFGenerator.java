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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import hygie.services.ServiceQuestion;
import hygie.services.ServiceQuiz;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class PDFGenerator {
    public static void generatePDF(Stage stage) {
        
           ServiceQuiz sq = new ServiceQuiz();
        
      //   System.out.println("aloo"+this.getQuestionnaireId());// Instantiate your service class
        List<Map<String, Object>> resultList = sq.getAllbyquiz(0); // Call the getAll2() method to get the data

        
        // Create a new paragraph

        
        
        
      int    previousquestionId = -1  ;
        // Create a new document
        Document document = new Document();
        try {
                        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        //    PdfWriter.getInstance(document, new FileOutputStream("example.pdf"));

            // Create a file chooser to allow the user to select a save location for the PDF file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);
            
            if (file != null) {
                // Create a new PDF file and open it for writing
                                PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();
                  Paragraph questionh = new Paragraph("Questionaire: " + resultList.get(0).get("questionnaire_name"), headerFont);
// Create a new paragraph

// Set the alignment of the paragraph to center
questionh.setAlignment(Element.ALIGN_CENTER);

// Add the paragraph to the document
document.add(questionh);

                

                  for (Map<String, Object> row : resultList) {
                int questionnaireId = (int) row.get("questionnaire_id");
                String questionnaireName = (String) row.get("questionnaire_name");
                int questionId = (int) row.get("question_id");
                String questionText = (String) row.get("question_text");
                String responseText = (String) row.get("reponse_text");

                if (questionId != previousquestionId) {
                    // Add the questionnaire header
                    Paragraph questionnaireHeader = new Paragraph("Question: " + questionText, headerFont);
                    document.add(questionnaireHeader);

                    // Add a line break
                    document.add(new Paragraph(" "));
                }
                if(responseText!= null)
                {
                     Paragraph questionnaireHeader = new Paragraph("Reponse: " + responseText, headerFont);
                    document.add(questionnaireHeader);

                    // Add a line break
                    document.add(new Paragraph(" "));
                }

                // Add the question and response
              
                // Add a line break
                document.add(new Paragraph(" "));
                previousquestionId = questionId      ;

                // Update the previous questionnaire id
            }
                
               
        
                
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
