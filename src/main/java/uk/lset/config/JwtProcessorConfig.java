package uk.lset.config;


import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class JwtProcessorConfig {

    @Bean
    public ConfigurableJWTProcessor<SecurityContext> jwtProcessor() throws MalformedURLException {
        // URL where JWKS is hosted (e.g., from an authentication provider)
        URL jwkSetURL = new URL("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_5MncTZem5/.well-known/jwks.json");

        // Load JWK Set from the remote JWKS URL
        JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(jwkSetURL);

        // Create JWT Processor
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        // Set up JWS key selector
        JWSKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);

        return jwtProcessor;
    }
}
