import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import rest.RestHelper;


public class PostRequestTest {
    private static final String STATUS_CODE_201 = "201";
    private static final String STATUS_CODE_400 = "400";
    private RestHelper.RequestTypes POST = RestHelper.RequestTypes.POST;
    private RestHelper restHelper = new RestHelper();

    @Test
    @DisplayName("Create a user")
    @Description("Send a post request to create a user")
    public void createUser() {
        restHelper.genRandomNameJob();
        restHelper.sendRequest(POST, "/api/users");

        restHelper.assertStatusCode(STATUS_CODE_201);
        restHelper.compareReqResParam("name");
        restHelper.compareReqResParam("job");
        restHelper.isParamInResp("id");
        restHelper.isParamInResp("createdAt");
    }

    @Test
    @DisplayName("Registration test. Successful request")
    @Description("Send a post request to register")
    public void registerSuccessful() {
        restHelper.genRandomLoginPass();
        restHelper.sendRequest(POST, "/api/register");
        restHelper.assertStatusCode(STATUS_CODE_201);
        restHelper.isParamInResp("token");
    }

    @Test
    @DisplayName("Login test. Failed request")
    @Description("Send a post request to login")
    public void loginFailed() {
        restHelper.genRandomLogin();
        restHelper.sendRequest(POST, "/api/register");
        restHelper.assertStatusCode(STATUS_CODE_400);
        restHelper.isParamInResp("error");
        restHelper.checkParamValue("error", "Missing password");
    }

}
