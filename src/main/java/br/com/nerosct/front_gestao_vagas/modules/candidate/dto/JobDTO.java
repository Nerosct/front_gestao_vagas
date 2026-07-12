package br.com.nerosct.front_gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private UUID id;
    private String description;
    private String benefits;
    private String level;
    private UUID companyId;
    private Date createdAt;
}
