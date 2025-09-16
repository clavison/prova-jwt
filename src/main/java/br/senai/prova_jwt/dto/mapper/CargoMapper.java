package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.model.Cargo;

public class CargoMapper {

    public static CargoDto converterParaDto(Cargo cargo) {
        if (cargo == null)
            return null;
        CargoDto dto = new CargoDto();
        dto.setId(cargo.getIdCargo());
        dto.setNome(cargo.getNome());
        dto.setDescricao(cargo.getDescricao());
        dto.setSalarioBase(cargo.getSalarioBase());
        return dto;
    }

    public static Cargo converterParaEntidade(CargoDto dto) {
        if (dto == null)
            return null;
        Cargo c = new Cargo();
        c.setIdCargo(dto.getId());
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        c.setSalarioBase(dto.getSalarioBase());
        return c;
    }

    // aliases
    public static CargoDto toDto(Cargo c) {
        return converterParaDto(c);
    }

    public static Cargo toEntity(CargoDto d) {
        return converterParaEntidade(d);
    }
}