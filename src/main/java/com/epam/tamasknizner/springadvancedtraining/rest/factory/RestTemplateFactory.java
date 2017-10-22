package com.epam.tamasknizner.springadvancedtraining.rest.factory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Responsible for creating a {@link RestTemplate} with the necessary details, like the 'Accept' header and the auth header.
 */
@Component
public class RestTemplateFactory {

    private static final List<HttpMessageConverter<?>> MESSAGE_CONVERTERS = Collections.singletonList(new MappingJackson2HttpMessageConverter());

    public RestTemplateWrapper createRestTemplate(final String jsessid) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(MESSAGE_CONVERTERS);
        return new RestTemplateWrapper(restTemplate, createHeaders(jsessid));
    }

    public <T> RestTemplateWrapper createRestTemplate(final String jsessid, final T request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(MESSAGE_CONVERTERS);
        return new RestTemplateWrapper(restTemplate, createHttpEntity(jsessid, request));
    }

    private <T> HttpEntity<T> createHttpEntity(final String jsessid, final T request) {
        return new HttpEntity<>(request, getHttpHeaders(jsessid));
    }

    private HttpEntity<String> createHeaders(final String jsessid) {
        return new HttpEntity<>("parameters", getHttpHeaders(jsessid));
    }

    private HttpHeaders getHttpHeaders(final String jsessid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", jsessid);
        return headers;
    }

}
