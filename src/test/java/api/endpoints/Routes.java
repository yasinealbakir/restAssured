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

    public static String TOKEN = "eee75e4f8cdf586516a588c7c1516000ae6162c5501aa86ecad1031fe6a64cd3";

}
