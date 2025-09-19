package com.prova.rayssa.controller;

import com.prova.rayssa.dto.FuncionarioDTO;
import com.prova.rayssa.dto.FuncionarioFiltroDTO;
import com.prova.rayssa.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioAdvancedController {
    
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/paginado")
    public ResponseEntity<Map<String, Object>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cargoNome,
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) BigDecimal salarioMinimo,
            @RequestParam(required = false) BigDecimal salarioMaximo) {
        
        // Criar direção da ordenação
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        // Criar Pageable
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        // Criar filtro
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO(
            nome, cargoNome, cargoId, salarioMinimo, salarioMaximo
        );
        
        // Buscar dados
        Page<FuncionarioDTO> funcionarios = funcionarioService.buscarComFiltros(filtro, pageable);
        
        // Preparar resposta
        Map<String, Object> response = new HashMap<>();
        response.put("funcionarios", funcionarios.getContent());
        response.put("currentPage", funcionarios.getNumber());
        response.put("totalItems", funcionarios.getTotalElements());
        response.put("totalPages", funcionarios.getTotalPages());
        response.put("pageSize", funcionarios.getSize());
        response.put("hasNext", funcionarios.hasNext());
        response.put("hasPrevious", funcionarios.hasPrevious());
        response.put("isFirst", funcionarios.isFirst());
        response.put("isLast", funcionarios.isLast());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/buscar")
    public ResponseEntity<List<FuncionarioDTO>> buscarComFiltro(
            @RequestBody FuncionarioFiltroDTO filtro) {
        
        List<FuncionarioDTO> funcionarios = funcionarioService.buscarComFiltrosSemPaginacao(filtro);
        return ResponseEntity.ok(funcionarios);
    }
    
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        List<FuncionarioDTO> todos = funcionarioService.listarTodos();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFuncionarios", todos.size());
        
        // Agrupar por cargo
        Map<String, Long> porCargo = new HashMap<>();
        todos.forEach(f -> {
            String cargo = f.getCargo() != null ? f.getCargo().getNome() : "Sem cargo";
            porCargo.put(cargo, porCargo.getOrDefault(cargo, 0L) + 1);
        });
        stats.put("porCargo", porCargo);
        
        // Calcular salário médio por cargo
        Map<String, BigDecimal> salarioMedioPorCargo = new HashMap<>();
        porCargo.keySet().forEach(cargo -> {
            BigDecimal salarioMedio = todos.stream()
                .filter(f -> f.getCargo() != null && cargo.equals(f.getCargo().getNome()))
                .map(f -> f.getCargo().getSalario())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(porCargo.get(cargo)), 2, java.math.RoundingMode.HALF_UP);
            salarioMedioPorCargo.put(cargo, salarioMedio);
        });
        stats.put("salarioMedioPorCargo", salarioMedioPorCargo);
        
        return ResponseEntity.ok(stats);
    }
}