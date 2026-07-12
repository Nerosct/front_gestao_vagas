package br.com.nerosct.front_gestao_vagas.modules.candidate.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String access_token;
    private List<String> roles;
    private long expires_in;
}
