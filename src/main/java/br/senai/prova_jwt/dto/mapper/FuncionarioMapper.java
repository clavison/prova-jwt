package br.senai.prova_jwt.dto.mapper;


import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Funcionario;

public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        if (funcionario == null) return null;
        return new FuncionarioDto(
                funcionario.getId(),
                funcionario.getNome(),
                CargoMapper.toDto(funcionario.getCargo())
        );
    }

    public static Funcionario toEntity(FuncionarioDto dto) {
        if (dto == null) return null;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(CargoMapper.toEntity(dto.getCargo()));
        return funcionario;
    }
}

;