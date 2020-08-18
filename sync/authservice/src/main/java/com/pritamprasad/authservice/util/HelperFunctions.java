package com.pritamprasad.authservice.util;

import com.pritamprasad.authservice.models.User;

import java.util.UUID;

public class HelperFunctions {

    public static String generateNewToken() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }

    public static User maskSecret(User user) {
        user.setPassword("****");
        return user;
    }
}
