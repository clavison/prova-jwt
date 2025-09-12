package br.senai.prova_jwt.mapper;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }

        return FuncionarioDto.builder()
                .id(funcionario.getId())
                .username(funcionario.getLogin())
                .cargo(CargoMapper.toDto(funcionario.getCargo()))
                .build();
    }

    public static Funcionario toEntity(FuncionarioDto funcionarioDto) {
        if (funcionarioDto == null) {
            return null;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionario.getId());
        funcionario.setLogin(funcionarioDto.getUsername());
        funcionario.setSenha(funcionarioDto.getPassword());
        funcionario.setCargo(CargoMapper.toEntity(funcionarioDto.getCargo()));

        return funcionario;
    }
}
