package org.ssdlv.jobservice.offers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.users.User;
import org.ssdlv.jobservice.utils.SlugifyUtil;
import org.ssdlv.jobservice.utils.State;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    private double price;
    /*@Enumerated(EnumType.STRING)
    private State state;*/
    @Column(nullable = false)
    private String slug;
    @Column(nullable = false)
    private Long userId;
    private Date activatedAt;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Job job;
    @Transient
    private User user;
    private Date startDate;
    private Date endDate;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public Offer(String title, String description, double price, Job job, Long userId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.slug = SlugifyUtil.getInstance().slugify(title);
        this.job = job;
        this.userId = userId;
        this.startDate = new Date();
        this.endDate = new Date();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
