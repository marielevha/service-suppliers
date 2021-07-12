package org.ssdlv.jobservice.offers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.utils.SlugifyUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @Column(nullable = false, columnDefinition = "longtext")
    private String description;
    private double price;
    @Column(nullable = false)
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private Date activatedAt;
    private Date deletedAt;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Job job;

    public Offer(String title, String description, double price, Job job) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.slug = SlugifyUtil.getInstance().slugify(title);
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.job = job;
    }
}
