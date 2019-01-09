package com.upt.generator.datc;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class Generator {
    private RestTemplate restTemplate;

    public Generator(){
        restTemplate = new RestTemplate();
    }

    @Scheduled(fixedDelay = 30_000)
    public void getValues(){
        log.info("Started posting new batch of generated values");
        val sensors = restTemplate.exchange("https://irrigationapi2018.azurewebsites.net/api/sensors", HttpMethod.GET, null,  new ParameterizedTypeReference<List<Sensor>>(){}).getBody();
        sensors.stream()
                .forEach(sensor -> {
                    Random random = new Random();
                    Value value = new Value(sensor.getId(), random.nextInt(100) - 20,random.nextInt(160) - 30);
                    String response = restTemplate.postForObject("https://irrigationapi2018.azurewebsites.net/api/values", value,String.class);
                    log.info("Posted value {} and got response {}", value, response);
                });
    }

    @Data
    private static class Sensor{
        @JsonAlias("Id")
        private int id;
    }

    @Data
    @AllArgsConstructor
    private static class Value{
        private int Id_senzor;
        private double Temperatura;
        private double Umiditate;
    }
}
