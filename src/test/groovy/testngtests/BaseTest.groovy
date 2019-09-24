package testngtests

import geb.testng.GebTestTrait
import groovy.util.logging.Log4j
import org.testng.annotations.BeforeTest

@Log4j
abstract class BaseTest implements GebTestTrait {

    @BeforeTest
    void beforeBaseTest() {
        browser.driver.manage().window().maximize()
    }


}