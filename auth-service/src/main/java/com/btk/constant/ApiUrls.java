package com.btk.constant;

public class ApiUrls {

    public static final String VERSION = "api/v1";
    public static final String AUTH = VERSION + "/auth";

    //AuthController
    public static final String REGISTER_USER = "/register-user";
    public static final String REGISTER_SITE_MANAGER = "/register-site-manager/{token}";
    public static final String LOGIN = "/login";
    public static final String UPDATE = "/update";
    public static final String DELETE_BY_ID = "/delete-by-id";
    public static final String FIND_BY_ID = "/find-by-id";
    public static final String FIND_ALL = "/find-all";
    public static final String ACTIVATE_STATUS = "/activate-status";
    public static final String PASSWORD_CHANGE = "/password-change";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String FORGOT_PASSWORD_REQUEST = "/forgot-password-request";

    public static final String CONFIRM_ACCOUNT = "/confirm-account";

}
