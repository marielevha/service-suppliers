package org.ssdlv.userservice.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ssdlv.userservice.userpermission.UserPermission;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    @OneToMany(mappedBy = "permission", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<UserPermission> users;

    public Permission(String name) {
        this.name = name;
        this.created_at = new Date();
        this.updated_at = new Date();
    }
}
