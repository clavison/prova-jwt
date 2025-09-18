package br.senai.prova_jwt.domain.cargo;

import br.senai.prova_jwt.helpers.bases.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl extends BaseService<Cargo, Long, CargoRepository> implements CargoService {

    public CargoServiceImpl(CargoRepository repo) {
        super(repo, Cargo.class);
    }

}
