package testAbv;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class MyProviderData {
	

	@DataProvider(name="invalidData")
	public static Object[][] getData(Method m){
		
		Object[][] data = null;
		
		if(m.getName().equals("checkTelField")){
		  data = new Object[][] {
			{"1234567894"},     //validate error M up of 9n = 10n
			{"hi"},          //validate error M with String
			{"e23456789"},   // you can insert char="e" in field telefon but you cannot save it
			{" "}            // validate SPACE
		  };
		
		}else if(m.getName().equals("invalidRegistrationIndex")){
			
			data = new Object[][]{
				{"j5","12Tt%6&","1*","8","p1","s9"},  //pass with 7ch
				{"j","1","1","12345678","?","d+"}   //tel with 8
				
			};
			
		}else if(m.getName().equals("checkAbvUserField")) {
			data = new Object[][] {
				{"k   "},        // take different err M,data = k and 3space
				{"здр"}
			};
			
		}
		return data;
		
		
	}
	
	
	
	
	@DataProvider(name="invalidData2")
	public Object[][]myData2(){
		return new Object[][]
				{
			{""},
			{"j"}
		};
		}
	

	
}
