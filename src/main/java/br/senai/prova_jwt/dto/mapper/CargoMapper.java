package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.model.Cargo;

public class CargoMapper {

    public static CargoDto toDTO(Cargo cargo) {
        if (cargo == null) {
            return null;
        }
        return new CargoDto(
                cargo.getId(),
                cargo.getNome(),
                cargo.getSalario(),
                cargo.getFuncionarios() != null
                        ? cargo.getFuncionarios().stream()
                        .map(FuncionarioMapper::toDTO)
                        .toList()
                        : null
        );
    }

    public static Cargo toEntity(CargoDto dto) {
        if (dto == null) {
            return null;
        }
        Cargo cargo = new Cargo();
        cargo.setId(dto.getId());
        cargo.setNome(dto.getNome());
        cargo.setSalario(dto.getSalario());
        return cargo;
    }

}