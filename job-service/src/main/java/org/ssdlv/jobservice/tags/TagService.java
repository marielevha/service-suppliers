package org.ssdlv.jobservice.tags;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.utils.SlugifyUtil;

import java.util.Collection;
import java.util.Date;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Async
    public void createMultiple(Collection<Tag> collection, Job job) {
        collection.forEach(tag -> {
            tag.setJob(job);
            tag.setCreatedAt(new Date());
            tag.setUpdatedAt(new Date());
            //tag.setText(SlugifyUtil.getInstance().slugify(tag.getText()));
            tag.setText(tag.getText());
            tagRepository.save(tag);
        });
    }
}
