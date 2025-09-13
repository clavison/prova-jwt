package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoFiltroDto;
import br.senai.prova_jwt.dto.specifications.CargoSpecifications;
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

    public Page<Cargo> pegarCargosPaginado(CargoFiltroDto filtro, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
        }
        return cargoRepository.findAll(CargoSpecifications.comFiltros(filtro), pageable);
    }
}
