import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import rest.RestHelper;

public class UpdateRequestTest {
    private static final String STATUS_CODE_200 = "200";
    private RestHelper restHelper = new RestHelper();
    private static final RestHelper.RequestTypes PATCH = RestHelper.RequestTypes.PATCH;
    private static final RestHelper.RequestTypes PUT = RestHelper.RequestTypes.PUT;

    @Test
    @DisplayName("Update a user. Patch request")
    @Description("Send a patch request to update a user")
    public void patchUser() {
        restHelper.genRandomNameJob();
        restHelper.sendRequest(PATCH, "/api/users/23");
        restHelper.assertStatusCode(STATUS_CODE_200);
        restHelper.isParamInResp("updatedAt");
        restHelper.compareReqResParam("name");
        restHelper.compareReqResParam("job");

    }

    @Test
    @DisplayName("Update a login. Put request")
    @Description("Send a put request to update a login")
    public void putUser() {
        restHelper.genRandomLogin();
        restHelper.sendRequest(PUT, "/api/users/23");
        restHelper.assertStatusCode(STATUS_CODE_200);
        restHelper.isParamInResp("updatedAt");
        restHelper.compareReqResParam("email");
    }
}
