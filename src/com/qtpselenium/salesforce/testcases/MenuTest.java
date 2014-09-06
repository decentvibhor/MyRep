package com.qtpselenium.salesforce.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.Keywords;
import com.qtpselenium.util.TestUtil;
import com.qtpselenium.util.Xls_Reader;

public class MenuTest {
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//salesforce//config//TestCases.xlsx");
	Keywords k=Keywords.getInstance();
	//Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//salesforce//config//TestCases.xlsx");
	@Test(dataProvider="getData")
	public void topMenuTest(Hashtable<String,String> data){
	//runmode of the test
    	
    	if(!TestUtil.getRunmode("MenuTest", xls))
    		throw new SkipException("Skipping Test as Runmode is No");
    	//runmode of dataset
    	if(data.get("Runmode").equals("N"))
    		throw new SkipException("Skipping as flag is N");
    	
		/*Keywords session=Keywords.getInstance();
		session.openBrowser(data.get("Browser"));
		session.navigate("testSiteURL");
		//Assert.assertTrue(session.isElementPresent("solutionsTab"),"solutions tab not present");
		session.click("solutionsTab");
		session.navigate("testSiteURL");
		//Assert.assertTrue(session.isElementPresent("prodTab"),"product tab not present");
		session.click("prodTab");
		session.navigate("testSiteURL");
		//Assert.assertTrue(session.isElementPresent("servicesTab"),"services tab not present");
		session.click("servicesTab"); */
    	k.executeKeywords("MenuTest", xls,data);
	}
	
		@DataProvider
		public Object[][] getData(){
			return TestUtil.getData("MenuTest",xls);
			/*Object[][] data=new Object[2][1];
			
			data[0][0]="Mozilla";
			data[1][0]="Chrome";
			return data;*/
		}
	}

