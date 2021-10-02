package org.ssdlv.categoryservice.categories;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ssdlv.categoryservice.tags.Tag;
import org.ssdlv.categoryservice.utils.SlugifyUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private String slug;
    private boolean activated;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    @OneToMany(mappedBy = "category")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Tag> tags;
    @Transient
    private int totalJobs;

    public Category(String title, String description, boolean activated) {
        this.title = title;
        this.description = description;
        this.slug = SlugifyUtil.getInstance().slugify(title);
        this.activated = activated;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
