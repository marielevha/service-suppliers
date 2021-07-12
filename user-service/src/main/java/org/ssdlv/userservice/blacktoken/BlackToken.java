package org.ssdlv.userservice.blacktoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "token")
})
public class BlackToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "longtext")
    private String token;
    private Date createdAt;

    public BlackToken(String token) {
        this.token = token;
        this.createdAt = new Date();
    }
}
