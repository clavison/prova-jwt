package br.senai.prova_jwt.domains.cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    public CargoDTO salvar(CargoDTO dto) {
        Cargo cargo = CargoMapper.toEntity(dto);
        Cargo salvo = repository.save(cargo);
        return CargoMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public CargoDTO buscarPorId(Long id) {
        Cargo cargo = repository.findById(id).orElse(null);
        return CargoMapper.toDto(cargo);
    }

    public Page<CargoDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cargo> cargos = repository.findAll(pageable);
        return cargos.map(CargoMapper::toDto);
    }

    public Page<CargoDTO> listarComFiltros(CargoFiltroDTO filtro, Pageable pageable) {
        Page<Cargo> cargos = repository.findAll(CargoSpecification.comFiltros(filtro), pageable);
        return cargos.map(CargoMapper::toDto);
    }
}
