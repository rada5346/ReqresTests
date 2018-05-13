import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import rest.RestHelper;


public class GetRequestTest {
    private static final String STATUS_CODE_200 = "200";
    private static final String STATUS_CODE_404 = "404";
    private static final RestHelper.RequestTypes GET = RestHelper.RequestTypes.GET;
    private RestHelper restHelper = new RestHelper();


    @Test
    @DisplayName("Get a single user")
    @Description("Send a request to get an existing user")
    public void getSingleUser() {
        restHelper.sendRequest(GET, "/api/users/2");
        restHelper.assertStatusCode(STATUS_CODE_200);
        restHelper.isParamInResp("data.id");
        restHelper.isParamInResp("data.first_name");
        restHelper.isParamInResp("data.last_name");
        restHelper.isParamInResp("data.avatar");
    }


    @Test
    @DisplayName("Get list of users")
    @Description("Send request to get a list of users")
    public void getUsers() {
        restHelper.sendRequest(GET, "/api/users");
        restHelper.assertStatusCode(STATUS_CODE_200);
        restHelper.checkParamCount("data.id", 3);
    }


    @Test
    @DisplayName("Get a nonexistent resource")
    @Description("Send request to get status code = 404")
    public void getResource() {
        restHelper.sendRequest(GET, "/api/unknown/23");
        restHelper.assertStatusCode(STATUS_CODE_404);
    }
}
