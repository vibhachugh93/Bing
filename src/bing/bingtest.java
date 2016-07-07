package bing;


     import java.io.IOException;
	 import java.util.List;
	 import java.util.Map;

	 import javax.xml.parsers.ParserConfigurationException;

	 import org.json.simple.parser.ParseException;
	 import org.junit.AfterClass;
	 import org.openqa.selenium.By;
	 import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
	 import org.testng.annotations.BeforeClass;
	 import org.testng.annotations.DataProvider;
	 import org.testng.annotations.Test;
	 import org.xml.sax.SAXException;

	 public class bingtest {
	 	static WebDriver driver;
	 	static AdmAuthentication authToken;
	 	static List<String> lines;
	 	static AdmAccessToken token;
	 	static bingui txtObj;
	 	static Map<String,String> mapObj;
	 	public static String getLanguageCodeFromMap(Map<String,String> mapObj,String str){
	 		return (String)mapObj.get(str);
	 	}
	 	
	 	@DataProvider(name="linesObjects")
	 	public static Object[][] getLines(){
	 		int size= lines.size();
	 		Object[][] lineObjects= new Object[size][1];
	 		for(int i=0;i<size;i++){
	 			lineObjects[i][0]= lines.get(i);
	 		}
	 		return lineObjects;
	 	}
	   @Test(dataProvider="linesObjects")
	   public void testBingTranslator(String line)  {
	 		String text = null;
	 		String from = null;
	 		String to = null;
	 	    String APIText=null;
	 	    String UIText=null;
	 	    String[] problem= line.split(",");
	 	    from= problem[0];
	 	    to= problem[1];
	 	    text= problem[2];
	 	    try {
	 	    	    	
	 	    	driver.findElement(By.cssSelector("#srcText")).clear(); 
	 	  
	 	    	
	 	    	if(from==null || from.equals("")){
	 	    	
	 	    		 
	 	    		 to= getLanguageCodeFromMap(mapObj, to);
	 	    		 if(to==null ){
	 	    			 return;
	 	    		 }
	 	    	    APIText=authToken.autodetectTranslation(to, text);
	 	    	   
	 			    try {
	 					UIText= txtObj.autotextConversion(driver, to, text);
	 					
	 				} catch (InterruptedException e) {
	 					
	 					e.printStackTrace();
	 				}
	 			 //   System.out.println(APIText+" "+UIText);
	 			}
	 			else{
	 			
	 				      from = getLanguageCodeFromMap(mapObj, from);
	 				      to= getLanguageCodeFromMap(mapObj, to);
	 				      if(from==null || to==null){
	 				    	  return;
	 				      }
	 					  APIText=authToken.translateText(from, to, text);
	 					  try {
	 						UIText= txtObj.translateText(driver, from, to, text);
	 					} catch (InterruptedException e) {
	 					
	 						e.printStackTrace();
	 					}
	 					//  System.out.println(APIText+"  "+UIText);
	 			}
	 			    		   
	 		} catch (IOException e) {
	 						
	 		    	e.printStackTrace();
	 		} catch (ParserConfigurationException e) {
	 						
	 				e.printStackTrace();
	 		} catch (SAXException e) {
	 						
	 				e.printStackTrace();
	 		}
	 	    
	 	    Assert.assertEquals(APIText, UIText);
	    }
	   @BeforeClass
	   public void beforeClass() throws IOException,ParseException {
	 	  String clientId="testNGDemoClient";
	 	  String clientSecret="jexBmJlYO56nXox8O0kcEiOEF9/M+ogSHOxgQ9q35sk=";
	 	  authToken= new AdmAuthentication(clientId, clientSecret);
	 	  mapObj= authToken.getLanguageCodeMap();
	 	  lines= authToken.getLinesFromFile(AdmAuthentication.txtFilePath);
	 	  token=	authToken.getAccessTokenUsingPost(AdmAuthentication.DatamarketAccessUri, authToken.getRequest());
	 	 String downloadDir = System.getProperty("user.home") + "//Downloads";
	 	  System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
	 	
		 driver = new ChromeDriver();
	 	  driver.get("https://www.bing.com/translator");
	 	  txtObj= new bingui();
	   }

	   @AfterClass
	   public void afterClass(){
	 	  driver.close();
	   }
	 
}
