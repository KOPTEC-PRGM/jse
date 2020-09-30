package com.nlmk.potapov.tm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceUtil {

    private static final Logger logger = LogManager.getLogger(ServiceUtil.class);

    private ServiceUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void traceLogger(final String methodName, final Object[] params) {
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Вызов метода ").append(methodName).append("(");
        String prefix ="";
        for (Object par:params){
            resultMessage.append(prefix);
            prefix = ", ";
            resultMessage.append(par);
        }
        resultMessage.append(")");
        logger.trace(resultMessage);
    }

}
