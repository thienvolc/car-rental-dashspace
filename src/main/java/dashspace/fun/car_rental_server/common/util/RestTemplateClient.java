package dashspace.fun.car_rental_server.common.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class RestTemplateClient {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Nullable
    public <T> T get(String url, Class<T> clazz) throws RestClientException {
        return restTemplate.getForObject(url, clazz);
    }

    public <T> List<T> getList(String url, ParameterizedTypeReference<List<T>> typeRef)
            throws RestClientException {

        ResponseEntity<List<T>> res = restTemplate.exchange(url, GET, null, typeRef);

        return (res.getBody() == null ? List.of() : res.getBody());
    }
}
