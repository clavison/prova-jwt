package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecifications;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criar(Funcionario funcionario) {

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> criarEmLote(List<Funcionario> funcionarios) {
        return funcionarioRepository.saveAll(funcionarios);
    }

    public Page<Funcionario> pegarFuncionariosPaginado(FuncionarioFiltroDto filtro, Pageable pageable) {
        return funcionarioRepository.findAll(FuncionarioSpecifications.comFiltros(filtro), pageable);
    }

    public Funcionario atualizar(Long id, Funcionario funcionarioParaAtualizar) {
        return funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {

                    funcionarioExistente.setNome(funcionarioParaAtualizar.getNome());
                    funcionarioExistente.setCargo(funcionarioParaAtualizar.getCargo());

                    return funcionarioRepository.save(funcionarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id: " + id));
    }

    public void deletar(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com o id: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
