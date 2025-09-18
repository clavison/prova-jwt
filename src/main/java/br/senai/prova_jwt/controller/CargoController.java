package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.domain.cargo.Cargo;
import br.senai.prova_jwt.domain.cargo.CargoDto;
import br.senai.prova_jwt.domain.cargo.CargoMapper;
import br.senai.prova_jwt.domain.cargo.CargoService;
import br.senai.prova_jwt.helpers.bases.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cargos")
public class CargoController extends BaseController<Cargo, CargoDto, Long, CargoMapper> {

    public CargoController(CargoMapper mapper,
                           CargoService cargoService) {
        super(mapper, cargoService);
    }
}
