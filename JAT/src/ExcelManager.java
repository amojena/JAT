import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Vector;

public class ExcelManager {
    private static String[] columns = {"Company Name", "Job Title", "Job ID", "Type",
    "Applied?", "URL", "Username", "Password", "Heard Back?"};

    private String path = "test.xlsx";
    static private int lastRow;

    /*
    Code for read and write function taken from https://www.callicoder.com/java-write-excel-file-apache-poi/
    */
    void write(Vector<jobApp> applications) throws IOException{

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

    private boolean fileExists(){
        return (new File("test.xlsx")).exists();
    }
    //TODO write read function that will read in job applications created
    Vector<jobApp> read() throws IOException, InvalidFormatException
    {
        //Saved applications to be returned
        Vector<jobApp> savedApps = new Vector<>(0);

        if (!fileExists()) {
            System.out.println("Base file does not exist.");
            return  savedApps;
        }
        // Obtain a workbook from the excel file
        Workbook workbook = WorkbookFactory.create(new File("test.xlsx"));

        // Get Sheet at index 0
        Sheet sheet = workbook.getSheetAt(0);

        // Read details of first application
        int rowIndex = 3;
        lastRow = rowIndex;
        Row row = sheet.getRow(rowIndex++);

        //run while there is a company name in the first cell of the row
        while (row != null)
        {
            jobApp app = new jobApp();
            app.companyName = row.getCell(0).getStringCellValue();
            app.jobTitle    = row.getCell(1).getStringCellValue();
            app.jobID       = row.getCell(2).getStringCellValue();
            app.type        = row.getCell(3).getStringCellValue();
            app.applied     = row.getCell(4).getStringCellValue();
            app.url         = row.getCell(5).getStringCellValue();
            app.username    = row.getCell(6).getStringCellValue();
            app.password    = row.getCell(7).getStringCellValue();
            app.heardBack   = row.getCell(8).getStringCellValue();
            app.print();
            savedApps.addElement(app);

            row = sheet.getRow(rowIndex++);
        }

        //close workbook
        workbook.close();
        lastRow = rowIndex;
        return savedApps;
    }

    void delete(int delIndex) throws IOException, InvalidFormatException {
        // Obtain a workbook from the excel file
        Workbook workbook = WorkbookFactory.create(new File("test.xlsx"));

        // Get Sheet at index 0
        Sheet sheet = workbook.getSheetAt(0);

        // Read details of first application
        Row row = sheet.getRow(delIndex + 3);
        sheet.removeRow(row);

        workbook.close();
        lastRow--;
    }

    //TODO write an append function so that it doesn't write a new file every time
    //TODO find a way to sort by Company name -> Job Title so excel file looks nice
}