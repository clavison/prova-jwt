package br.senai.prova_jwt.domain.funcionario;


import br.senai.prova_jwt.domain.cargo.CargoMapper;
import br.senai.prova_jwt.domain.usuario.UsuarioMapper;
import br.senai.prova_jwt.helpers.bases.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper implements BaseMapper<Funcionario, FuncionarioDto> {

    private final CargoMapper cargoMapper;
    private final UsuarioMapper usuarioMapper;

    public FuncionarioMapper(CargoMapper cargoMapper, UsuarioMapper usuarioMapper) {
        this.cargoMapper = cargoMapper;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public FuncionarioDto toDTO(Funcionario funcionario) {
        return FuncionarioDto.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cargo(cargoMapper.toDTO(funcionario.getCargo()))
                .usuario(usuarioMapper.toDTO(funcionario.getUsuario()))
                .build();
    }

    @Override
    public Funcionario toEntity(FuncionarioDto funcionarioDto) {
        return Funcionario.builder()
                .id(funcionarioDto.getId())
                .nome(funcionarioDto.getNome())
                .cargo(cargoMapper.toEntity(funcionarioDto.getCargo()))
                .usuario(usuarioMapper.toEntity(funcionarioDto.getUsuario()))
                .build();
    }
}
