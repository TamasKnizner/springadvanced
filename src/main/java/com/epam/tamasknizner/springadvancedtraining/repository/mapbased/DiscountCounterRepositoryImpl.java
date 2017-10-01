package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.DiscountCounter;
import com.epam.tamasknizner.springadvancedtraining.repository.DiscountCounterRepository;
import org.springframework.stereotype.Component;

@Component
public class DiscountCounterRepositoryImpl extends MapBasedIdentityRepository<DiscountCounter> implements DiscountCounterRepository {
}
