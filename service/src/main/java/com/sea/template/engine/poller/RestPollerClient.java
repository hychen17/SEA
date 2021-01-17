package com.sea.template.engine.poller;

import org.springframework.web.client.RestTemplate;

public class RestPollerClient implements PollerClient<RestTemplate, String> {

    private RestTemplate delegate;

    public RestPollerClient(RestTemplate restTemplate) {
        this.delegate = restTemplate;
    }

    public String poll(String url) {
        return delegate.getForObject(url, String.class);
    }

    public RestTemplate delegate() {
        return this.delegate;
    }
}
