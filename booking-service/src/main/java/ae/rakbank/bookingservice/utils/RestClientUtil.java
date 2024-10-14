package ae.rakbank.bookingservice.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class RestClientUtil {

    private final RestClient restClient;

    @Autowired
    public RestClientUtil(RestTemplateBuilder restTemplateBuilder) {
        this.restClient = RestClient.create(restTemplateBuilder.build());
    }



    public RestClient.ResponseSpec get(String uri) {

        return restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw  new RestClientException(response.getBody().toString());
                });
    }

    public RestClient.ResponseSpec get(String baseUrl, Map<String, Object> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl).buildAndExpand(queryParams).toUri();
        return get(buildUriWithQueryParams(baseUrl, queryParams));
    }

    public <T> RestClient.ResponseSpec post(String uri, T body) {

        return restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw  new RestClientException(response.getBody().toString());
                });
    }

    public <T> RestClient.ResponseSpec delete(String uri, Map<String, Object> queryParams) {
        uri = buildUriWithQueryParams(uri, queryParams);

        return restClient.delete()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw  new RestClientException(response.getBody().toString());
                });
    }

    private String buildUriWithQueryParams(String url, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        return builder.build().toUriString();
    }

    public <T> RestClient.ResponseSpec postForm(String uri, MultiValueMap<String, String> formData, HttpHeaders headers) {

        return this.restClient.post()
                .uri(uri)
                .headers(h -> h.addAll(headers))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw  new RestClientException(response.getBody().toString());
                });

    }
    // New method to include headers in GET request
    public <T> RestClient.ResponseSpec get(String uri, Map<String, Object> queryParams,  HttpHeaders headers) {

        uri = buildUriWithQueryParams(uri, queryParams);

        return this.restClient.get()
                .uri(uri)
                .headers(h -> h.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw  new RestClientException(response.getBody().toString());
                });
    }

}
