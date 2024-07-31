package com.hg.product.utility.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.net.http.HttpClient.Redirect.*;

public class HttpClientOperation {
    public static void main(String[] args) {
        HttpClientOperation hco = new HttpClientOperation();

        // connect a server and get response with async
        String url = "https://google.com";
        String response = hco.connectAndGetPage(url);
        System.out.println("response = " + response);
    }

    private String connectAndGetPage(String url) {
        String response = null;
        HttpClient httpClient = HttpClient
                .newBuilder()
                .followRedirects(ALWAYS)
                .build();
        try {
            HttpRequest req = HttpRequest
                    .newBuilder(new URI(url))
                    .GET()
                    .build();
            CompletableFuture<String> future = httpClient
                    .sendAsync(req, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
            while (!future.isDone()) {
            }
            response = future.get();

        } catch (URISyntaxException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
