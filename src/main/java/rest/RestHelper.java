package rest;

import common.ConfigProperties;
import io.qameta.allure.Step;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class RestHelper {
    private ConfigProperties props = new ConfigProperties();

    private RequestSpecification request = RestAssured.given().baseUri(props.URL).filter(new AllureRestAssured());
    private Response response = null;
    private JSONObject requestBody = new JSONObject();
    private String someRandomString = String.format("%1$TH%1$TM%1$TS", new Date());
    private static final Logger log = Logger.getLogger(RestHelper.class.getName());


    public enum RequestTypes {
        POST, GET, PUT, PATCH, DELETE
    }

    // -------- Requests --------
    @Step("Send {0} request")
    public void sendRequest(RequestTypes requestType, String path) {
        request.contentType(ContentType.JSON).body(requestBody.toString());
        log.info(String.format("Send %s request %s. Path is %s", requestType.toString(), requestBody.toString(), path));

        switch (requestType) {
            case POST:
                response = request.post(path);
                break;
            case GET:
                response = request.get(path);
                break;
            case PUT:
                response = request.put(path);
                break;
            case PATCH:
                response = request.patch(path);
                break;
            case DELETE:
                response = request.delete(path);
                break;
        }

        log.info("Response is " + response.getBody().asString());
    }


    // -------- Data Generation --------
    @Step("Generate a request with random name and job")
    public void genRandomNameJob() {
        requestBody.put("name", "name" + someRandomString);
        requestBody.put("job", "job" + someRandomString);
    }


    @Step("Generate a request with random login and password")
    public void genRandomLoginPass() {
        requestBody.put("email", "email" + someRandomString);
        requestBody.put("password", "pass" + someRandomString);
    }

    @Step("Generate a request with random login")
    public void genRandomLogin() {
        requestBody.put("email", "email" + someRandomString);
    }

    // -------- Asserts --------
    @Step("Check that the status code is {0}")
    public void assertStatusCode(String statusCode) {
        log.info("Check status code " + statusCode);
        assertEquals("Assert status code ", String.valueOf(response.getStatusCode()), statusCode);
    }

    @Step("Check that the {0} in the response and request is the same")
    public void compareReqResParam(String param) {
        log.info(String.format("Check that the %s in the response and request is the same", param));
        assertEquals("Assert the user name", requestBody.get(param), getParamFromResponse(param));
    }

    @Step("Check that '{0}' is in the response")
    public void isParamInResp(String parameter) {
        log.info(String.format("Check that '%s' is in the response", parameter));
        assertNotNull(getParamFromResponse(parameter));
    }

    @Step("Check that {0} has value {1}")
    public void checkParamValue(String param, String expectedValue) {
        log.info(String.format("Check that %s has value %s", param, expectedValue));
        assertEquals("Assert value of " + param, expectedValue, getParamFromResponse(param));
    }

    @Step("Check that in response the count of {0} is {1}")
    public void checkParamCount(String param, int expectedCount){
        log.info(String.format("Check that in response the count of %s is %d", param, expectedCount));
        assertEquals("Assert params count ", expectedCount, getCountOfParams(param));
    }

    private int getCountOfParams(String param){
        return response.jsonPath().getList(param).size();
    }

    private String getParamFromResponse(String param) {
        return response.jsonPath().getString(param);
    }
}
