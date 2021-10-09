package org.ssdlv.categoryservice.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class JobDailyResponse {
    private LocalDate publishedAt;
    private Integer size;
}
