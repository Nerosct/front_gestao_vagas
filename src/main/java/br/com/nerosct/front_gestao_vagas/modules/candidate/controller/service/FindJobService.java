package br.com.nerosct.front_gestao_vagas.modules.candidate.controller.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.nerosct.front_gestao_vagas.modules.candidate.controller.dto.JobDTO;

import org.springframework.http.HttpStatus;

@Service
public class FindJobService {

    public List<JobDTO> execute(String token, String filter) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080/candidate/jobs")
                .queryParam("jobDescriptionFilter", filter);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {
        };

        try {
            var result = rt.exchange(builder.toUriString(), HttpMethod.GET, request, responseType);
            return result.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
