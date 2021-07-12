package org.ssdlv.jobservice.tags;

import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.utils.SlugifyUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public Page<Tag> searchTagContainsString(String query) {
        List<Tag> tags = tagRepository.findAllByTextContaining(query);

        Map<String, List<Tag>> groupTags = tags
                .stream()
                .collect(Collectors.groupingBy(Tag::getText));

        List<Tag> finalTags = new ArrayList<>();

        groupTags.values().forEach(tags1 -> {
            finalTags.add(tags1.get(0));
            System.err.println(tags1.size());
        });
        return paginate_tag(finalTags);
    }

    public Page<Tag> paginate_tag(List<Tag> tags) {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), tags.size());
        return new PageImpl<>(tags.subList(start, end), pageable, tags.size());
    }
}
