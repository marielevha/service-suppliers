package org.ssdlv.categoryservice.tags;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;
import org.ssdlv.categoryservice.categories.Category;
import org.ssdlv.categoryservice.categories.CategoryRepository;
import org.ssdlv.categoryservice.utils.SlugifyUtil;
import org.ssdlv.categoryservice.utils.requests.AddTagRequest;

import java.util.Date;
import java.util.Optional;

@Service
public class TagService {
    private TagRepository tagRepository;
    private CategoryRepository categoryRepository;

    public TagService(TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public Tag create(AddTagRequest tagRequest) {
        Category category = categoryRepository.findById(tagRequest.getCategoryId()).orElseThrow(null);
        Tag tag = new Tag(tagRequest.getText(), category);
        tag = tagRepository.save(tag);
        return tag;
    }

    public Tag update(Tag newTag, Long id) throws NotFound {
        Tag tag = tagRepository.findById(id).orElseThrow(NotFound::new);

        tag.setText(newTag.getText());
        tag.setCategory(newTag.getCategory());

        tag.setUpdatedAt(new Date());
        tag.setSlug(SlugifyUtil.getInstance().slugify((tag.getText())));
        return tagRepository.save(tag);
    }

    public Tag find(Long id) throws NotFound {
        return tagRepository.findById(id).orElseThrow(NotFound::new);
    }

    public Boolean delete(Long id) throws NotFound {
        Tag tag = tagRepository.findById(id).orElseThrow(NotFound::new);
        tag.setDeletedAt(new Date());
        return tagRepository.save(tag).getDeletedAt() != null;
    }
}
