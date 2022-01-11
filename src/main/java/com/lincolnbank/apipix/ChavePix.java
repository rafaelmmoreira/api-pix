package com.lincolnbank.apipix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChavePix {

    enum TipoChave {
        INVALIDA, CPF, EMAIL, TELEFONE, ALEATORIA
    };

    public static final Pattern CPF_REGEX = Pattern.compile("(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|"+
        "(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)|(^\\d{11}$)|(^\\d{14}$)");

    public static final Pattern TELEFONE_REGEX = Pattern.compile("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]"+
        "\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$");

    public static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", 
        Pattern.CASE_INSENSITIVE);

    public static final Pattern ALEATORIA_REGEX = Pattern.compile("^[A-F0-9]{8}\\-[A-F0-9]{4}\\-[A-F0-9]{4}\\-[A-F0-9]{4}\\-"
    +"[A-F0-9]{12}$", Pattern.CASE_INSENSITIVE);

/**
 * Foram utilizadas as regras determinadas pelo BACEN para formatação de chave Pix:
 * https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf
 * 
 * E-mail: formato fulano_da_silva.recebedor@exemplo.com
 * CPF/CNPJ: Sequência de 11 ou 14 dígitos numéricos, sem pontuação
 * Telefone: padrão internacional, símbolo "+" seguido de 13 dígitos numéricos
 * Chave aleatória: formato 123e4567-e12b-12d1-a456-426655440000
 * 
 * @param chave String contendo a chave Pix
 */
    public static TipoChave determinaTipoChave(String chave) {  
        
        Matcher matcher = CPF_REGEX.matcher(chave);
        if (matcher.find()) return TipoChave.CPF;

        matcher = TELEFONE_REGEX.matcher(chave);
        if (matcher.find()) return TipoChave.TELEFONE;

        matcher = EMAIL_REGEX.matcher(chave);
        if (matcher.find()) return TipoChave.EMAIL;

        matcher = ALEATORIA_REGEX.matcher(chave);
        if (matcher.find()) return TipoChave.ALEATORIA;

        return TipoChave.INVALIDA;

    }
};
