package br.senai.prova_jwt.domains.funcionario;


import br.senai.prova_jwt.domains.cargo.CargoMapper;

public class FuncionarioMapper {

    public static FuncionarioDTO toDto(Funcionario funcionario) {
        if (funcionario == null) return null;
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                CargoMapper.toDto(funcionario.getCargo())
        );
    }

    public static Funcionario toEntity(FuncionarioDTO dto) {
        if (dto == null) return null;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(CargoMapper.toEntity(dto.getCargo()));
        return funcionario;
    }
}
