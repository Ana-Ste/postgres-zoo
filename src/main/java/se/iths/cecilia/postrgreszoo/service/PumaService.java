package se.iths.cecilia.postrgreszoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.iths.cecilia.postrgreszoo.model.Puma;
import se.iths.cecilia.postrgreszoo.repository.PumaRepository;
import se.iths.cecilia.postrgreszoo.validator.PumaValidator;

import java.util.List;

@Service
public class PumaService {

    private static final Logger logger = LoggerFactory.getLogger(PumaService.class);

    private final PumaRepository repository;
    private final PumaValidator validator;

    public PumaService(PumaRepository repository, PumaValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Puma> getAll() {
        logger.info("Fetching all pumas");
        return repository.findAll();
    }

    public Puma getOne(Long id) {
        logger.info("Fetching puma with id: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Puma not found with id: {}", id);
                    return new RuntimeException("Puma not found");
                });
    }

    public Puma create(Puma puma) {
        logger.info("Creating new puma with name: {}", puma.getName());

        validator.validate(puma);

        Puma saved = repository.save(puma);

        logger.info("Puma created successfully with id: {}", saved.getId());

        return saved;
    }

    public Puma update(Long id, Puma puma) {
        logger.info("Updating puma with id: {}", id);

        validator.validate(puma);

        Puma existing = getOne(id);

        existing.setName(puma.getName());
        existing.setAge(puma.getAge());
        existing.setWeight(puma.getWeight());
        existing.setDangerous(puma.isDangerous());

        Puma updated = repository.save(existing);

        logger.info("Puma updated successfully with id: {}", updated.getId());

        return updated;
    }

    public void delete(Long id) {
        logger.info("Deleting puma with id: {}", id);

        getOne(id);

        repository.deleteById(id);

        logger.info("Puma deleted successfully with id: {}", id);
    }
}