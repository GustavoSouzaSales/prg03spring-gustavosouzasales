package br.com.ifba.curso.service;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.repository.CursoRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


// são feitas as validações, regras de negócio.
@Slf4j // libera o uso do log.
@Service
public class CursoService implements CursoIService {
    
    private static final Logger logger = LoggerFactory.getLogger(CursoService.class);
    
    private final CursoRepository repository; // reduz o acoplamento, deixando mais organizado.

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Curso salvar(Curso curso) {

    log.info("Salvando curso: nome='{}', codigo='{}'", curso.getNome(), curso.getCodigo());

    if (curso.getNome() == null || curso.getNome().isBlank()) {
        log.warn("Tentativa de salvar curso com nome inválido");
        throw new RuntimeException("Nome inválido!");
    }

    if (curso.getCodigo() == null || curso.getCodigo().isBlank()) {
        log.warn("Tentativa de salvar curso com código inválido");
        throw new RuntimeException("Código inválido!");
    }

    if (repository.buscarPorNome(curso.getNome()).isPresent()) {
        log.error("Nome repetido ao salvar curso: {}", curso.getNome());
        throw new RuntimeException("Já existe um curso com esse nome!");
    }

    if (repository.buscarPorCodigo(curso.getCodigo()).isPresent()) {
        log.error("Código repetido ao salvar curso: {}", curso.getCodigo());
        throw new RuntimeException("Já existe um curso com esse código!");
    }

    log.info("Curso salvo com sucesso!");
    return repository.save(curso);
}


    @Override
    public Curso atualizar(Curso curso) {
        
        log.info("Tentando atualizar curso ID={}, Nome='{}', Código='{}'", curso.getId(), curso.getNome(), curso.getCodigo());

        if (curso.getId() == null) {
            log.warn("Falha ao atualizar — ID está nulo");
            throw new RuntimeException("ID não pode ser nulo!");
        }

        // Nome repetido
        Optional<Curso> existenteNome = repository.buscarPorNome(curso.getNome());
        if (existenteNome.isPresent() && !existenteNome.get().getId().equals(curso.getId())) {
            log.warn("Conflito ao atualizar — já existe outro curso com nome '{}'", curso.getNome());
            throw new RuntimeException("Outro curso já usa esse nome!");
        }

        // Código repetido
        Optional<Curso> existenteCodigo = repository.buscarPorCodigo(curso.getCodigo());
        if (existenteCodigo.isPresent() && !existenteCodigo.get().getId().equals(curso.getId())) {
            log.warn("Conflito ao atualizar — já existe outro curso com código '{}'", curso.getCodigo());
            throw new RuntimeException("Outro curso já usa esse código!");
        }

        log.info("Curso ID={} atualizado com sucesso!", curso.getId());
        return repository.save(curso);
    }

    @Override
    public void deletar(Curso curso) {
        log.warn("Deletando curso id={}", curso.getId());
        repository.delete(curso);
    }

    @Override
    public List<Curso> listarTodos() {
        log.info("Listando todos os cursos");
        return repository.findAll();
    }

    @Override
    public Curso buscarPorId(Long id) {
        log.info("Buscando curso por ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                log.error("Curso ID={} não encontrado!", id);
                return new RuntimeException("Curso não encontrado!");
            });
    }

    @Override
    public Curso buscarPorNome(String nome) {
        logger.info("Buscando curso pelo nome: {}", nome);
        return repository.buscarPorNome(nome)
                .orElseThrow(() -> {
                logger.warn("Curso não encontrado com o nome: {}", nome);
                return new RuntimeException("Curso não encontrado!");
            });
    }

    @Override
    public Curso buscarPorCodigo(String codigo) {
        logger.info("Buscando curso pelo código: {}", codigo);
        return repository.buscarPorCodigo(codigo)
                .orElseThrow(() -> {
                logger.warn("Curso não encontrado com o código: {}", codigo);
                return new RuntimeException("Curso não encontrado!");
            });
    }
}
