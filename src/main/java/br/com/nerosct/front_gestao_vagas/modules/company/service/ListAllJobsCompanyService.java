package br.com.nerosct.front_gestao_vagas.modules.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.nerosct.front_gestao_vagas.modules.candidate.dto.JobDTO;

@Service
public class ListAllJobsCompanyService {

    @Value("${host.api.gestao.vagas}")  
    private String hostApiGestaoVagas;


    public List<JobDTO> execute(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<JobDTO>> responseType = 
        new ParameterizedTypeReference<List<JobDTO>>() {};

        String url = this.hostApiGestaoVagas.concat("/company/job/");

        var response = rt.exchange(url, HttpMethod.GET, entity, responseType);

        return response.getBody();
        
    }
    
}
