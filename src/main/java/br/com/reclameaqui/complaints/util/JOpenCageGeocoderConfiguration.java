package br.com.reclameaqui.complaints.util;

import com.byteowls.jopencage.JOpenCageGeocoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JOpenCageGeocoderConfiguration {

    @Value("${opencage.api.key}")
    private String apiKey;

    @Bean
    public JOpenCageGeocoder createBean() {
        return new JOpenCageGeocoder(apiKey);
    }
}
