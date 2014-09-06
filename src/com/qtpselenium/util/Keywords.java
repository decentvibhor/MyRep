package com.qtpselenium.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//Selenium layer
public class Keywords {
	WebDriver driver=null;
	WebDriver bak_chrome;
	WebDriver bak_mozilla;
	WebDriver bak_ie;
	Properties OR=null;
	Properties ENV=null;
	static Keywords k;
	WebDriverWait wait;
	Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
	
	private Keywords(){
		try{
			OR=new Properties();
			
			FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//salesforce//config//OR.properties");
			OR.load(fs);
			//init env 
			ENV=new Properties();
			String filename=OR.getProperty("environment")+".properties";
			fs=new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//salesforce//config//"+filename);
			ENV.load(fs);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static Keywords getInstance(){
		if(k==null)
			k=new Keywords();
		return k;
	}
	
	public void executeKeywords(String testName,Xls_Reader xls,Hashtable<String,String> table){
		
		int rows=xls.getRowCount("Test Steps");
		for(int rNum=2;rNum<=rows;rNum++){
			
			String tcid=xls.getCellData("Test Steps","TCID",rNum);
			
			if(tcid.equals(testName)){
			    String keyword=xls.getCellData("Test Steps","Keyword",rNum);
			    String object=xls.getCellData("Test Steps","Object",rNum);
			    String data=xls.getCellData("Test Steps","Data",rNum);
			    String result="";
			    // execute the keywords
			    if(keyword.equals("openBrowser"))
			    	result=openBrowser(table.get(data));
			    else if(keyword.equals("navigate"))
			    	result=navigate(data);
			    else if(keyword.equals("click"))
			    	result=click(object);
			    else if(keyword.equals("input"))
			    	result=input(object,table.get(data));
			    else if(keyword.equals("isElementPresent"))
			    	result=isElementPresent(object);
			    else if(keyword.equals("verifyLogin"))
			    	result=verifyLogin(table.get("Flag"));
			    else if(keyword.equals("waitForElementPresence"))
			    	result=waitForElementPresence(object);
			    //assertions
			    if(!result.equals("Pass")){
			    	
			    	//Screenshot
			    	try{
			    	// testname_keywordname_line_num.jpg
			    	String fileName=tcid+"_"+keyword+"_"+rNum+".jpg";	
			    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"//Screenshots//"+fileName)); 
			    	}catch(Exception e){
			    		
			    	}
			    	//String fileName=testName+"_"+keyword+".jpg";
			    	
			    	
			    	String proceed=xls.getCellData("Test Steps","Proceed_On_Fail",rNum);
			    	if(proceed.equals("Y")){
			    		//fail and continue the test
			    		try{
			    			Assert.fail(result);
			    		}catch(Throwable t){
			    			System.out.println("*******Error*******");
			    			//listeners
			    			ErrorUtil.addVerificationFailure(t);
			    		}
			    	}else{
			    		//fail and stop
			    		Assert.fail(result);
			    	}
			    	
			    }
			   
			    
			
			log(tcid +" ------"+ keyword +" ------"+ object +" ------"+ data+" ------" +result);
			}
		}
	}
	
	public String openBrowser(String browserName){ //open a browser
		
		if(browserName.equals("Mozilla")&& bak_mozilla!=null){
		   driver=bak_mozilla;
		   return "Pass";
		}else if(browserName.equals("Chrome")&& bak_chrome!=null){
			driver=bak_chrome;
			return "Pass";
		}else if(browserName.equals("IE")&& bak_chrome!=null){
			driver=bak_ie;
			return "Pass";
		}
		
		if(browserName.equals("Mozilla")){
	    	driver=new FirefoxDriver();	
	    	bak_mozilla=driver;
		    
		}
	    	else if(browserName.equals("Chrome")){
	    		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//drivers//chromedriver.exe");
	    		driver=new ChromeDriver();
	    		bak_chrome=driver;
	    		
	    	}else if(browserName.equals("IE")){
	    		//Set the exe
	    		bak_ie=driver;
	    		
	    	}
		     driver.manage().window().maximize();
		     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		     
		     //initialize WebDriverWait 
		     if(driver!=null){
		    	 wait=new WebDriverWait(driver, 30);
		     }
		     return "Pass";
	}
	
	public String navigate(String URLKey){ //load page
		try{
		driver.get(ENV.getProperty(URLKey)); 
		}catch(Exception e){
			return "Fail - Unable to navigate to - " + URLKey;
		}
		return "Pass";
	}
	
	public String click(String xpathKey){  //click
		try{
		driver.findElement(By.xpath(OR.getProperty(xpathKey))).click();
		}catch(Exception e){
			return "Fail - Unable to click on - " + xpathKey;
		}
		return "Pass";
		
	}
	
	public String input(String xpathKey,String text){ //text fields
		try{
		driver.findElement(By.xpath(OR.getProperty(xpathKey))).sendKeys(text);
		}catch(Exception e){
			return "Fail - Unable to write on - " + xpathKey;
		}
		return "Pass";
	}
	
	public String validateTitle(String expectedTitleKey){
		String expectedTitle=OR.getProperty(expectedTitleKey);
		String actualTitle=driver.getTitle();
		if(expectedTitle.equals(actualTitle))
			return "Pass";
		else 
			return "Fail"; 
			
	}
	
	public String validateText(String expectedText,String xpathKey){
		
		String actualText=driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText();
		if(actualText.equals(expectedText))
			return "Pass";
		else
			return "Fail";
		
	}
	
	public String isElementPresent(String xpathKey){
		int count=driver.findElements(By.xpath(OR.getProperty(xpathKey))).size();
		if(count==0)
			return "Fail - Element not Found "+ xpathKey;
		else
			return "Pass";
	}
	
	public String close(){
		if(driver!=null){
		driver.quit();
		if(bak_chrome!=null)
		bak_chrome.quit();
		if(bak_mozilla!=null)
		bak_mozilla.quit();
		if(bak_ie!=null)
		bak_ie.quit();
		bak_chrome=bak_mozilla=bak_ie=null;
	    }
		return "Pass";
	}
	
	public String waitForElementPresence(String objectKey){
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(objectKey))));
		}catch(Exception e){
			return "Fail - Element Not Visible" + objectKey;
		}
		return "Pass";
		
	}
	
	/******************************App dependent functions************************/
	
	public void loginAsDefaultUser(){
		k.navigate("testSiteURL");
    	//k.click("loginPageLink");
    	k.input("username",ENV.getProperty("adminUsername"));
    	k.input("password",ENV.getProperty("adminPassword"));
    	k.click("loginButton");
	}
	
	public String verifyLogin(String flag){
		
		String actualTitle=driver.getTitle();
		String loginPageTitle=OR.getProperty("loginPageTitle");
		System.out.println(actualTitle);
		System.out.println(loginPageTitle);
		if(actualTitle.equals(loginPageTitle) && flag.equals("Y"))
			return "Fail - not able to login with correct data";
		else if(!actualTitle.equals(loginPageTitle)&& flag.equals("N"))
			return "Fail - able to login with wrong data";
		else
		   return "Pass";
		
	}
	
	//logging
	public void log(String msg){
		APPLICATION_LOGS.debug(msg);
	}
	
	

}
