package br.senai.prova_jwt.helpers.bases;


import br.senai.prova_jwt.exceptions.RegistroNaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@Slf4j
public class BaseService<T, ID, R extends BaseRepository<T, ID>> implements BaseServiceInterface<T, ID> {

    protected final R repo;
    private final Class<T> clazz;

    public BaseService(R repo, Class<T> clazz) {
        this.repo = repo;
        this.clazz = clazz;
    }

    @Override
    @Transactional
    public T salvar(T t) {
        return repo.save(t);
    }

    @Override
    public T buscarPorId(ID id) {
        validarId(id);
        return repo.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        clazz.getSimpleName() + " " + id + " não foi encontrado"));
    }

    @Override
    public Page<T> buscarPorSpecification(String parametro, Pageable pageable) {
        Specification<T> spec = (parametro == null || parametro.isBlank()) ? null : toSpecification(parametro);
        if (spec != null) log.debug("Aplicando specification: {}", parametro);
        return repo.findAll(spec, pageable);
    }
    @Override
    public Page<T> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    @Transactional
    public T excluir(ID id) {
        T entidade = buscarPorId(id);
        repo.delete(entidade);
        return entidade;
    }

    @Transactional
    @Override
    public T alterar(T novo, ID id) {
        validarId(id);
        T existente = buscarPorId(id);
        copiarCampos(novo, existente);
        return repo.save(existente);
    }

    private void validarId(ID id) {
        if (id == null) throw new IllegalArgumentException("O id é obrigatório");
    }

    private void copiarCampos(T source, T target) {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equals("id")) {
                try {
                    Object valor = field.get(source);
                    if (valor != null) {
                        field.set(target, valor);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Erro ao copiar campo '{}': {}", field.getName(), e.getMessage());
                    throw new RuntimeException("Erro ao atualizar registro", e);
                }
            }
        }
    }
}

