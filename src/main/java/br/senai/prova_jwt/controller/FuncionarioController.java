package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
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
    public FuncionarioDto criar(@Valid @RequestBody FuncionarioDto funcionarioDto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDto);
        Funcionario salvo = funcionarioService.criar(funcionario);
        return FuncionarioMapper.toDto(salvo);
    }

    @PostMapping("/lote")
    public List<FuncionarioDto> criarEmLote(@Valid @RequestBody List<FuncionarioDto> funcionariosDto) {
        List<Funcionario> funcionarios = funcionariosDto.stream()
                .map(FuncionarioMapper::toEntity)
                .collect(Collectors.toList());

        List<Funcionario> salvos = funcionarioService.criarEmLote(funcionarios);

        return salvos.stream()
                .map(FuncionarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<FuncionarioDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long cargoId,
            Pageable pageable
    ) {
        FuncionarioFiltroDto filtro = new FuncionarioFiltroDto();
        filtro.setNome(nome);
        filtro.setCargoId(cargoId);

        Page<Funcionario> page = funcionarioService.pegarFuncionariosPaginado(filtro, pageable);
        return page.map(FuncionarioMapper::toDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(
            @Valid
            @PathVariable Long id,
            @RequestBody FuncionarioDto funcionarioDto
    ) {
        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDto);
        Funcionario atualizado = funcionarioService.atualizar(id, funcionario);
        return ResponseEntity.ok(FuncionarioMapper.toDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
