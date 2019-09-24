package config

import geb.BuildAdapter

class GebConfigurationAdapter implements BuildAdapter {

    String getBaseUrl() {
        'https://www.google.com/'
    }

    File getReportsDir() {
        new File(System.properties['geb.build.reportsDir'])
    }

}