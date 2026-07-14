package br.com.nerosct.front_gestao_vagas.modules.company.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.nerosct.front_gestao_vagas.modules.company.dto.CreateJobsDTO;

@Service
public class CreateJobService {


    @Value("${host.api.gestao.vagas}")  
    private String hostApiGestaoVagas;

    public String execute(CreateJobsDTO createJobDTO, String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CreateJobsDTO> request = new HttpEntity<>(createJobDTO, headers);

        try {
            String url = this.hostApiGestaoVagas.concat("/company/job/");

            var result = rt.postForObject(url, request, String.class);
            return result;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
