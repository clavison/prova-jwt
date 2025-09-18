package br.senai.prova_jwt.helpers.bases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseServiceInterface<T, ID> {

    T salvar(T t);

    T buscarPorId(ID id);

    T excluir(ID id);

    T alterar(T novo, ID id);

    Page<T> buscarPorSpecification(String parametro, Pageable pageable);

    Page<T> listarPaginado(Pageable pageable);
    
}