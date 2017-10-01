package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.repository.DiscountStrategyRepository;
import com.epam.tamasknizner.springadvancedtraining.service.settings.AbstractDiscountStrategy;
import org.springframework.stereotype.Component;

@Component
public class DiscountStrategyRepositoryImpl extends MapBasedIdentityRepository<AbstractDiscountStrategy> implements DiscountStrategyRepository {
}
