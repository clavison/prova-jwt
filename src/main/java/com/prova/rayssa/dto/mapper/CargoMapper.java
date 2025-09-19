package com.prova.rayssa.dto.mapper;

import com.prova.rayssa.dto.CargoDTO;
import com.prova.rayssa.model.Cargo;

public class CargoMapper {
    
    public static CargoDTO toDto(Cargo cargo) {
        if (cargo == null) return null;
        
        CargoDTO dto = new CargoDTO();
        dto.setId(cargo.getId());
        dto.setNome(cargo.getNome());
        dto.setSalario(cargo.getSalario());
        
        return dto;
    }

    public static Cargo toEntity(CargoDTO dto) {
        if (dto == null) return null;
        
        Cargo cargo = new Cargo();
        cargo.setId(dto.getId());
        cargo.setNome(dto.getNome());
        cargo.setSalario(dto.getSalario());
        
        return cargo;
    }
    
    public static void updateEntityFromDto(CargoDTO dto, Cargo cargo) {
        if (dto == null || cargo == null) return;
        
        cargo.setNome(dto.getNome());
        cargo.setSalario(dto.getSalario());
    }
}
