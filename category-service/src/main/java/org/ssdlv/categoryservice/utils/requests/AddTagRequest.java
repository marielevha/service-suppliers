package org.ssdlv.categoryservice.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTagRequest {
    private String text;
    private Long categoryId;
}
