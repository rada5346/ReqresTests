import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import rest.RestHelper;


public class DeleteRequestTest {
    private static final String STATUS_CODE_204 = "204";
    private RestHelper.RequestTypes DELETE = RestHelper.RequestTypes.DELETE;
    private RestHelper restHelper = new RestHelper();

    @Test
    @DisplayName("Delete a user")
    @Description("Send a request to delete an existing user")
    public void getDeleteUser() {
        restHelper.sendRequest(DELETE, "/api/users/2");
        restHelper.assertStatusCode(STATUS_CODE_204);
    }
}
