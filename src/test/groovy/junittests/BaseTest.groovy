package junittests

import geb.junit4.GebTest
import groovy.util.logging.Log4j
import org.junit.Before


@Log4j
abstract class BaseTest extends GebTest {

    @Before
    void beforeBaseTest() {
        browser.driver.manage().window().maximize()
    }

}