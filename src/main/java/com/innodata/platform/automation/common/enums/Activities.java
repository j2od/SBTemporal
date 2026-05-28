package com.innodata.platform.automation.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Activities {
    HTTP_FETCH("httpFetch"),
    SEND_EMAIL("sendEmail"),
    CREATE_TICKET("createTicket"),
    UPDATE_CUSTOMER("updateCustomer");

    private final String name;

    Activities(String name) {
        this.name = name;
    }

    public static boolean isAllowed(String candidate) {
        for (Activities activity : values()) {
            if (activity.name.equals(candidate)) {
                return true;
            }
        }
        return false;
    }

    public static Optional<Activities> fromName(String candidate) {
        return Arrays.stream(values())
                .filter(a -> a.name.equals(candidate))
                .findFirst();
    }
}
