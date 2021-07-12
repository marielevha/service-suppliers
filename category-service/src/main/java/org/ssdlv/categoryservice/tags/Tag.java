package org.ssdlv.categoryservice.tags;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ssdlv.categoryservice.categories.Category;
import org.ssdlv.categoryservice.utils.SlugifyUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Text is required")
    private String text;
    @Column(nullable = false)
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Category category;

    public Tag(String text, Category category) {
        this.text = text;
        this.category = category;
        this.slug = SlugifyUtil.getInstance().slugify(text);
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
