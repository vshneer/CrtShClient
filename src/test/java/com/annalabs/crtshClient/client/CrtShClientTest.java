package com.annalabs.crtshClient.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CrtShClientTest {

    @Autowired
    CrtShClient crtShClient;

    @Test
    void testCrtShClient() throws IOException {
        Set<String> subdomains = crtShClient.getSubdomains("fnx.co.il");
        assertFalse(subdomains.isEmpty());
    }

}