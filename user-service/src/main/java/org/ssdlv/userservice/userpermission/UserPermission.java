package org.ssdlv.userservice.userpermission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ssdlv.userservice.permissions.Permission;
import org.ssdlv.userservice.users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date createdAt = new Date();
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Permission permission;

    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
        this.createdAt = new Date();
    }
}
