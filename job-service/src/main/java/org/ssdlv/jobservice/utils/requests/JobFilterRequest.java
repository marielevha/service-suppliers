package org.ssdlv.jobservice.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.ssdlv.jobservice.tags.Tag;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@ToString
public class JobFilterRequest {
    //FILTER
    private Long userId;
    private Long categoryId;
    private String state;
    private String column;
    private String order;
    private boolean ascending;
    private boolean published;
    private boolean deleted;
    private LocalDate start;
    private LocalDate end;
    private String query;
    private Collection<String> tags;
    private Collection<Long> categories;
    private Collection<Long> cities;

    //PAGINATE
    private int page;
    private int size;
}