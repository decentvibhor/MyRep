package com.qtpselenium.salesforce.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.Keywords;
import com.qtpselenium.util.TestUtil;
import com.qtpselenium.util.Xls_Reader;

public class CreateLead {
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//salesforce//config//TestCases.xlsx");
	Keywords session=Keywords.getInstance();

	
	@Test(dataProvider="getData")
	
	public void createLead(Hashtable<String,String> data){
	//runmode of the test
    	
    	if(!TestUtil.getRunmode("CreateLeadTest", xls))
    		throw new SkipException("Skipping Test as Runmode is No");
    	
    	//runmode of dataset
    	if(data.get("Runmode").equals("N"))
    		throw new SkipException("Skipping as flag is N");
    	
		session.openBrowser(data.get("browser"));
		
		//check if you are logged in
		//if(!session.isElementPresent("searchField")){
			//login
			session.loginAsDefaultUser();
			session.click("leadsTab");
			session.click("newLeadButton");
			//Assert.assertTrue(session.validateText("vibhor agarwal","leadOwner"),"Lead owner name is not correct");
			session.input("firstNameTitle",data.get("title"));
			session.input("leadName",data.get("firstName"));
			session.input("leadLastName",data.get("lastName"));
			session.input("leadCompany",data.get("company"));
			session.input("leadStatus",data.get("status"));
			session.click("leadSave");
		}
	//}
		
		@DataProvider //(parallel=true)
		public Object[][] getData(){
			return TestUtil.getData("CreateLeadTest",xls);
			/*Object data[][]=new Object[2][1];
			Hashtable<String,String> t1=new Hashtable<String,String>();
			Hashtable<String,String> t2=new Hashtable<String,String>();
			t1.put("title","Mr.");
			t1.put("firstName","Barack");
			t1.put("lastName","Obama");
			t1.put("company","USA");
			t1.put("status","Qualified");
			t1.put("browser","Mozilla");
			
			t2.put("title","Mr.");
			t2.put("firstName","Barack");
			t2.put("lastName","Obama");
			t2.put("company","USA");
			t2.put("status","Qualified");
			t2.put("browser","Chrome");
			/*
			data[1][0]="Mr.";
			data[1][1]="Barack";
			data[1][2]="Obama";
			data[1][3]="USA";
			data[1][4]="Qualified";
			data[1][5]="Chrome";
			
			data[0][0]=t1;
			data[1][0]=t2;
			
			return data;*/
		}
		
	}


