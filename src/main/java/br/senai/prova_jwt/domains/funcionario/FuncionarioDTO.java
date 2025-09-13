package com.erp.funcionariocargo.domains.funcionario;

import com.erp.funcionariocargo.domains.cargo.CargoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private CargoDTO cargo;
}
