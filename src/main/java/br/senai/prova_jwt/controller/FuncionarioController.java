package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioResponse;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.CargoRepository;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public Page<FuncionarioResponse> getAllFuncionarios(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cargo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Funcionario> funcionariosPage;

        if (nome != null && cargo != null) {
            funcionariosPage = funcionarioRepository.findByNomeAndCargoNome(nome, cargo, pageable);
        } else if (nome != null) {
            funcionariosPage = funcionarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else if (cargo != null) {
            funcionariosPage = funcionarioRepository.findByCargoNomeContaining(cargo, pageable);
        } else {
            funcionariosPage = funcionarioRepository.findAll(pageable);
        }

        return funcionariosPage.map(funcionario ->
                new FuncionarioResponse(
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getCargo().getNome(),
                        funcionario.getCargo().getSalario().doubleValue()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> getFuncionarioById(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if (funcionario.isPresent()) {
            Funcionario f = funcionario.get();
            FuncionarioResponse response = new FuncionarioResponse(
                    f.getId(),
                    f.getNome(),
                    f.getCargo().getNome(),
                    f.getCargo().getSalario().doubleValue()
            );
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Funcionario> createFuncionario(@Valid @RequestBody Funcionario funcionario) {
        if (funcionario.getCargo() == null || funcionario.getCargo().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!cargoRepository.existsById(funcionario.getCargo().getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(funcionarioRepository.save(funcionario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @Valid @RequestBody Funcionario funcionarioDetails) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();
            funcionario.setNome(funcionarioDetails.getNome());
            funcionario.setCargo(funcionarioDetails.getCargo());
            return ResponseEntity.ok(funcionarioRepository.save(funcionario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario(@PathVariable Long id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}