package org.ssdlv.userservice.utils.forms;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthForm {
    public String email;
    public String password;
}