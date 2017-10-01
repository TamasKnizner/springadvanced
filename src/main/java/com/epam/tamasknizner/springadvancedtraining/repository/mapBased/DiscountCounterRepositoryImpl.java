package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.DiscountCounter;
import org.springframework.stereotype.Component;

@Component
public class DiscountCounterRepositoryImpl extends MapBasedIdentityRepository<DiscountCounter> {
}
