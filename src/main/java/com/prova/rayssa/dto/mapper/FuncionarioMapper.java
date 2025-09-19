package com.prova.rayssa.dto.mapper;

import com.prova.rayssa.dto.FuncionarioDTO;
import com.prova.rayssa.model.Funcionario;

public class FuncionarioMapper {
    
    public static FuncionarioDTO toDto(Funcionario funcionario) {
        if (funcionario == null) return null;
        
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        
        if (funcionario.getCargo() != null) {
            dto.setCargoId(funcionario.getCargo().getId());
            dto.setCargo(CargoMapper.toDto(funcionario.getCargo()));
        }
        
        return dto;
    }

    public static Funcionario toEntity(FuncionarioDTO dto) {
        if (dto == null) return null;
        
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        
        // O cargo será setado no service usando o cargoId
        return funcionario;
    }
    
    public static void updateEntityFromDto(FuncionarioDTO dto, Funcionario funcionario) {
        if (dto == null || funcionario == null) return;
        
        funcionario.setNome(dto.getNome());
        // O cargo será atualizado no service
    }
}
