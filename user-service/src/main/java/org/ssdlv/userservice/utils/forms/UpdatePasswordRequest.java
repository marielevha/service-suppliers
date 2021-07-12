package org.ssdlv.userservice.utils.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordRequest {
    private String newPassword;
    private String confirmNewPassword;
}
