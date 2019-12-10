package com.pritamprasad.authservice.util;

import java.util.UUID;

public class HelperFunctions {

    public static String generateNewToken() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }
}
