import org.apache.poi.ss.usermodel.*; //to settle this like idk download or sth ig
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
// import java.util.HashMap;

public class ProjectDatabase {
    //create 3 hashmaps storing data details of Applicant, HDBOfficer, HDBManager
        //ApplicantList.xlsx
        //OfficerList.xlsx
        //ManagerList.xlsx
        // --> to creat the 3 hashmaps in main by calling function thrice for each xlsx sheet
    //1. function/method converting xlsx data into hastable/map? --> call in Main class to create different Userdatabases from the 3 excel sheets
        //different keys (integer) for different people
        //values are array list storing details [name, nric, age, marital status]
        //return hashmap
    //2. function returning values of specific array (value) of hashmap
    public static HashMap<String, List<String>> excelToHashmap (String filepath) {
        HashMap<String, List<String>> dataMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(new File(filepath));
            Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); //read first sheet
                boolean isFirstRow = true; 
                for (Row row:sheet) {
                    if (isFirstRow) {
                        isFirstRow = false;
                        continue;
                    }                                       
                    String key = row.getCell(0).toString(); 
                    List<String> rowData = new ArrayList<>();

                    for (int i=1; i<row.getLastCellNum(); i++) {
                        Cell cell = row.getCell(i);
                        rowData.add(cell.toString());
                    }
                    
                    dataMap.put(key, rowData); //key, value
                }
            } catch (IOException e) { //??
                e.printStackTrace();
            }
            return dataMap; 
    }
}
