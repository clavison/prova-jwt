package br.senai.prova_jwt.helpers.bases;

public interface BaseMapper<T, DTO> {

    DTO toDTO(T t);

    T toEntity(DTO dto);
}
