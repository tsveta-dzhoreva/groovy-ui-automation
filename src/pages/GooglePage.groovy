package pages

import geb.navigator.Navigator

class GooglePage extends AbstractPage {

    static url = '/'

    static at = {
        //title.contains(TITLE)
        googleLogo.displayed
    }

    static content = {
        googleLogo { $("#hplogo") }
        searchBox {$("input", name: "q")}
        searchButton {
            Navigator navigator = $("form", name: "f").find(name: "btnK", type: "submit", "input")

            if (navigator.size() == 1) {
                return navigator.singleElement()
            } else if (navigator.size() > 1) {
                return navigator.lastElement()
            }
            throw new RuntimeException("Element not found")
        }

        searchResultsContainer { $('#sbfrm_l') }

        searchResults { $('div.r').find('h3') }

        firstResult { searchResults[0] }
    }
}
