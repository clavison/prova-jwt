package br.senai.prova_jwt.mapper;

import br.senai.prova_jwt.application.dto.CargoDto;
import br.senai.prova_jwt.domain.model.Cargo;

public class CargoMapper {

    //
    public static CargoDto toDto(Cargo cargo) {
        if (cargo == null) return null;
        return new CargoDto(
                cargo.getId(),
                cargo.getNome(),
                cargo.getDescricao(),
                cargo.getSalario()
        );
    }

    // converter para entidade
    public static Cargo toEntity(CargoDto dto) {
        if (dto == null) return null;
        Cargo cargo = new Cargo();
        cargo.setId(dto.getId());
        cargo.setNome(dto.getNome());
        cargo.setDescricao(dto.getDescricao());
        cargo.setSalario(dto.getSalario());
        return cargo;
    }
}