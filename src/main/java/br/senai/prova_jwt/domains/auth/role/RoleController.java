package br.senai.prova_jwt.domains.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping
    public ResponseEntity<RoleDTO> criar(@RequestBody RoleDTO role) {
        RoleDTO salvo = service.salvar(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> buscarPorId(@PathVariable Long id) {
        RoleDTO role = service.buscarPorId(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public Page<RoleDTO> buscarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listar(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> atualizar(@PathVariable Long id, @RequestBody RoleDTO role) {
        RoleDTO roleBusca = service.buscarPorId(id);
        if (roleBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(service.atualizar(id, role));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public Page<RoleDTO> listar(
            @RequestParam(required = false) String descricao,
            Pageable pageable
    ) {
        RoleFiltroDTO filtro = new RoleFiltroDTO();
        filtro.setDescricao(descricao);
        return service.listarComFiltros(filtro, pageable);
    }

    /**
     * Cria as roles padrão (USER e ADMIN) no banco de dados.
     * Endpoint público - não requer autenticação.
     * Só cria as roles se ainda não existirem no banco.
     */
    @PostMapping("/role-setup")
    public ResponseEntity<String> criarRolesPadrao() {
        service.criarRolesPadrao();
        return ResponseEntity.ok("Roles padrão (USER e ADMIN) criadas com sucesso!");
    }
}