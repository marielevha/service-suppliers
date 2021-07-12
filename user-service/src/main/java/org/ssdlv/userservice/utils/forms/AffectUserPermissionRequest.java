package org.ssdlv.userservice.utils.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AffectUserPermissionRequest {
    @NotBlank(message = "User is required")
    private Long userId;
    @NotBlank(message = "Permission is required")
    private Long permissionId;
}
