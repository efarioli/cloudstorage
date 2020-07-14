package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(50);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getHomeWithoutLogin() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(50);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signUpNewUser() throws InterruptedException {
		//test that signs up a new user, logs that user in, verifies that they can access the home page,
		// then logs out and verifies that the home page is no longer accessible

		//Create new User
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement firstName = driver.findElement(By.id("inputFirstName"));
		firstName.sendKeys("test");
		WebElement lastName = driver.findElement(By.id("inputLastName"));
		lastName.sendKeys("testable");
		WebElement username = driver.findElement(By.id("inputUsername"));
		username.sendKeys("test");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("123");
		password.submit();
		Thread.sleep(50);

		//Login new User
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(50);

		WebElement username2 = driver.findElement(By.id("inputUsername"));
		username2.sendKeys("test");
		WebElement password2 = driver.findElement(By.id("inputPassword"));
		password2.sendKeys("123");
		password2.submit();
		Thread.sleep(50);
		Assertions.assertEquals("Home", driver.getTitle());

		//Logout the newly created User
		WebElement logoutBtn = driver.findElements(By.cssSelector("#logoutDiv > form > button")).get(0);
		System.out.println("00000000000000000000" +logoutBtn.getText());
		logoutBtn.sendKeys(Keys.RETURN); //intead of click
		Thread.sleep(100);
		Assertions.assertEquals(true, driver.getCurrentUrl().contains("login?logout"));

		//Check home page is not available anymore
		getHomeWithoutLogin();

	}

}
