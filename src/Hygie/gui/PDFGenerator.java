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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import hygie.services.ServiceQuestion;
import hygie.services.ServiceQuiz;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PDFGenerator {
    public static void generatePDF(Stage stage , int id) {
        
           ServiceQuiz sq = new ServiceQuiz();
        
      //   System.out.println("aloo"+this.getQuestionnaireId());// Instantiate your service class
        List<Map<String, Object>> resultList = sq.getAllbyquiz2(id); // Call the getAll2() method to get the data
        System.out.println(resultList);
        if(resultList.isEmpty()||resultList.get(0).isEmpty())
        {
        
               sq.showNotification("PDF file not created because Quiz is empty",0);
        }
        else
        {
        
        // Create a new paragraph

        
        
        
      int    previousquestionId = -1  ;
        // Create a new document
// Create a new document
Document document = new Document();
document.setMargins(36, 36, 36, 36); // set margins to 36 points
document.setPageSize(PageSize.A4); // set page size to A4

// ... rest of your code here ...
        try {
                        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(61, 145, 64)); // Création de la police avec une couleur personnalisée
Font questionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
Font responseFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        //    PdfWriter.getInstance(document, new FileOutputStream("example.pdf"));

            // Create a file chooser to allow the user to select a save location for the PDF file
            FileChooser fileChooser = new FileChooser();
fileChooser.setInitialDirectory(new File("C:/Users/Khaled/Desktop/pdfs")); // set the initial directory to "C:/my_folder"

            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);
            
            if (file != null) {
            MyHeaderFooter headerFooter = new MyHeaderFooter();

                // Create a new PDF file and open it for writing
               PdfWriter.getInstance(document, new FileOutputStream(file)).setPageEvent(headerFooter);
                document.open();
                  Paragraph questionh = new Paragraph("Questionaire: " + resultList.get(0).get("questionnaire_name"), titleFont);
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
                   // row.get(questionId)
                    Paragraph questionnaireHeader = new Paragraph(questionText, questionFont);
                    document.add(questionnaireHeader);

                    // Add a line break
                    document.add(new Paragraph(" "));
                }
                if(responseText!= null)
                {
                     Paragraph questionnaireHeader = new Paragraph(responseText, responseFont);
                    document.add(questionnaireHeader);

                    // Add a line break
                  //  document.add(new Paragraph(" "));
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
                         sq.showNotification("PDF file created and saved successfully",1);

                System.out.println("PDF file created and saved successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
          sq.showNotification("PDF file not created ",0);


        }
    }
    
    
   
    
    
    }
   private static class MyHeaderFooter extends PdfPageEventHelper {
        private static final Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Ajouter le numéro de page et la date au pied de page
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase(String.format("Page %d | %s", writer.getPageNumber(), DATE_FORMAT.format(new Date())), FONT);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);
        }
    }


}




