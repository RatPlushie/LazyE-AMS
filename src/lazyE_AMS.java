import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Scanner;

public class lazyE_AMS {

    public static void main(String[] args) {

        // User variables
        String amsUserName;
        String amsPassword;
        String jobLocation;
        String jobRole;

        // Creating scanner
        Scanner scanner = new Scanner(System.in);

        // Asking the user for some info
        System.out.println("What city do you live in: ");
        jobLocation = scanner.nextLine();

        System.out.println("What job are you searching for: ");
        jobRole = scanner.nextLine();

        System.out.println("What is your ams Username: ");
        amsUserName = scanner.nextLine();

        System.out.println("What is your ams password: ");
        amsPassword = scanner.nextLine();

        // Stopping the scanner
        scanner.close();

        // Init webdriver
        WebDriver driver = new FirefoxDriver();

        // Loading of karrier.at website
        driver.get("https://www.karriere.at/");

        // Maximising page for user readability
        driver.manage().window().maximize();

        // Waiting to accept the cookies message
        System.out.println("Waiting to accept cookies...");
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[2]/div/button")));
        System.out.print(" Cookies accepted\n");

        // Clearing the cookie window
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/button")).click();

        // Searching for applicable jobs
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/form/div[1]/div[1]/div/input")).sendKeys(jobRole);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/form/div[2]/div[1]/div[1]/div/input")).sendKeys(jobLocation);

        // Searching for jobs
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/form/div[3]/button")).click();

        // Creating job listing array of objects
        Job[] mJobs = new Job[3];

        // Setting the objects values for each job
        for (int i = 0; i <= 2; i++){

            // TODO - This code works as intended when slowed down in debug, I cant seem to make it slow down in application. More work needed.

            // Making the string to search for the correct list item
            String xpathString = "/html/body/div[1]/div[2]/div/div[3]/div/div[1]/div[1]/div[1]/div[5]/ol/li[" + (i + 1) + "]/div/div/div[2]";

            // Selecting the job listing
            driver.findElement(By.xpath(xpathString)).click();

            // Waiting for the job listing to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/div/div[1]/div/div/div/div/div[1]/div/div/div[1]/ul/li[1]/div/a")));

            // Retrieving the relevant info for the job
            String role = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/div/div[1]/div/div/div/div/div[1]/div/div/div[1]/h1")).getText();
            String companyName = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div[2]/div/div[1]/div/div/div/div/div[1]/div/div/div[1]/ul/li[1]/div/a")).getText();

            // Populating the newly created job object
            mJobs[i] = new Job(companyName, role);
        }

        // DEBUG print out of jobs found
        for (int i = 0; i <= 2; i ++){
            mJobs[i].printJobs();
        }










/*      AMS AUTOMATION CODE -- WIP



        // Loading eAMS website
        driver.get("https://www.e-ams.at/eams-sfa-account/p/index.jsf");

        // Logging into the ams website with user credentials
        driver.findElement(By.id("j_username")).sendKeys(amsUserName);
        driver.findElement(By.id("j_password")).sendKeys(amsPassword);

        driver.findElement(By.id("submit")).click();

        driver.findElement(By.id("pnav:bewerbungsverlauf")).click();

        List<String> currentJobs = new ArrayList<>();

        // 10 fields to copy into a string array
        for (int i = 1; i <= 10; i++){
            // Creating the xpath string to look up
            String xpathString = "/html/body/div[1]/div[3]/div[3]/form[1]/div/div/fieldset/fieldset/table/tbody/tr[" + i + "]/td[2]";

            currentJobs.add(driver.findElement(By.xpath(xpathString)).getText());
        }

        // DEBUG printout
        for (String stringlist : currentJobs){
            System.out.println(stringlist);
        }

        // Closing the window
        //driver.quit();

        */
    }
}

class Job {

    String companyName;
    String jobRole;

    Job(String title, String role){
        companyName = title;
        jobRole = role;
    }

    void printJobs(){
        System.out.println(companyName + ", " + jobRole);
    }
}

