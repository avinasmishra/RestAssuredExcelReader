package resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public ArrayList<String> getExcelData(String testCaseName) throws IOException
	{
		ArrayList<String> aL = new ArrayList<String>();

		FileInputStream fis = new FileInputStream("C:\\Users\\Avinash\\Desktop\\Selenium by Rahul Shethy\\API Testing\\TestConsumerData.xlsx");
		//this class having ability to read all excel details
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		//first we count all sheetname(steps- excel->sheetname->all rows->cells)
		int sheets = workbook.getNumberOfSheets();
		System.out.println("Total sheetCount: "+sheets);

		//print all sheetname
		for(int i=0;i<sheets;i++)
		{
			String sheetName = workbook.getSheetName(i);					
			//System.out.println("SheetName: "+sheetName);

			if(sheetName.equalsIgnoreCase("addBookAPI"))
			{
				XSSFSheet sheet = workbook.getSheetAt(i);

				//identify TestCase(one of the column name in 1st row) colon by scanning entire 1st row

				Iterator <Row> rows = sheet.iterator();
				//want first row
				Row firstRow = rows.next();
				//now iterate into 1st row and get all cell data
				Iterator<Cell> cel = firstRow.cellIterator();
				//use while loop
				int k=0;
				int column =0;
				while(cel.hasNext())
				{
					Cell value = cel.next();
					if(value.getStringCellValue().equalsIgnoreCase("testcase"))
					{
						column = k;
					}
					k++;

				}
				System.out.println("Test Case index: "+column);

				//now we got Purchase testcase is inside testcase column
				//now iterate entire column and get purchase test case name

				while(rows.hasNext())
				{
					Row ro = rows.next();
					if(ro.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName))
					{
						Iterator<Cell> cv = ro.cellIterator();
						while(cv.hasNext())
						{
							Cell c = cv.next();
							//if wants to store in arratList, create list above and add
							if(c.getCellType()==CellType.STRING)
							{
							aL.add(c.getStringCellValue());
							}
							else if(c.getCellType()==CellType.NUMERIC)
							{	
								aL.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
							
						}
					}
				}	

			}
		}
		return aL;
	}
}
