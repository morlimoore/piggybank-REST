package com.morlimoore.piggybankapi.entities;

import org.springframework.stereotype.Component;

@Component
public class ExampleBuilderUsage {

    public void exampleBuilder() {
       ExampleBuilder builder = new ExampleBuilder.Builder()
               .setEmail("")
               .setAccountNumber("")
               .setName("")
               .build();
    }
}
