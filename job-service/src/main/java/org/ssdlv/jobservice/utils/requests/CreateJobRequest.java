package org.ssdlv.jobservice.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.tags.Tag;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CreateJobRequest {
    private Job job;
    private Collection<Tag> tags;
}
