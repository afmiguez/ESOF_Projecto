package com.projeto.gestao_explicacoes.services.explicadorServices;

import com.projeto.gestao_explicacoes.exceptions.FalhaCriarException;
import com.projeto.gestao_explicacoes.models.*;
import com.projeto.gestao_explicacoes.models.builders.ExplicadorBuilder;
import com.projeto.gestao_explicacoes.repositories.*;
import com.projeto.gestao_explicacoes.services.explicadorServices.filters.ExplicadorDTO;
import com.projeto.gestao_explicacoes.services.explicadorServices.filters.FilterExplicadorService;
import com.projeto.gestao_explicacoes.services.explicadorServices.filters.FilterObjectExplicador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile(value = "db")
public class ExplicadorServiceDB implements ExplicadorService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private ExplicadorRepo explicadorRepo;
    private HorarioRepo horarioRepo;
    private IdiomaRepo idiomaRepo;
    private AtendimentoRepo atendimentoRepo;
    private CadeiraRepo cadeiraRepo;
    private FilterExplicadorService filterExplicadorService;

    @Autowired
    public ExplicadorServiceDB(ExplicadorRepo explicadorRepo, HorarioRepo horarioRepo, IdiomaRepo idiomaRepo, AtendimentoRepo atendimentoRepo, CadeiraRepo cadeiraRepo, FilterExplicadorService filterExplicadorService) {
        this.explicadorRepo = explicadorRepo;
        this.horarioRepo = horarioRepo;
        this.idiomaRepo = idiomaRepo;
        this.atendimentoRepo = atendimentoRepo;
        this.cadeiraRepo = cadeiraRepo;
        this.filterExplicadorService = filterExplicadorService;
    }

    @Override
    public Set<Explicador> findAll() {
        Set<Explicador> explicadores = new HashSet<>();
        for (Explicador explicador : this.explicadorRepo.findAll()) {
            explicadores.add(explicador);
        }
        return explicadores;
    }

    @Override
    public Optional<Explicador> criarExplicador(Explicador explicador) {

        if(this.explicadorRepo.findByNumero(explicador.getNumero()).isPresent()){

            return Optional.empty();
        }

        Explicador explicadorCriado = this.explicadorRepo.save(explicador);

        return Optional.of(explicadorCriado);
    }

    @Override
    public Optional<ExplicadorDTO> findByNome(String nomeExplicador) {

        Optional<Explicador> optExplicador = this.explicadorRepo.findByNome(nomeExplicador);

        if ( optExplicador.isEmpty() ) {
            return Optional.empty();
        }

        return Optional.of(new ExplicadorDTO(optExplicador.get().getNome(), optExplicador.get().getNumero()));
    }

    @Override
    public Optional<ExplicadorDTO> modificaExplicador(ExplicadorDTO infoExplicador) {
        this.logger.info("No método: ExplicadorServiceDB -> modificaExplicador");

        // Previne que o explicador não tenha numero e nome
        if (infoExplicador.getNumero() == 0 || infoExplicador.getNumero() == null || infoExplicador.getNome() == null) {
            this.logger.info("Explicador sem Nome ou Numero!");
            return Optional.empty();
        }

        Optional<Explicador> optExplicador = this.explicadorRepo.findByNumero(infoExplicador.getNumero());

        // Não existindo o explicador, ele é criado
        if (optExplicador.isEmpty()) {
            this.logger.info("A criar novo explicador!");

            Explicador novoExplicador = new ExplicadorBuilder()
                    .setNome(infoExplicador.getNome())
                    .setNumero(infoExplicador.getNumero())
                    .setHorario(infoExplicador.getHorarios())
                    .setIdiomas(infoExplicador.getIdiomas())
                    .setAtendimentos(infoExplicador.getAtendimentos())
                    .setCadeiras(infoExplicador.getCadeiras())
                    .build();
            this.explicadorRepo.save(novoExplicador);
            return Optional.of(new ExplicadorDTO(
                    novoExplicador.getNome(),
                    novoExplicador.getNumero(),
                    novoExplicador.getHorarios(),
                    novoExplicador.getIdiomas(),
                    novoExplicador.getCadeiras()));
        }

        this.logger.info("Atualizar explicador existente!");

        Explicador explicador = optExplicador.get();
        explicador.setNome(infoExplicador.getNome());
        explicador.setNumero(infoExplicador.getNumero());

        if ( !infoExplicador.getHorarios().isEmpty() ) {
            for (Horario horario: infoExplicador.getHorarios()) {
                if (explicador.containsHorario(horario)) {
                    continue;
                }
                explicador.addHorario(horario);
                this.horarioRepo.save(horario);
            }
        }

        if ( !infoExplicador.getIdiomas().isEmpty() ) {
            for (Idioma idioma : infoExplicador.getIdiomas()) {
                if (explicador.containsIdioma(idioma)) {
                    continue;
                }
                Idioma auxIdioma = new Idioma(idioma.getNome(), idioma.getSigla());
                explicador.addIdioma(auxIdioma);
                this.idiomaRepo.save(auxIdioma);
            }
        }

        if ( !infoExplicador.getAtendimentos().isEmpty() ) {
            for (Atendimento atendimento: infoExplicador.getAtendimentos()) {
                if (explicador.containsAtendimento(atendimento)) {
                    continue;
                }
                explicador.addAtendimento(atendimento);
                this.atendimentoRepo.save(atendimento);
            }
        }

        if ( !infoExplicador.getCadeiras().isEmpty() ) {
            for (Cadeira cadeira: infoExplicador.getCadeiras()) {
                if (explicador.containsCadeira(cadeira)) {
                    continue;
                }
                explicador.addCadeira(cadeira);
                this.cadeiraRepo.save(cadeira);
            }
        }

        this.explicadorRepo.save(explicador);

        return Optional.of(new ExplicadorDTO(
                explicador.getNome(),
                explicador.getNumero(),
                explicador.getHorarios(),
                explicador.getIdiomas(),
                explicador.getAtendimentos(),
                explicador.getCadeiras()));
    }

    @Override
    public Set<ExplicadorDTO> procuraExplicadores(FilterObjectExplicador filterObjectExplicador) {
        this.logger.info("No método: ExplicadorServiceDB -> procuraExplicadores");

        if (filterObjectExplicador.getHoraInicio().getMinute() != 0 || filterObjectExplicador.getHoraFim().getMinute() != 0 ) {
            throw new FalhaCriarException("Só existem horas certas, logo os minutos tem de ser sempre 0!! Ex: 13:00 !!");
        }

        if ( filterObjectExplicador.getHoraInicio() != null && filterObjectExplicador.getHoraFim() != null ) {
            if ( filterObjectExplicador.getHoraInicio().getHour() > filterObjectExplicador.getHoraFim().getHour() ) {
                throw new FalhaCriarException("A Hora de Inicio não pode ser superior a Hora de Fim!!");
            }
        }

        Set<Explicador> todosExplicadores = this.findAll();

        //return this.filterExplicadorService.preFilter(todosExplicadores, filterObjectExplicador);

        Set<ExplicadorDTO> explicadorTransfer = new HashSet<>();
        for (Explicador explicador : this.filterExplicadorService.preFilter(todosExplicadores, filterObjectExplicador)) {
            explicadorTransfer.add(explicador.copyToExplicadorDTO());
        }
        return explicadorTransfer;
    }

}
