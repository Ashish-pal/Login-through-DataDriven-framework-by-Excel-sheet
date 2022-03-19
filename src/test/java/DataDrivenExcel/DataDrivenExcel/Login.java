package DataDrivenExcel.DataDrivenExcel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {

	static ExcelOperations excel = new ExcelOperations();
	static String excelFilePath = Constants.TestData_FILEPATH + Constants.TestData_FILENAME;
	static WebDriver driver;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void loginTest() throws InterruptedException, IOException {

		driver.get(Constants.URL);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		excel.setExcelFile(excelFilePath, "Sheet2");

		for (int i = 1; i <= excel.getRowCountInSheet(); i++) {
			WebElement workEmail = driver.findElement(By.id("login1"));
			workEmail.clear();
			workEmail.sendKeys(excel.getCellData(i, 0));

			WebElement passWord = driver.findElement(By.id("password"));
			passWord.clear();
			passWord.sendKeys(excel.getCellData(i, 1));

			WebElement createAccountButton = driver.findElement(By.name("proceed"));
			createAccountButton.click();

			String title = driver.getTitle();
			if (title.equals("Rediffmail")) {
				excel.setCellValue(i, 2, "Passed", excelFilePath);
			} else {
				excel.setCellValue(i, 2, "Failed", excelFilePath);
			}
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}