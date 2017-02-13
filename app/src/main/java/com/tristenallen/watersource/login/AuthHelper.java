package com.tristenallen.watersource.login;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tristen on 2/13/17.
 */
public class AuthHelper {
    private static final AuthHelper instance = new AuthHelper();
    private Map<String, Integer> userIDs;
    private Map<Integer, String> passwords;

    private AuthHelper() {
        userIDs = new HashMap<>();
        userIDs.put("root", 0);
        passwords = new HashMap<>();
        passwords.put(0, "password");
    }

    public AuthHelper getInstance() {
        return instance;
    }

    public AuthPackage login(String username, String password) {
        if (!userIDs.containsKey(username)) {
            return new AuthPackage(-1, username, AuthStatus.INVALID_NAME);
        } else {
            int id = userIDs.get(username);
            String validPass = passwords.get(id);

            if (password.equals(validPass)) {
                return new AuthPackage(id, username, AuthStatus.VALID_LOGIN);
            } else {
                return new AuthPackage(id, username, AuthStatus.INVALID_PASSWORD);
            }
        }
    }
}
