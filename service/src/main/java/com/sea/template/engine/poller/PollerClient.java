package com.sea.template.engine.poller;

import org.springframework.web.client.RestTemplate;

public abstract interface PollerClient<T, R>{

    R poll(String url);
    T delegate();
}
