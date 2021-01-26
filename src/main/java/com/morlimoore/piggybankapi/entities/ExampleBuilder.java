package com.morlimoore.piggybankapi.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Setter
@Getter
public class ExampleBuilder {
    private String accountNumber;
    private String name;
    private String email;

    private ExampleBuilder() {
    }

    public static class Builder {
        private String accountNumber;
        private String name;
        private String email;

        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ExampleBuilder build() {
            ExampleBuilder exampleBuilder = new ExampleBuilder();
            exampleBuilder.setAccountNumber(this.accountNumber);
            exampleBuilder.setName(this.name);
            exampleBuilder.setEmail(this.email);

            return exampleBuilder;
        }
    }

//    public static Map<String, String> doDemo() {
//        Map<String, String> map = Collections.singletonMap("key", "value");
//        map.put("key2", "value2");
//        return map;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(doDemo());
//    }

}
