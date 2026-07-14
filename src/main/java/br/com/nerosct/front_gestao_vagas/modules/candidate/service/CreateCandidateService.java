package br.com.nerosct.front_gestao_vagas.modules.candidate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.nerosct.front_gestao_vagas.modules.candidate.dto.CreateCandidateDTO;

@Service
public class CreateCandidateService {

    @Value("${host.api.gestao.vagas}")  
    private String hostApiGestaoVagas;

    public void execute(CreateCandidateDTO createCandidateDTO) {
        try {
            RestTemplate rt = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateCandidateDTO> request = new HttpEntity<>(createCandidateDTO, headers);

            String url = this.hostApiGestaoVagas.concat("/candidate/");

            var result = rt.postForObject(url, request, String.class);
            System.out.println(result);
        } catch (HttpClientErrorException ex) {
            throw ex;
        }
    }
}
