package br.senai.prova_jwt.domains.cargo;

public class CargoMapper {

    public static CargoDTO toDto(Cargo cargo) {
        if (cargo == null) return null;
        return new CargoDTO(
                cargo.getId(),
                cargo.getNome(),
                cargo.getSalario()
        );
    }

    public static Cargo toEntity(CargoDTO dto) {
        if (dto == null) return null;
        Cargo cargo = new Cargo();
        cargo.setId(dto.getId());
        cargo.setNome(dto.getNome());
        cargo.setSalario(dto.getSalario());
        return cargo;
    }
}
