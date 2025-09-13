package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioFiltroDTO;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecification;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Page<Funcionario> pegarFuncionariosPaginado(FuncionarioFiltroDTO filtro, Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("nome").ascending()
            );
        }
        return funcionarioRepository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable);
    }
}
