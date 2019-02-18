import com.sun.xml.internal.bind.v2.TODO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Vector;

public class ExcelWriter {
    private static String[] columns = {"Company Name", "Job Title", "Job ID", "Type",
    "Applied?", "URL", "Username", "Password", "Heard Back?"};

    private String path = "test.xlsx";

    /*
    Code for this function taken from https://www.callicoder.com/java-write-excel-file-apache-poi/
    */
    void write(Vector<jobApp> applications) throws IOException, InvalidFormatException {

        //Create workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Job Applications");

        //Format header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setFontName("Arial");

        // Create a CellStyle with the font for the header
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(2);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }


        // Create a CellStyle with the font for the data
        Font dataFont = workbook.createFont();
        dataFont.setBold(false);
        dataFont.setFontHeightInPoints((short)12);
        dataFont.setFontName("Arial");

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setFont(dataFont);


        int rowNum = 3;
        for(jobApp app : applications)
        {
            String[] appDetails = {app.companyName, app.jobTitle, app.jobID, app.type,
                    app.applied, app.url, app.username, app.password, app.heardBack};
            Row row = sheet.createRow(rowNum++);
            int col = 0;
            for (String appDetail : appDetails)
            {
                Cell cell = row.createCell(col++);
                cell.setCellValue(appDetail);
                cell.setCellStyle(dataCellStyle);
            }

        }

        for(int i = 0; i < columns.length; i++)
            sheet.autoSizeColumn(i);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }

    //TODO write read function that will read in job applications created
    //TODO write an append function so that it doesn't write a new file every time
    //TODO find a way to sort by Company name -> Job Title so excel file looks nice
}