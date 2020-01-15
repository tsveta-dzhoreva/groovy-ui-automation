package restApi

import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.After
import io.restassured.RestAssured;
import org.json.simple.*
import org.junit.Test;

class RestTestsLucidLink {

    String baseURI = "https://webportal-be.lucidlink.com/api/v1/";
    String tokenEndpoint = "https://lucid-staging.auth.eu-central-1.amazoncognito.com/oauth2/token"
    String filespaceURI = baseURI + "/filespaces/";
    String fileSpaceId = ""

    @After
    void cleanup() {
        RequestSpecification request = this.getRequest();

        Response response = request.delete(this.getFilespaceURI());

        assert response.getStatusCode() == 204
    }

    @Test
    void testCreateFilespace(){
        Response response = this.createFilespace();
        assert response.getStatusCode() == 201
        this.fileSpaceId = response.path("id");

        RequestSpecification getRequest = this.getRequest();

        // Wait filespace to be provisioned
        String status = ""
        Integer timeToWait = 10000;
        Integer timeoutMax = 60000;
        Integer timeoutCurrent = 0;

        while (status != "provisioned" || timeoutCurrent < timeoutMax){
            Response getResponse = getRequest.get(this.getFilespaceURI())

            assert getResponse.getStatusCode() == 200

            String tempStatus = getResponse.path("status");

            if (status == "") {
                assert tempStatus == "provisioning"
            }

            status = tempStatus;
            timeoutCurrent += timeToWait;
        }

        assert status == "provisioned";
    }

    Response createFilespace() {
        RequestSpecification request = this.getRequest();

        JSONObject childData = new JSONObject();

        childData.put("owner", "lucidlink")
        childData.put("provider", "Wasabi")
        childData.put("region", "eu-central-1")
        childData.put("endpoint", "s3.eu-central-1.wasabisys.com")
        childData.put("bucket", "5dd93324-8352-4627-8725-6d4479ad3274")

        JSONObject parentData = new JSONObject();

        parentData.put("domain", "5e1dd59cf283197e79cbbcf4")
        parentData.put("name", "autotestname")
        parentData.put("storage", childData);

        request.body(parentData.toJSONString());

        Response response = request.post(filespaceURI);

        return response;
    }

    String getFilespaceURI() {
        return this.filespaceURI + this.fileSpaceId;
    }

    String getToken(){
        RequestSpecification request = RestAssured.given();
        request.headers("Content-Type", "application/x-www-form-urlencoded",
                "Authorization", "Basic Mjg1ZDQxOG1nM3RucmFlMGg2cTd1MTBtaHI6bjduaHU0b2YwZmQ0NjNtcnEzZGtycHJ0YTVuZmt0NDgyYWw3OGM4aXFybzRvZXU3b2cw");
        request.body("grant_type=client_credentials");

        Response response = request.post(tokenEndpoint);

        assert response.getStatusCode() == 200

        return response.path("access_token");
    }

    RequestSpecification getRequest() {
        RequestSpecification getRequest = RestAssured.given();
        getRequest.headers("Content-Type", "application/json",
                "Authorization", "Bearer " + getToken());

        return getRequest;
    }
}