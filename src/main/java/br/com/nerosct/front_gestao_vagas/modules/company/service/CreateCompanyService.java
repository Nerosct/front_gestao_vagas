package br.com.nerosct.front_gestao_vagas.modules.company.service;


import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

import br.com.nerosct.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;

@Service
public class CreateCompanyService {


    @Value("${host.api.gestao.vagas}")  
    private String hostApiGestaoVagas;

    public void execute(CreateCompanyDTO createCompanyDTO) {
        try {
            RestTemplate rt = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateCompanyDTO> request = new HttpEntity<>(createCompanyDTO, headers);

            String url = this.hostApiGestaoVagas.concat("/company/");

            var result = rt.exchange(url, HttpMethod.POST, request, String.class);
            System.out.println(result);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

    }

}
