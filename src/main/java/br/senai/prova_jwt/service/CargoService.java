package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoFiltroDTO;
import br.senai.prova_jwt.dto.specifications.CargoSpecification;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo criar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public List<Cargo> criarEmLote(List<Cargo> cargos) {
        return cargoRepository.saveAll(cargos);
    }

    public Page<Cargo> pegarCargosPaginado(CargoFiltroDTO filtro, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
        }
        return cargoRepository.findAll(CargoSpecification.comFiltros(filtro), pageable);
    }

    public Cargo atualizar(Long id, Cargo cargoAtualizado) {
        Cargo cargoExistente = cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo não encontrado com id: " + id));

        cargoExistente.setNome(cargoAtualizado.getNome());
        cargoExistente.setSalario(cargoAtualizado.getSalario());

        return cargoRepository.save(cargoExistente);
    }

    public void deletar(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new RuntimeException("Cargo não encontrado com id: " + id);
        }
        cargoRepository.deleteById(id);
    }
}

