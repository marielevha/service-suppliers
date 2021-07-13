package org.ssdlv.jobservice.offers;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;
import org.ssdlv.jobservice.jobs.JobRepository;
import org.ssdlv.jobservice.users.UserService;
import org.ssdlv.jobservice.utils.SlugifyUtil;
import org.ssdlv.jobservice.utils.State;

import java.util.Date;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final JobRepository jobRepository;
    private final UserService userService;

    public OfferService(OfferRepository offerRepository, JobRepository jobRepository, UserService userService) {
        this.offerRepository = offerRepository;
        this.jobRepository = jobRepository;
        this.userService = userService;
    }

    public Offer create(Offer offer) {
        offer.setCreatedAt(new Date());
        offer.setUpdatedAt(new Date());
        offer.setSlug(SlugifyUtil.getInstance().slugify((offer.getTitle())));
        return offerRepository.save(offer);
    }

    public Offer update(Offer newOffer, Long id) throws NotFound {
        Offer offer = offerRepository.findById(id).orElseThrow(NotFound::new);
        offer.setTitle(newOffer.getTitle());
        offer.setDescription(newOffer.getDescription());
        offer.setPrice(newOffer.getPrice());
        offer.setJob(newOffer.getJob());
        offer.setUpdatedAt(new Date());
        offer.setSlug(SlugifyUtil.getInstance().slugify((offer.getTitle())));
        return offerRepository.save(offer);
    }

    public Boolean delete(Long id) throws NotFound {
        Offer offer = offerRepository.findById(id).orElseThrow(NotFound::new);
        offer.setDeletedAt(new Date());
        return offerRepository.save(offer).getDeletedAt() != null;
    }

    public Offer accept(Long id) throws NotFound {
        Offer offer = offerRepository.findById(id).orElseThrow(NotFound::new);
        //offer.setState(State.ACCEPTED);
        offer.setActivatedAt(new Date());
        return offerRepository.save(offer);
    }

    public Offer refused(Long id) throws NotFound {
        Offer offer = offerRepository.findById(id).orElseThrow(NotFound::new);
        //offer.setState(State.REFUSED);
        return offerRepository.save(offer);
    }

    public Offer findById(Long id) throws NotFound {
        return offerRepository.findById(id).orElseThrow(NotFound::new);
    }

    public void data() {}

}
