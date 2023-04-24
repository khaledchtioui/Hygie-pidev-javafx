/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.gui;

import hygie.services.ServiceQuiz;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Khaled
 */
public class ExcelExporter {
    
   

public static void exportToExcel() {
    // Get the data to export
    ServiceQuiz sq =new ServiceQuiz() ;
    List<Map<String, Object>> dataList = sq.getAllbyquiz(0);

    // Create a new Workbook object
    Workbook workbook = new XSSFWorkbook();

    // Create a new sheet in the workbook
    Sheet sheet = workbook.createSheet("Data");

    // Create a header row
    Row headerRow = sheet.createRow(0);
    String[] headers = {"questionnaire_id", "questionnaire_name", "question_id", "question_text", "question_type", "question_point", "reponse_text"};
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
    }

    // Populate the data rows
    int rowNum = 1;
    for (Map<String, Object> row : dataList) {
        Row dataRow = sheet.createRow(rowNum++);
        dataRow.createCell(0).setCellValue((int) row.get("questionnaire_id"));
        dataRow.createCell(1).setCellValue((String) row.get("questionnaire_name"));
        dataRow.createCell(2).setCellValue((int) row.get("question_id"));
        dataRow.createCell(3).setCellValue((String) row.get("question_text"));
        dataRow.createCell(4).setCellValue((int) row.get("question_type"));
        dataRow.createCell(5).setCellValue((int) row.get("question_point"));
        dataRow.createCell(6).setCellValue((String) row.get("reponse_text"));
    }

    // Prompt the user for a file save location
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Excel File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
    File file = fileChooser.showSaveDialog(null);

    // Write the workbook to the chosen file
    if (file != null) {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    
}
