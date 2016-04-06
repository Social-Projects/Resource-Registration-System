package org.registrator.community.service;

import org.registrator.community.dto.json.PasswordResetJson;

public interface PasswordResetService {

    String batchPasswordReset(PasswordResetJson batch);
    String passwordReset();
}
