package br.com.nerosct.front_gestao_vagas.modules.candidate.controller.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserDTO {

    private UUID id;
    private String username;
    private String email;
    private String name;
    private String description;
}
