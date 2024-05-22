package bindings;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This class contains all the methods that will glue with the Cucumber files.
 */
public class seleniumMethods {
    WebDriver driver;


    /**
     * A method for checking the visibility of a specific element on a page.
     *
     * @param xpath string of the element.
     * @return true or false depending on the visibility of the element.
     */
    public boolean isElementVisible(String xpath) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }


    /**
     * A method for clicking the login button.
     *
     * @throws Throwable if the element is not found.
     */
    @When("^i click login$")
    public void i_click_login() throws Throwable {
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[3]/div/label")).click();
    }


    /**
     * A final step method that checks if the user is unable to login.
     *
     * @throws Throwable if the elements are not found.
     */
    @Then("^i can not login$")
    public void i_can_not_login() throws Throwable {
        assertTrue(driver.findElement(By.xpath("/html/body/div[2]/div/div[2]")).isDisplayed());
        driver.findElement(By.xpath("/html/body/div[2]/div/div[6]/button[1]")).click();
        driver.quit();
    }


    /**
     * A method for filling the login form.
     *
     * @param arg can be correct or incorrect depending on the test.
     */
    @And("^i enter the \"([^\"]*)\" credentials$")
    public void iEnterTheCorrectCredentials(String arg) {

        switch (arg) {
            case "correct":
                driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[2]/input")).sendKeys("admin");
                driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[1]/input")).sendKeys("admin");
                break;
            case "incorrect":
                driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[2]/input")).sendKeys("admin");
                driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[1]/input")).sendKeys("inccorectpassword");
                break;

        }

        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/form/div[3]/div/label")).click();
    }


    /**
     * A method for checking if the user is fully logged in.
     */
    @Then("^i successfully logged in$")
    public void iSuccessfullyLoggedIn() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/div[1]")));
        System.out.println(driver.getTitle() + "          " + driver.getCurrentUrl());
        assertTrue(driver.getTitle().equals("BTA | Data") && driver.getCurrentUrl().equals("http://localhost:8888/BTA/app/upload"));
        driver.quit();
    }


    /**
     * A method for checking if the server return th unauthorized response.
     */
    @Then("^i will be prompted with unauthorized$")
    public void iWillBePromptedWithUnauthorized() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement unauthorizedImg;
        unauthorizedImg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/h2[1]")));
        assertTrue(unauthorizedImg.getText().equals("Sorry, you do not have permission to view this page."));
        driver.quit();
    }


    /**
     * A method for ensuring that the I am on the specified page.
     *
     * @param url of the page that I am currently on.
     * @throws Throwable if elements are not found.
     */
    @Given("^i am on the \"([^\"]*)\" page$")
    public void iAmOnThePage(String url) throws Throwable {
        switch (url) {
            case "login":
                System.setProperty("webdriver.chrome.driver", "src/test/seleniumWebdriver/chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.get("http://localhost:8888/BTA/");
                driver.manage().window().maximize();
                break;
            case "upload":
//                WebDriverWait wait=new WebDriverWait(driver, 5);
//                System.out.println("Case upload:  " + driver.getCurrentUrl());
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/div[1]")));
                if (!driver.getCurrentUrl().equals("http://localhost:8888/BTA/app/upload")) {
                    driver.get("http://localhost:8888/BTA/app/upload");
                }
                break;
            case "data":
                if (!driver.getCurrentUrl().equals("http://localhost:8888/BTA/app/data/SWIFT_MT940_Standard_27May.940")) {
                    driver.get("http://localhost:8888/BTA/app/data/SWIFT_MT940_Standard_27May.940");
                }
                break;
            case "statistics":
                if (!driver.getCurrentUrl().equals("http://localhost:8888/BTA/app/data/SWIFT_MT940_Standard_27May.940/stats")) {
                    driver.get("http://localhost:8888/BTA/app/data/SWIFT_MT940_Standard_27May.940/stats");
                }
                break;
            default:
                String surl = "http://localhost:8888/BTA/app/" + url;
                driver.get(surl);
        }
    }


    /**
     * A method for uploading a file.
     *
     * @param arg0 is the name of the file.
     * @throws Throwable if elements are not found.
     */
    @When("^i upload \"([^\"]*)\" file$")
    public void iUploadFile(String arg0) throws Throwable {
        boolean isVisible = true;
        WebElement uploadbox = driver.findElement(By.xpath("//*[@id=\"file-upload\"]"));
        String path = System.getProperty("user.dir") + "\\src\\test\\java\\testFiles\\" + arg0;
        System.out.println(path);
        File file = new File(path);
        String filePath = file.getAbsolutePath();
        uploadbox.sendKeys(filePath);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/label")).click();

        if (isElementVisible("/html/body/div[2]/div/div[1]/span")) {
            driver.findElement(By.xpath("/html/body/div[2]/div/div[6]/button[1]")).click();
            driver.get("http://localhost:8888/BTA/app/data/" + arg0);
        }
    }


    /**
     * A method for checking if the data page of the file is correct.
     *
     * @param arg0 the name of the file of which data is shown.
     * @throws Throwable if elements are not found.
     */
    @Then("^i am prompted with data page of the \"([^\"]*)\" file$")
    public void iAmPromptedWithDataPageOfTheFile(String arg0) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"table-d\"]/tbody[19]")));
        if (!arg0.equals("data")) {
            assertTrue(driver.findElement(By.xpath("//*[@id=\"table-f\"]/tbody/tr/td[1]")).getText().equals(arg0));
            driver.quit();
        }
    }


    /**
     * A method for checking if the server responds with status code 404.
     */
    @Then("^i will be prompted with not found page$")
    public void iWillBePromptedWithNotFoundPage() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement notfound;
        notfound = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"notfound\"]/div/div/h2")));
        assertTrue(notfound.getText().equals("404 - The Page can't be found"));
        driver.quit();

    }


    /**
     * A method for deleting all the files in the database.
     */
    @And("^i click delete all button$")
    public void iClickDeleteAllButton() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        System.out.println("jaskdjklasjdklasjd" + driver.getCurrentUrl() + "          " + driver.getTitle());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[4]/div/label"))).click();

        WebElement deleteAll;
        deleteAll = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[6]/button[1]")));
        deleteAll.click();
    }


    /**
     * A method for checking if ALL files are actually deleted.
     */
    @Then("^all files will be deleted$")
    public void thenAllFilesWillBeDeleted() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement msg;
        msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[1]")));
        System.out.println(msg.getText());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"swal2-title\"]")).getText().equals("All files have been successfully deleted!"));
        driver.quit();
    }


    /**
     * A method for deleting a specific file.
     *
     * @param fileName is the name of the file.
     * @throws Throwable if the elements is not found.
     */
    @And("^i delete the \"([^\"]*)\" file$")
    public void iDeleteTheFile(String fileName) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement cross;
        cross = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[6]/img[2]")));
        cross.click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[6]/button[1]")).click();

    }


    /**
     * A method for checking if the specific file is deleted.
     *
     * @param fileName is the name of the file that should be deleted.
     * @throws Throwable if the element is not found.
     */
    @Then("^the \"([^\"]*)\" should be deleted$")
    public void thenTheFileShouldBeDeleted(String fileName) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[1]/div[2]")));
        assertTrue(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]")).getAttribute("class").contains("success"));
        driver.quit();
    }


    /**
     * A method for checking if I am logged in.
     */
    @Then("^i am logged in$")
    public void iAmLoggedIn() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/div[1]")));
        System.out.println(driver.getTitle() + "          " + driver.getCurrentUrl());
        assertTrue(driver.getTitle().equals("BTA | Data") && driver.getCurrentUrl().equals("http://localhost:8888/BTA/app/upload"));
    }


    /**
     * A method for clicking on a specific transaction.
     *
     * @param fileName is the name of the transaction
     * @throws Throwable if the element is not found.
     */
    @And("^i click on the \"([^\"]*)\" transaction$")
    public void iClickOnTheTransaction(String fileName) throws Throwable {
        driver.findElement(By.xpath("//*[@id=\"table-d\"]/tbody[1]")).click();

    }


    /**
     * A method for checking if the first transaction expands.
     */
    @Then("^the first transaction expands$")
    public void thenTheFirstTransactionExpands() {
        assertTrue(driver.findElement(By.xpath("//*[@id=\"table-d\"]/tbody[1]/tr[2]")).isDisplayed());
        driver.quit();
    }


    /**
     * A method for clicking on a specific button.
     *
     * @param buttonLabel is the label name of the button.
     * @throws Throwable if elements are not found.
     */
    @And("^i click on the \"([^\"]*)\" button$")
    public void iClickOnTheButton(String buttonLabel) throws Throwable {
        switch (buttonLabel) {
            case "statistics":
                driver.findElement(By.xpath("//*[@id=\"stats\"]")).click();
                break;
            case "Expand All":
                driver.findElement(By.xpath("//*[@id=\"filter-table\"]/button[2]")).click();
                break;
        }

    }


    /**
     * A method for checking if all the transactions expand.
     */
    @Then("^all transactions wil expand$")
    public void thenAllTransactionsWilExpand() {
        System.out.println("txt:: " + driver.findElement(By.xpath("//*[@id=\"filter-table\"]/button[2]")).getText());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"filter-table\"]/button[2]")).getText().equals("COLLAPSE ALL"));
        driver.quit();
    }


    /**
     * A method for inputting a keyword into the search box.
     *
     * @param keyword to search for.
     * @throws Throwable if element is not found.
     */
    @And("^i search for \"([^\"]*)\" keyword$")
    public void iSearchForKeyword(String keyword) throws Throwable {
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"filter-table\"]/input"));
        searchBox.sendKeys(keyword);
    }


    /**
     * A method for checking if the transactions are containing the specific keyword.
     *
     * @param arg0 is the keyword we are looking for.
     * @throws Throwable if elements are not found.
     */
    @Then("^transactions will contain the \"([^\"]*)\" keyword$")
    public void transactionsWillContainTheKeyword(String arg0) throws Throwable {
        assertTrue(driver.findElement(By.xpath("//*[@id=\"table-d\"]/tbody[1]/tr[1]/td[5]")).getText().contains(arg0.toUpperCase(Locale.ROOT)));
        assertTrue(driver.findElement(By.xpath("//*[@id=\"table-d\"]/tbody[2]/tr[1]/td[5]")).getText().contains(arg0.toUpperCase(Locale.ROOT)));
        driver.quit();
    }


    /**
     * A method for selecting a transaction using a specific date.
     *
     * @param arg0 the date of the selection.
     * @throws Throwable if elements are not found.
     */
    @And("^i select the transactions on \"([^\"]*)\"$")
    public void iSelectTheTransactionsOn(String arg0) throws Throwable {
        switch (arg0) {
            case "1 Apr 2014":
                driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/tr[1]")).click();
                break;
            case "1 May 2014":
                driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/tr[2]")).click();
                break;

            case "2 May 2016":
                driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/tr[3]")).click();
                break;
        }
    }


    /**
     * A method for checking if a selected transaction will turn blue.
     *
     * @param arg0 the table element to check.
     * @throws Throwable if element is not found.
     */
    @Then("^the selection \"([^\"]*)\" turn blue$")
    public void thenTheSelectionTurnBlue(String arg0) throws Throwable {
        WebElement selection = driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/" + arg0));
        assertTrue(selection.getAttribute("class").contains("selected"));
        driver.quit();
    }


    /**
     * A method for selecting the interval date of the transactions.
     *
     * @param arg0 is the startDate.
     * @param arg1 is the endDate.
     * @throws Throwable if elements are not found.
     */
    @And("^i input the  interval \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iInputTheIntervalAnd(String arg0, String arg1) throws Throwable {
        WebElement startDate = driver.findElement(By.xpath("//*[@id=\"start-date\"]"));
        WebElement endDate = driver.findElement(By.xpath("//*[@id=\"end-date\"]"));
        //Fill date as mm/dd/yyyy 1 Apr 2014 and 1 May 2014
        startDate.sendKeys("04012014");
        endDate.sendKeys("05012014");
    }


    /**
     * A method for selecting two distinct transactions.
     *
     * @param arg0 the table element of the first transaction.
     * @param arg1 the table element of the second transaction.
     * @throws Throwable if elements are not found.
     */
    @Then("^the selections \"([^\"]*)\" and \"([^\"]*)\" turn blue$")
    public void thenTheSelectionsAndTurnBlue(String arg0, String arg1) throws Throwable {
        WebElement selection1 = driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/" + arg0));
        WebElement selection2 = driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/" + arg1));
        assertTrue(selection1.getAttribute("class").contains("selected") && selection2.getAttribute("class").contains("selected"));

    }


    /**
     * A method for checking if the selection actually displays information about the selected transactions.
     *
     * @param arg0 the table element of the first transaction.
     * @param arg1 the table element of the second transaction.
     * @throws Throwable if element is not found.
     */
    @Then("^the selected table will display information about \"([^\"]*)\" and \"([^\"]*)\"  properly$")
    public void theSelectedTableWillDisplayInformationAboutAndProperly(String arg0, String arg1) throws Throwable {
        int trans1 = Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/" + arg0 + "/td[2]")).getText());
        int trans2 = Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"stats-table\"]/table/tbody/" + arg1 + "/td[2]")).getText());
        int totalTrans = Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"d2\"]")).getText());
        assertTrue(trans1 + trans2 == totalTrans);
        driver.quit();

    }
}
