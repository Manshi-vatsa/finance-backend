package com.manshi.financebackend.security;

import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.enums.Role;
import com.manshi.financebackend.enums.Status;

public class AuthorizationUtil {

    public static void checkAccess(User user, Role... allowedRoles) {

        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("User is inactive");
        }

        for (Role role : allowedRoles) {
            if (user.getRole() == role) {
                return;
            }
        }

        throw new RuntimeException("Access Denied");
    }
}