package com.hg.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Value("${vault_tester}")
    private String vaultTester;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("vault connection is: " + vaultTester);
    }
}