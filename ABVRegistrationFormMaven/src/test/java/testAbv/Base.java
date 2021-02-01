package testAbv;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

public class Base {
	
	public static Properties prop = new Properties();
	public static WebDriver driver;
	public static JavascriptExecutor js;
	
	@BeforeTest
	public void setUp() throws IOException {

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\resources\\chromedriver3.exe");
		driver = new ChromeDriver();
		/*
		 * I take data from myData.properties
		 */

		FileInputStream myFile = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\resources\\myData.properties");
		prop.load(myFile);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		for (Cookie cook : driver.manage().getCookies()) {
			String writeup = cook.getName();
			driver.manage().deleteCookie(cook);
		}

		js = (JavascriptExecutor) driver;
	}

}
