package br.senai.prova_jwt.mapper;


import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;

public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioDto(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo() != null ? funcionario.getCargo().getId() : null
        );
    }

    public static Funcionario toEntity(FuncionarioDto dto) {
        if (dto == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());

        if (dto.getCargoId() != null) {
            Cargo cargo = new Cargo();
            cargo.setId(dto.getCargoId());
            funcionario.setCargo(cargo);
        }

        return funcionario;
    }
}
