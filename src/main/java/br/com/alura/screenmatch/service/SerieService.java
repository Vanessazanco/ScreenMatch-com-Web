package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.util.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());

    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(Conversor::serieParaSerieDTO)
                .toList();
    }


    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional <Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getAtores(),
                    s.getGenero(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional <Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(ep -> new EpisodioDTO(ep.getTemporada(),
                            ep.getNumeroEpisodio(),
                            ep.getTitulo())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obeterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(epi -> new EpisodioDTO(epi.getTemporada(),
                        epi.getNumeroEpisodio(),
                        epi.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeCategoria) {
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        return converteDados(repositorio.findByGenero(categoria));
    }

    public SerieDTO deletar(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            repositorio.delete(s);
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getAtores(),
                    s.getGenero(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }
}
