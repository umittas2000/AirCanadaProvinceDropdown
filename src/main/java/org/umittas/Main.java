package org.umittas;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Sample expected data to compare with actual
        List<String> ExpectedProvinces = new ArrayList<String>();
        ExpectedProvinces.add("Alberta");
        ExpectedProvinces.add("British Columbia");
        ExpectedProvinces.add("Manitoba");
        ExpectedProvinces.add("New Brunswick");
        ExpectedProvinces.add("Newfoundland and Labrador");


        WebDriver driver = new ChromeDriver();
        driver.get("https://www.aircanada.com/ca/en/aco/home.html");

        Thread.sleep(3000);
        driver.manage().window().fullscreen();

        driver.findElement(By.id("bkmg-tab-button-flightPasses")).click();
        Thread.sleep(3000);

        //This part is tricky, direct clicking on select web element did not work, there is a parent tag which is <abc-select> and it's handling onClick event.
        driver.findElement(By.xpath("//abc-select[contains(@class,'bkmg-flight-passes-province')]")).click();
        Thread.sleep(3000);

        //xpath siblings technique used for nested web elements
        List<WebElement> listItems = driver.findElements(By.xpath("//ul[@role='listbox']  //li[contains(@class,'abc-form-element-option')]"));

        for(int i=1;i<=listItems.size();i++){
            //Xpath and dynamic iteration used
            WebElement item = driver.findElement(By.xpath(("(//ul[@role='listbox'] //div[@class='abc-form-element-option-text ng-star-inserted'])["+i+"]")));
            if(ExpectedProvinces.contains(item.getText())){
                System.out.println(i+". "+ item.getText() + " | Passed");
            }else {
                System.out.println(i + ". " + item.getText() + " | Failed");
            }
        }

        driver.close(); //Closing the tab only, if you work on multiple tabs, you can use driver.quit
    }
}