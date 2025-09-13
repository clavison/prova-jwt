package br.senai.prova_jwt.dto.mapper;


import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Funcionario;

public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        if (funcionario == null){
            return null;
        }
        return new FuncionarioDto(
                funcionario.getId(),
                funcionario.getNome(),
                CargoMapper.toDto(funcionario.getCargo())
        );
    }

    public static Funcionario toEntity(FuncionarioDto funcionarioDto) {
        if (funcionarioDto == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioDto.getId());
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setCargo(CargoMapper.toEntity(funcionarioDto.getCargo()));
        return funcionario;
    }

}
