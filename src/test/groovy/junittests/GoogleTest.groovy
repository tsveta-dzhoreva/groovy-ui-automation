package junittests

import groovy.util.logging.Log4j
import pages.GooglePage
import org.junit.Test

@Log4j
class GoogleTest extends BaseTest {

    @Test
    void searchGoogleTest() {
        def googlePage = to GooglePage
        googlePage.searchBox.value('Geb Framework')
        googlePage.searchButton.click()
        waitFor { googlePage.searchResultsContainer.displayed }
        assert googlePage.title.startsWith('Geb Framework - Google')
        assert googlePage.firstResult.text() == 'Geb - Very Groovy Browser Automation'
    }

}
