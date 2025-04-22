import org.apache.poi.ss.usermodel.*; //to settle this like idk download or sth ig
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
// import java.util.HashMap;

public class UserDatabase {
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
                boolean isFirstRow = true; //to skip 1st row (header row)
                for (Row row:sheet) {
                    if (isFirstRow) {
                        isFirstRow = false; //skip 1st row only
                        continue;
                    }                                       
                    //int rowNum = row.getRowNum() - 1; //row number = key for each user data
                    String key = row.getCell(1).toString(); //2nd column ie. nric is key, rest is value
                    List<String> rowData = new ArrayList<>();

                    //for (Cell cell: row)
                        //rowData.add(cell.toString()); //convert each cell to string and store in list eg. name, age, marital status
                    for (int i=0; i<row.getLastCellNum(); i++) {
                        if (i==1) continue; //skip nric column
                        Cell cell = row.getCell(i);
                        rowData.add(cell.toString());
                    }
                    
                    dataMap.put(key, rowData); //key, value
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dataMap; //ie. nric -> [name, age, marital status, password]
    }

    //eg. applicant -> (nric -> [...details...])
    public static HashMap<String, HashMap<String, List<String>>> combinedHashmap(HashMap<String, List<String>> sheet1, HashMap<String, List<String>> sheet2, HashMap<String, List<String>> sheet3) {
        HashMap<String, HashMap<String, List<String>>> nestedMap = new HashMap<>(); ///declare and allocate memory for new hashmap
        nestedMap.put("applicant", sheet1); 
        nestedMap.put("hdbofficer", sheet2);
        nestedMap.put("hdbmanager", sheet3);

        return nestedMap;
    }
    public static HashMap<String, List<String>> chooseHashmap(HashMap<String, HashMap<String, List<String>>> nest_map, String nest_key) {
        if (nest_map.containsKey(nest_key)) {
            return nest_map.get(nest_key);
        }
        return null;
    }

}
