package br.senai.prova_jwt.domain.cargo;


import br.senai.prova_jwt.helpers.bases.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class CargoMapper implements BaseMapper<Cargo, CargoDto> {

    @Override
    public CargoDto toDTO(Cargo cargo) {
        return CargoDto.builder()
                .id(cargo.getId())
                .nome(cargo.getNome())
                .salario(cargo.getSalario())
                .build();
    }

    @Override
    public Cargo toEntity(CargoDto cargoDto) {
        return Cargo.builder()
                .id(cargoDto.getId())
                .nome(cargoDto.getNome())
                .salario(cargoDto.getSalario())
                .build();
    }

}
