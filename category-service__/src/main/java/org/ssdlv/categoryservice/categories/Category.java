package org.ssdlv.categoryservice.categories;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @Id
    private Long id;
    private String title;
    private String description;
    private String slug;
    private boolean activated;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;

    public Category(String title, String description, String slug, boolean activated) {
        //this.id = id;
        this.title = title;
        this.description = description;
        this.slug = slug;
        this.activated = activated;
        this.created_at = new Date();
        this.updated_at = new Date();
        this.deleted_at = new Date();
    }
}
