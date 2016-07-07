package bing;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class bingui {

	
		public String translateText(WebDriver driver,String from,String to,String text) throws InterruptedException{
			driver.findElement(By.cssSelector(".sourceText .LS_Header")).click();
			driver.findElement(By.cssSelector(".sourceText .LanguageList .LS_Item[value="+ from +"]")).click();
			driver.findElement(By.cssSelector(".destinationText .LS_Header")).click();
			driver.findElement(By.cssSelector(".destinationText .LanguageList .LS_Item[value="+ to +"]")).click();
			driver.findElement(By.cssSelector("#srcText")).sendKeys(text);
			Thread.sleep(3000);
			String convertedString= driver.findElement(By.cssSelector("#destText")).getText();
	        return convertedString;
		}
		
		public String autotextConversion(WebDriver driver,String to,String text) throws InterruptedException{
			
			driver.findElement(By.cssSelector(".sourceText  .LS_Header")).click();
			 driver.findElement(By.cssSelector(".sourceText  .LanguageList  .LS_Item[value='-']")).click();
			driver.findElement(By.cssSelector("#srcText")).sendKeys(text);
	        driver.findElement(By.cssSelector(".destinationText .LS_Header")).click();
			driver.findElement(By.cssSelector(".destinationText .LanguageList .LS_Item[value="+ to +"]")).click();
			//driver.findElement(By.cssSelector("#TranslateButton")).click();
			Thread.sleep(3000);
			String convertedString= driver.findElement(By.cssSelector("#destText")).getText();
		    return convertedString;
		}
		
		public static void main(String[] args) throws InterruptedException{
			String from="fr";
			String to="en";
			String text="bonjour";
			File fExecutable = new File("/home/vibhachugh/firefox/firefox");
			FirefoxBinary ffBinary = new FirefoxBinary(fExecutable);
			FirefoxProfile ffProfile = new FirefoxProfile();
			WebDriver driver = new FirefoxDriver(ffBinary, ffProfile);

			driver.get("https://www.bing.com/translator");
		    bingui txtObj= new bingui();
		    System.out.println(txtObj.translateText(driver, from, to, text));
		    driver.findElement(By.cssSelector("#srcText")).clear();
			System.out.println(txtObj.autotextConversion(driver, to, text));
		}
	

	}


