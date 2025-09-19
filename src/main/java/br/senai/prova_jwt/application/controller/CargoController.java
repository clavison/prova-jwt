package br.senai.prova_jwt.application.controller;

import br.senai.prova_jwt.application.dto.CargoDto;
import br.senai.prova_jwt.application.dto.filtros.CargoFiltroDto;
import br.senai.prova_jwt.domain.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService service;

    @PostMapping
    public ResponseEntity<CargoDto> criar(@RequestBody CargoDto dto) {
        CargoDto salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        CargoDto dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<CargoDto>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    // Endpoint GET para listar cargos com filtros opcionais e paginação
    @GetMapping
    public Page<CargoDto> listar(
            // Parâmetros opcionais para filtrar a busca
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            // Objeto Pageable permite paginação e ordenação automáticas do Spring
            Pageable pageable
    ) {
        // Cria um objeto de filtro e preenche com os parâmetros recebidos
        CargoFiltroDto filtro = new CargoFiltroDto();
        filtro.setNome(nome);
        filtro.setDescricao(descricao);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);

        // Chama o serviço para listar os cargos aplicando filtros e paginação
        return service.listarComFiltros(filtro, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody CargoDto dto) {
        if (service.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(service.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}