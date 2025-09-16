package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDTO;
import br.senai.prova_jwt.dto.FuncionarioFiltroDTO;
import br.senai.prova_jwt.mapper.FuncionarioMapper;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public FuncionarioDTO criar(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDTO);
        Funcionario salvo = funcionarioService.criar(funcionario);
        return FuncionarioMapper.toDTO(salvo);
    }

    @PostMapping("/lote")
    public List<FuncionarioDTO> criarEmLote(@Valid @RequestBody List<FuncionarioDTO> funcionariosDTO) {
        List<Funcionario> funcionarios = funcionariosDTO.stream()
                .map(FuncionarioMapper::toEntity)
                .collect(Collectors.toList());

        List<Funcionario> salvos = funcionarioService.criarEmLote(funcionarios);

        return salvos.stream()
                .map(FuncionarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<FuncionarioDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long cargoId,
            Pageable pageable
    ) {
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO();
        filtro.setNome(nome);
        filtro.setCargoId(cargoId);

        Page<Funcionario> page = funcionarioService.pegarFuncionariosPaginado(filtro, pageable);
        return page.map(FuncionarioMapper::toDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(
            @Valid
            @PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO
    ) {
        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDTO);
        Funcionario atualizado = funcionarioService.atualizar(id, funcionario);
        return ResponseEntity.ok(FuncionarioMapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 No Content
    }
}
