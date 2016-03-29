package org.registrator.community.exceptions;

/**
 * Created by roman.golyuk on 23.03.2016.
 */
public class RegistratorException extends Exception {

    public String getMessageKey() {
        return "label.errorClass." + this.getClass().getSimpleName();
    }

}
