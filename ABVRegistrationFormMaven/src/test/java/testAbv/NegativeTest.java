package testAbv;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NegativeTest extends Base{
	static WebElement user, passW, confPass, telNum, firName, lasName, errMTelefon_empty;



	@BeforeClass
	public void openRegistration() {
		WebElement registrLink = driver.findElement(By.cssSelector("form#loginForm strong"));
		js.executeScript("arguments[0].click()", registrLink);
		WebElement tittle = driver.findElement(By.xpath("//h2[contains(text(),'Създаване на АБВ Профил')]"));
		String pageTittle = tittle.getText();
		Assert.assertEquals(pageTittle, "Създаване на АБВ Профил");
		clickCreateProf();

	}

	@Test(priority = 1, enabled = true)
	public void emptyFieldValidation() {
		/*
		 * user error message empty data
		 */
		WebElement emptUserErrM = driver.findElement(By.xpath("(//div[contains(@class,'abv-row x')]//div)[3]")); // problem
																													// with
																													// element!!!!
		String eMForEmpty = emptUserErrM.getText();
		Assert.assertEquals(eMForEmpty, "Моля попълнете полето АБВ потребител.");

		/*
		 * pass error massage empty data
		 */
		WebElement emptyPassEM = driver.findElement(By.xpath("(//div[@class='abv-row x']//div)[2]"));
		String emptyPassEMTxt = emptyPassEM.getText();
		Assert.assertEquals(emptyPassEMTxt, "Моля попълнете полето Парола.");
		/*
		 * cornfPass error M with empty data
		 */
		WebElement emptyConfPass = driver
				.findElement(By.cssSelector("form#regform>div:nth-of-type(4)>div:nth-of-type(2)"));
		String emptyConfPEM = emptyConfPass.getText();
		Assert.assertEquals(emptyConfPEM, "Моля попълнете полето Повтори парола.");
		/*
		 * telefon errM with empty data
		 */
		errMTelefon_empty = driver.findElement(By.xpath("//div[@class='abv-phone']/following-sibling::div[1]"));
		String emptyTel = errMTelefon_empty.getText();
		Assert.assertEquals(emptyTel, "Моля попълнете полето Телефон.");

		/*
		 * name errM with empty data
		 */
		WebElement errMName_1 = driver.findElement(By.cssSelector(".x:nth-child(10) .abv-messageGray"));
		String emptyName = errMName_1.getText();
		Assert.assertEquals(emptyName, "Моля попълнете полето Име.");
		/*
		 * LastName errM with empty data
		 */
		WebElement errMLastN_1 = driver.findElement(By.xpath("//*[@id=\"regform\"]/div[9]/div[2]"));
		String emptyLName = errMLastN_1.getText();
		Assert.assertEquals(emptyLName, "Моля попълнете полето Фамилия.");

		/*
		 * AntiSpam err M
		 */
		WebElement spamCheck = driver.findElement(By.cssSelector(".x:nth-child(14) .abv-messageGray"));
		String spamTxt = spamCheck.getText();
		Assert.assertEquals(spamTxt, "Моля попълнете полето Антиспам верификация.");
	}

	@Test(priority = 2, dataProvider = "invalidData", dataProviderClass = MyProviderData.class, enabled = true)
	public void checkTelField(String telefon) {
		/*
		 * error M with String data
		 */
		WebElement telNum = driver.findElement(By.xpath(prop.getProperty("telefoneNum")));
		telNum.sendKeys(telefon);
		int telIndex = countData(telefon);
		if (telIndex > 9) {
			clickCreateProf();
			WebElement TelErM = driver.findElement(By.xpath("//form[@id='regform']/div[6]/div[2]"));
			String invalidTelErM = TelErM.getText();
			Assert.assertEquals(invalidTelErM, "Полето Телефон не може да съдържа повече от 9 символа.");
			telNum.clear();
		} else {
			clickCreateProf();
			errMTelefon_empty = driver.findElement(By.xpath("//div[@class='abv-phone']/following-sibling::div[1]"));
			String stringTel = errMTelefon_empty.getText();
			Assert.assertEquals(stringTel, "Моля попълнете полето Телефон.");
			telNum.clear();
		}
	}

	@Test(priority = 3, dataProvider = "invalidData", dataProviderClass = MyProviderData.class, enabled = true)
	public void checkAbvUserField(String abvUser) {
		user = driver.findElement(By.id(prop.getProperty("abvUser")));
		user.sendKeys(abvUser);
		clickCreateProf();

		WebElement invalidUser = driver.findElement(By.xpath("(//div[contains(@class,'abv-row x')]//div)[3]"));
		String invalidUserErrM = invalidUser.getText();
		Assert.assertEquals(invalidUserErrM, "Полето АБВ потребител е попълнено некоректно.");
		user.clear();
	}

	@Test(priority = 4, dataProvider = "invalidData", dataProviderClass = MyProviderData.class, enabled = true)
	public void invalidRegistrationIndex(String userName, String pass, String confirmPass, String telefon, String fName,
			String LName) throws Exception {
		/*
		 * insert data
		 */
		user = driver.findElement(By.id(prop.getProperty("abvUser")));
		user.sendKeys(userName);
		passW = driver.findElement(By.xpath(prop.getProperty("password")));
		passW.sendKeys(pass);
		confPass = driver.findElement(By.xpath(prop.getProperty("password2")));
		confPass.sendKeys(confirmPass);
		telNum = driver.findElement(By.xpath(prop.getProperty("telefoneNum")));
		telNum.sendKeys(telefon);
		firName = driver.findElement(By.xpath(prop.getProperty("firstName")));
		firName.sendKeys(fName);
		lasName = driver.findElement(By.xpath(prop.getProperty("lastName")));
		lasName.sendKeys(LName);
		WebElement submitBut = driver.findElement(By.xpath("(//input[@type='submit'])[2]"));
		js.executeScript("arguments[0].click()", submitBut);

		/*
		 * /verify string index for name field
		 */

		int userN = countData(userName);

		if (userN == 1 || userN == 2) {
			WebElement messageUser = driver
					.findElement(By.cssSelector(".abv-domain.abv-row.x > .abv-error.abv-messageGray"));
			String errMUser = messageUser.getText();
			Assert.assertEquals(errMUser, "Полето АБВ потребител не може да съдържа по-малко от 3 символа.");
		}

		/*
		 * cannot use Bulgarian language!!! Handle with: Window > Preferences > General
		 * > Workspace, set Text file encoding to Other : UTF-8
		 */

		/*
		 * verify string index for pass error message
		 */

		int passIndex = countData(pass);
		if (passIndex == 1 | passIndex <= 7) {
			WebElement message2 = driver
					.findElement(By.xpath("//div[text()='Полето Парола не може да съдържа по-малко от 8 символа.']"));
			String errMPass = message2.getText();
			Assert.assertEquals(errMPass, "Полето Парола не може да съдържа по-малко от 8 символа.");
		}

		/*
		 * /veryfy string index for confirm pass field
		 */

		int passIndexConf = countData(confirmPass);

		if (passIndexConf == 1 || passIndexConf <= 7) {
			WebElement messageConfP = driver.findElement(By.xpath("//form[@id='regform']/div[4]/div[2]"));
			String errConfPass = messageConfP.getText();
			Assert.assertEquals(errConfPass, "Полето Повтори парола не може да съдържа по-малко от 8 символа.");
		}

		/*
		 * verify insert index for telefon field
		 */

		int telIndex = countData(telefon);
		if (telIndex == 1 || telIndex <= 8) {
			WebElement errMTelefon = driver
					.findElement(By.cssSelector("form#regform>div:nth-of-type(6)>div:nth-of-type(2)"));
			String telefonM = errMTelefon.getText();
			Assert.assertEquals(telefonM, "Полето Телефон не може да съдържа по-малко от 9 символа.");
		}

		/*
		 * verify insert index for name field
		 */

		int nameIndex = countData(fName);
		if (nameIndex == 1 || nameIndex == 2) {
			WebElement errMName = driver.findElement(By.cssSelector(".x:nth-child(10) .abv-messageGray"));
			String MName = errMName.getText();
			Assert.assertEquals(MName, "Полето Име не може да съдържа по-малко от 3 символа.");
		}

		/*
		 * verify insert for lastName field
		 */

		int lastNIndex = countData(LName);
		if (lastNIndex == 1 || lastNIndex == 2) {
			WebElement errMLastN = driver.findElement(By.xpath("//*[@id=\"regform\"]/div[9]/div[2]"));

			String MLastN = errMLastN.getText();
			Assert.assertEquals(MLastN, "Полето Фамилия не може да съдържа по-малко от 3 символа.");
		}
		user.clear();
		passW.clear();
		confPass.clear();
		telNum.clear();
		firName.clear();
		lasName.clear();
		driver.navigate().refresh();

	}

	public void clickCreateProf() {
		WebElement submitBut = driver.findElement(By.xpath("(//input[@type='submit'])[2]"));
		js.executeScript("arguments[0].click()", submitBut);
	}

	public int countData(String insertData) {
		int count = 0;

		// Counts each character except space
		for (int i = 0; i < insertData.length(); i++) {
			if (insertData.charAt(i) != ' ')
				count++;
		}
		return count;

	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();

	}
}
