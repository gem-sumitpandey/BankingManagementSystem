package com.assignment.bankManagementSystem.utils;

import java.util.UUID;

public class AccountUtils {
    public static String generateRandomAccountNumber() {

        String uuid = UUID.randomUUID().toString();


        uuid = uuid.replace("-", "").toLowerCase();


        String randomString = uuid.substring(0, 10);

        randomString = randomString.replace('a', '1');
        randomString = randomString.replace('b', '2');

        return randomString;
    }

}
