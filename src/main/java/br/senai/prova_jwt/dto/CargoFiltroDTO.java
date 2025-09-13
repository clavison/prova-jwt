package br.senai.prova_jwt.dto;

public class CargoFiltroDTO {

    private String nome;
    private Double salarioMin;
    private Double salarioMax;

    public CargoFiltroDTO() {
    }

    public CargoFiltroDTO(String nome, Double salarioMin, Double salarioMax) {
        this.nome = nome;
        this.salarioMin = salarioMin;
        this.salarioMax = salarioMax;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalarioMin() {
        return salarioMin;
    }

    public void setSalarioMin(Double salarioMin) {
        this.salarioMin = salarioMin;
    }

    public Double getSalarioMax() {
        return salarioMax;
    }

    public void setSalarioMax(Double salarioMax) {
        this.salarioMax = salarioMax;
    }
}
