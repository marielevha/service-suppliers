package org.ssdlv.userservice.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ssdlv.userservice.userpermission.UserPermission;
import org.ssdlv.userservice.utils.Constants;
import org.ssdlv.userservice.utils.SlugifyUtil;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;
    private String phone;
    @Email(message = "Please entry valid email")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;
    //@Min(value = 8)
    @NotBlank(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    private String avatar;
    private String profile;
    @Column(unique = true, nullable = false)
    private String slug;
    private String address;
    private double latitude;
    private double longitude;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;
    private Date deletedAt;
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<UserPermission> permissions;

    public User(String first_name, String last_name, String phone, String email, String password, String avatar, String address) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.slug = SlugifyUtil.getInstance().slugify((first_name + " " + last_name));
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.latitude = 27.174835;
        this.longitude = 78.040753;
        this.profile = Constants.PROFILE_CLIENT;
    }
}
