package com;

import org.apache.http.conn.ssl.NoopHostnameVerifier;

import feign.Client;
import feign.Feign;

public class FeignHttpsConfig {

    public Feign.Builder feignBuilder() {
        final Client trustSSLSockets = client();
        return Feign.builder().client(trustSSLSockets);
    }

    public Client client(){
        return new Client.Default(
                TrustingSSLSocketFactory.get(), new NoopHostnameVerifier());
    }


}