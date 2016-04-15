package org.registrator.community.dto;

import org.registrator.community.entity.SmtpParameters;

public class SettingsDTO {
    private String registrationMethod;
    private String timeZone;
    private boolean success;
    private boolean error;
    private SmtpParameters smtpParameters;

    public SettingsDTO() {}

    public String getRegistrationMethod() {
        return registrationMethod;
    }

    public void setRegistrationMethod(String registrationMethod) {
        this.registrationMethod = registrationMethod;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getSuccess() {
        return success;
    }

    public boolean getError() {
        return error;
    }

    public void setSmtpParameters(SmtpParameters smtpParameters) {
        this.smtpParameters = smtpParameters;
    }

    public SmtpParameters getSmtpParameters() {
        return smtpParameters;
    }
}
