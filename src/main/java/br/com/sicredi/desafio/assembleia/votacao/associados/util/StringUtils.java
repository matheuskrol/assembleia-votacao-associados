package br.com.sicredi.desafio.assembleia.votacao.associados.util;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;

@UtilityClass
public class StringUtils {

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}