package api.endpoints;

public class Routes {

    //User Module
    public static String BASE_URL = "https://gorest.co.in/public/v2";
    public static String USER_POST_URL = BASE_URL + "/users";
    public static String USER_GET_URL = BASE_URL + "/users/{id}";
    public static String USER_PUT_URL = BASE_URL + "/users/{id}";
    public static String USER_DELETE_URL = BASE_URL + "/users/{id}";

    //Posts Module
    public static String POSTS_GET_URL = BASE_URL + "/users/{id}/posts";
    public static String POSTS_POST_URL = BASE_URL + "/users/{id}/posts";

    public static String TOKEN = "94d2d09ade4ecf893bfe8c282a15a8f188a4fa3c4d61c7d14b426adfd2e09dff";

}
