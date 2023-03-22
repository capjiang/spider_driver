package com.jiang.schedule_manage.common;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class HttpClient {

    @Autowired
    private static RestTemplate restTemplate;

    public static String sendPostRequest(String url, MultiValueMap<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForObject(url, requestEntity, ResponseEntity.class);
        return response.getBody();
    }

    public static String sendGetRequest(String url, MultiValueMap<String, String> params, HttpHeaders headers) {
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }

    public static String sendPostRequestRaw(String url, String jsonParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(jsonParam, headers);
        ResponseEntity<String> resp = restTemplate.postForEntity(url, entity, String.class);
        return resp.getBody();
    }

    public static String sendPostRequestObject(String url, Object request) {
        return restTemplate.postForObject(url, request, String.class);
    }

    public static String sendGetRequest2(String url) {
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        return resp.getBody();
    }

}
