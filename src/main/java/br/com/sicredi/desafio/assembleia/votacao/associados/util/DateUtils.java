package br.com.sicredi.desafio.assembleia.votacao.associados.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DateUtils {

    public static boolean horarioEntrePeriodos(LocalDateTime horario, LocalDateTime inicio, LocalDateTime termino) {
        return !horario.isBefore(inicio) && !horario.isAfter(termino);
    }
}