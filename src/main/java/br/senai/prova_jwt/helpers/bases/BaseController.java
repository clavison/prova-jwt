package br.senai.prova_jwt.helpers.bases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, DTO, ID, Mapper extends BaseMapper<T, DTO>> {

    private final Mapper mapper;

    private final BaseServiceInterface<T, ID> baseServiceInterface;

    private static final int PAGE_SIZE = 15;

    public BaseController(Mapper mapper, BaseServiceInterface<T, ID>  baseServiceInterface) {
        this.mapper = mapper;
        this.baseServiceInterface = baseServiceInterface;
    }

    @PostMapping
    public DTO salvar(@RequestBody DTO dto) {
        return mapper.toDTO(baseServiceInterface.salvar(mapper.toEntity(dto)));
    }

    @GetMapping("/{id}")
    public DTO buscarPorId(@PathVariable(name = "id") ID id) {
        return mapper.toDTO(baseServiceInterface.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public DTO deletarPorId(@PathVariable(name = "id") ID id) {
        return mapper.toDTO(baseServiceInterface.excluir(id));
    }

    @PutMapping("/{id}")
    public DTO alterarPorId(@RequestBody DTO dto, @PathVariable ID   id) {
        return mapper.toDTO(baseServiceInterface.alterar(mapper.toEntity(dto), id));
    }

    @GetMapping(params = "search")
    public ResponseEntity<Page<DTO>> buscarPorSpecification(@RequestParam(name = "search") String search, Pageable pageable) {
        Page<DTO> itens = baseServiceInterface.buscarPorSpecification(search, pageable).map(mapper::toDTO);
        if (!itens.hasContent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itens);
    }

    @GetMapping
    public ResponseEntity<Page<DTO>> listarPaginado(@PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        Page<DTO> itens = baseServiceInterface.listarPaginado(pageable).map(mapper::toDTO);
        if (!itens.hasContent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itens);
    }

}