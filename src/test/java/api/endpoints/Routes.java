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

    public static String TOKEN = "4792779a7752b803e0736ede9a800ade3edfdadbdf9072c8d72f0ec9016fea8a";

}
