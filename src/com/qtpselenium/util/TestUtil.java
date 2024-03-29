package com.qtpselenium.util;

import java.util.Hashtable;

public class TestUtil {
	
	public static Object[][] getData(String testCase,Xls_Reader xls){
		
		//row num on which the test is starting
		//rows of data
		//columns
		//extract data
		int testStartRowNum=1;
		while (!xls.getCellData("Data",0,testStartRowNum).equals(testCase)){
			testStartRowNum++;
		}
		System.out.println("Test Case "+testCase+" starts from row "+ testStartRowNum);
		
		//rows of data
		int dataStartRowNum=testStartRowNum+2;
		int rows=0;
		while(!xls.getCellData("Data",0,dataStartRowNum+rows).equals("")){
			rows++;
		}
		
		System.out.println("Total rows in test case "+testCase+" are "+ rows);
		
		//total columns
		int colStartRowNum=testStartRowNum+1;
		int cols=0;
		while(!xls.getCellData("Data",cols,colStartRowNum).equals("")){
			cols++;
		}
		
		System.out.println("Total columns in test case "+testCase+" are "+ cols);
		
		System.out.println("*********************************");
		
		//Extract Data
		
		Object testData[][]=new Object[rows][1];
		int index=0;
		Hashtable<String,String> table=null;
		
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
			table=new Hashtable<String,String>();//initialize for every row
			
			for(int cNum=0;cNum<cols;cNum++){
				String key=xls.getCellData("Data", cNum, colStartRowNum);
			    String value=xls.getCellData("Data", cNum, rNum);
				System.out.print(value +"---- ");
				//fill the table
				table.put(key, value);
			}
			//put table in array
			 System.out.println("");	
			 testData[index][0]=table;
			 index++;
		}
		System.out.println("*********************************");
		
		return testData;
	}
	
	//true-Y
	//false-N
	public static boolean getRunmode(String testName,Xls_Reader xls){
		
		for(int rNum=2;rNum<=xls.getRowCount("Test Cases");rNum++){
			String testCaseName=xls.getCellData("Test Cases","TCID", rNum);
			if(testCaseName.endsWith(testName)){
				//Chk RunMode
				  if(xls.getCellData("Test Cases","Runmode", rNum).equals("Y")){
					  return true; 
				  }
				   
				   else{
					   return false;
				   }
					   
			}
			
		}
		return false;
	}

}
