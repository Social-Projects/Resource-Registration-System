package org.registrator.community.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Utils class for WEB support
 */
public class HttpUtils {

    private HttpUtils() {}

    public static String getFullRequestURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append(request.getQueryString());
        }
        return requestURL.toString();
    }

}
