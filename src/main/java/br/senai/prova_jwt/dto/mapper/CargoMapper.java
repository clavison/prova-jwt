package br.senai.prova_jwt.dto.mapper;


import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.model.Cargo;

public class CargoMapper {

    public static CargoDto toDto(Cargo cargo) {
        if (cargo == null){
            return null;
        }
        return new CargoDto(
                cargo.getId(),
                cargo.getNome(),
                cargo.getSalario()
        );
    }

    public static Cargo toEntity(CargoDto cargoDto) {
        if (cargoDto == null) {
            return null;
        }
        Cargo cargo = new Cargo();
        cargo.setId(cargoDto.getId());
        cargo.setNome(cargoDto.getNome());
        cargo.setSalario(cargoDto.getSalario());
        return cargo;
    }

}
