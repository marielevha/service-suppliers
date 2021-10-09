package org.ssdlv.jobservice.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ssdlv.jobservice.categories.Category;
import org.ssdlv.jobservice.cities.City;
import org.ssdlv.jobservice.offers.Offer;
import org.ssdlv.jobservice.tags.Tag;
import org.ssdlv.jobservice.users.User;
import org.ssdlv.jobservice.utils.SlugifyUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Column(nullable = false, columnDefinition = "text")
    private String title;
    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    @Column(columnDefinition = "date")
    private LocalDate publishedAt;
    private Date deletedAt;
    private double latitude;
    private double longitude;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private Long userId;
    //@Column(columnDefinition = "varchar")
    @OneToMany(mappedBy = "job")
    private Collection<Tag> tags;
    @Transient
    private Collection<String> tagsSlug;
    @Transient
    private User user;
    @Transient
    private Category category;
    @OneToMany(mappedBy = "job")
    private Collection<Offer> offers;
    @ManyToOne
    private City city;
    @Transient
    private int totalOffers = 0;

    public Job(
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Description is required") String description,
        Long userId,
        Long categoryId,
        City city
    ) {
        this.title = title;
        this.description = description;
        this.slug = SlugifyUtil.getInstance().slugify(title);
        this.userId = userId;
        this.categoryId = categoryId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.publishedAt = LocalDate.now();
        this.latitude = 27.174835;
        this.longitude = 78.040753;
        this.city = city;
    }
}
