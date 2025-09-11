package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;


public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        if (funcionario == null) return null;
        return new FuncionarioDto(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCargo() != null ? funcionario.getCargo().getId() : null
        );
    }

    public static Funcionario toEntity(FuncionarioDto dto, Cargo cargo) {
        if (dto == null) return null;
        Funcionario f = new Funcionario();
        f.setId(dto.getId());
        f.setNome(dto.getNome());
        f.setEmail(dto.getEmail());
        f.setTelefone(dto.getTelefone());
        f.setCargo(cargo);
        return f;
    }
}