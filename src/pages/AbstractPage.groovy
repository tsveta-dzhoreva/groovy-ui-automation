package pages

import geb.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class AbstractPage extends Page {

    void waitForAjax() {
        waitFor('ajaxSubmissionSuccessElements') {
            browser.driver.executeScript("return jQuery.active == 0;") == true
        }
    }

    void moveToElement(String id) {
        WebElement element = driver.findElement(By.id(id))
        Actions actions = new Actions(driver)
        actions.moveToElement(element)
        actions.perform()
    }

}
