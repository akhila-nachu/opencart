package utilities;

	import java.io.IOException;

	import org.testng.annotations.DataProvider;

	public class DataProviders {

		//DataProvider 1
		
		@DataProvider(name="LoginData")
		public String [][] getData() throws IOException //returns 2dim string type of array
		{
			String path=".\\testData\\Opencart_LoginData.xlsx";//taking xl file from testData folder
			
			ExcelUtility xlutil=new ExcelUtility(path);//creating an object for ExcelUtility class
			
			int totalrows=xlutil.getRowCount("Sheet1");	//counting tot no.of rows by passing sheetname
			int totalcols=xlutil.getCellCount("Sheet1",1); //counting tot no.of colomns by passing sheetname & row number
					
			String logindata[][]=new String[totalrows][totalcols];//created two dimension array with size of rows&cols which can store the data user and password
			
			for(int i=1;i<=totalrows;i++)  //1   //read the data from xl storing in two deminsional array
			{		
				for(int j=0;j<totalcols;j++)  //0    i is rows j is col
				{
					logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  //1,0 if index is i in xl, its i-1 in array to ignore heading of xl
				}
			}
		return logindata;//returning two dimension array
					
		}
		
		//DataProvider 2
		
		//DataProvider 3
		
		//DataProvider 4
	}
