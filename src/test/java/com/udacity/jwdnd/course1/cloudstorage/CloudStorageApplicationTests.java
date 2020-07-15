package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

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
        WebElement logoutForm = driver.findElement(By.cssSelector("#logoutDiv > form"));
        logoutForm.submit(); //The logout button is inside a form
        Thread.sleep(50);

        Assertions.assertEquals(true, driver.getCurrentUrl().contains("login?logout"));

        //Check home page is not available anymore
        getHomeWithoutLogin();

    }

    @Test
    public void createANote() throws InterruptedException {

        driver.get("http://localhost:" + this.port + "/login");
        Thread.sleep(100);
        WebElement username2 = driver.findElement(By.id("inputUsername"));
        username2.sendKeys("js");
        WebElement password2 = driver.findElement(By.id("inputPassword"));
        password2.sendKeys("123");
        password2.submit();
        Thread.sleep(500);

        //Select Note Tab
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        Actions builder = new Actions(driver);
        builder.moveToElement(noteTab).click(noteTab);
        builder.perform();
        Thread.sleep(200);

        //Click addNote Button
        WebElement addNoteButton = driver.findElement(By.cssSelector("#nav-notes > button"));
        addNoteButton.click();
        Thread.sleep(500);

        //Input two values in the input form
        WebElement noteTitleInput = driver.findElement(By.id("note-title"));
        noteTitleInput.sendKeys("NoteTile123abdcz");
        WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
        noteDescriptionInput.sendKeys("NoteDescriptio123abdcz Whatever");

        //Submitting the add note form
        noteDescriptionInput.submit();
        Thread.sleep(500);

        //Closing the confirmation message after the new note is created
        WebElement acceptModal = driver.findElement(By.id("errormodalbutton"));
        acceptModal.click();
        Thread.sleep(100);

        //Getting the last element of the note list
        //and extracting both values
        WebElement lastNoteRowNoteTitleInput = driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child >th:nth-child(2)"));
        WebElement lastNoteRowNoteDescrInput = driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child >td:nth-child(3)"));
        String lastNoteRowTitle = lastNoteRowNoteTitleInput.getText();
        String lastNoteRowNoteDescription = lastNoteRowNoteDescrInput.getText();


        Assertions.assertEquals("NoteTile123abdcz" + "NoteDescriptio123abdcz Whatever", lastNoteRowTitle + lastNoteRowNoteDescription);
    }

    @Test
    public void deleteANote() throws InterruptedException {

        //Login as Lionel Messi
        //This user has 2 Notes and 6 Ballon D'Ore
        driver.get("http://localhost:" + this.port + "/login");
        Thread.sleep(100);
        WebElement username2 = driver.findElement(By.id("inputUsername"));
        username2.sendKeys("lm");
        WebElement password2 = driver.findElement(By.id("inputPassword"));
        password2.sendKeys("789");
        password2.submit();
        Thread.sleep(500);

//
        //Select Note Tab
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        Actions builder = new Actions(driver);
        builder.moveToElement(noteTab).click(noteTab);
        builder.perform();
        Thread.sleep(200);

       // WebElement tbodyOfTable = driver.findElements (By.cssSelector("#userTable > tbody > tr"));
        List<WebElement> notes = driver.findElements(By.cssSelector("#userTable > tbody > tr"));
        int notesSizeBeforeDelete = notes.size();



        //Click delete Button in first row
        WebElement firstDeleteNoteButton = driver.findElement(By.cssSelector("#userTable > tbody > tr:nth-child(1) > td:nth-child(1) > a"));
        firstDeleteNoteButton.click();
        Thread.sleep(500);

        //Closing the confirmation Delete message after the new note is created
        WebElement acceptModal = driver.findElement(By.id("errormodalbutton"));
        acceptModal.click();
        Thread.sleep(100);



        notes = driver.findElements(By.cssSelector("#userTable > tbody > tr"));
        int notesSizeAfterDelete = notes.size();


        Assertions.assertEquals(notesSizeBeforeDelete - 1, notesSizeAfterDelete);
    }


}
