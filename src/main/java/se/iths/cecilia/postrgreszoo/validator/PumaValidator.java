package se.iths.cecilia.postrgreszoo.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.iths.cecilia.postrgreszoo.exception.pumaExceptions.PumaInvalidAgeException;
import se.iths.cecilia.postrgreszoo.exception.pumaExceptions.PumaInvalidWeightException;
import se.iths.cecilia.postrgreszoo.exception.pumaExceptions.PumaNameIsEmptyException;
import se.iths.cecilia.postrgreszoo.exception.pumaExceptions.PumaNameIsNullException;
import se.iths.cecilia.postrgreszoo.model.Puma;

@Component
public class PumaValidator {

    private static final Logger logger = LoggerFactory.getLogger(PumaValidator.class);

    public void validate(Puma puma) {
        if (puma.getName() == null) {
            logger.warn("Validation failed: name is null");
            throw new PumaNameIsNullException("Puma name cannot be null");
        }

        if (puma.getName().isEmpty()) {
            logger.warn("Validation failed: name is empty");
            throw new PumaNameIsEmptyException("Puma name cannot be empty");
        }

        if (puma.getAge() < 0) {
            logger.warn("Validation failed: age is negative");
            throw new PumaInvalidAgeException("Puma age cannot be negative");
        }

        if (puma.getWeight() <= 0) {
            logger.warn("Validation failed: weight <= 0");
            throw new PumaInvalidWeightException("Puma weight must be greater than 0");
        }
    }
}