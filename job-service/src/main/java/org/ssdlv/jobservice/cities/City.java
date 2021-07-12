package org.ssdlv.jobservice.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ssdlv.jobservice.jobs.Job;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    @OneToMany(mappedBy = "city")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Job> jobs;

    public City(String name) {
        this.name = name;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
