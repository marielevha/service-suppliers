package org.ssdlv.jobservice.tags;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.utils.SlugifyUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(indexes = {
    @Index(columnList = "text"),
    @Index(columnList = "createdAt"),
})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Job job;

    public Tag(String text, Job job) {
        this.text = text;
        //this.text = SlugifyUtil.getInstance().slugify(text);
        this.job = job;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}