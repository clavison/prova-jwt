package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Funcionario;

public class FuncionarioMapper {

    public static FuncionarioDto converterParaDto(Funcionario f) {
        if (f == null) {
            return null;
        }
        FuncionarioDto dto = new FuncionarioDto();
        dto.setId(f.getIdFuncionario());
        dto.setNomeCompleto(f.getNomeCompleto());
        dto.setEmailContato(f.getEmailContato());
        dto.setTelefone(f.getTelefone());
        if (f.getCargo() != null) {
            dto.setCargoId(f.getCargo().getIdCargo());
            dto.setCargoNome(f.getCargo().getNome());
        }
        return dto;
    }

    public static Funcionario converterParaEntidade(FuncionarioDto dto) {
        if (dto == null) {
            return null;
        }
        Funcionario f = new Funcionario();
        f.setIdFuncionario(dto.getId());
        f.setNomeCompleto(dto.getNomeCompleto());
        f.setEmailContato(dto.getEmailContato());
        f.setTelefone(dto.getTelefone());
        return f;
    }

    public static FuncionarioDto toDto(Funcionario f) {
        return converterParaDto(f);
    }

    public static Funcionario toEntity(FuncionarioDto d) {
        return converterParaEntidade(d);
    }
}