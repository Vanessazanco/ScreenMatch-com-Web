package br.com.alura.screenmatch.util;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;

public class Conversor {
    public static SerieDTO serieParaSerieDTO(Serie serie){
        Long id = serie.getId();
        String titulo = serie.getTitulo();
        Integer totalTemporadas = serie.getTotalTemporadas();
        Double avaliacao = serie.getAvaliacao();
        String atores = serie.getAtores();
        Categoria genero = serie.getGenero();
        String poster = serie.getPoster();
        String sinopse = serie.getSinopse();

        return new SerieDTO(id, titulo, totalTemporadas, avaliacao, atores, genero, poster, sinopse);
    }
}
