package org.ssdlv.jobservice.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    private Long id;
    private String title;
    private String description;
    private String slug;
    private boolean activated;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
