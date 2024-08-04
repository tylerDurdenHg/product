package com.hg.product.logger;

import com.hg.product.exception.GenericException;
import com.hg.product.exception.NonBusinessException;
import org.slf4j.Logger;

import java.util.Objects;

public class GenericLogger {

    public static void handleException(Logger log, String classMethod, String errorMessage, Object obj, String nonBusinessMessage, Exception e) {
        if (e instanceof GenericException gex) {
            log.error("{} {} {} ", classMethod, errorMessage, Objects.requireNonNullElse(obj, " "), e);
            throw gex;
        } else {
            log.error("Non Business Exception with ", e);
            throw new NonBusinessException(nonBusinessMessage, String.format(errorMessage + " %s ", obj), e);
        }
    }
}
