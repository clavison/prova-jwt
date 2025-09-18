package br.senai.prova_jwt.dto;

public class FuncionarioFiltroDTO {

    private String nome;
    private Long cargoId;

    public FuncionarioFiltroDTO() {
    }

    public FuncionarioFiltroDTO(String nome, Long cargoId) {
        this.nome = nome;
        this.cargoId = cargoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }
}
