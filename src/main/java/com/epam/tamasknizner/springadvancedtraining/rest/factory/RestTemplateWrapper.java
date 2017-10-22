package com.epam.tamasknizner.springadvancedtraining.rest.factory;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWrapper {

    private RestTemplate restTemplate;
    private HttpEntity<?> entity;

    public RestTemplateWrapper(final RestTemplate restTemplate, final HttpEntity<?> entity) {
        this.restTemplate = restTemplate;
        this.entity = entity;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public HttpEntity<?> getEntity() {
        return entity;
    }
}
