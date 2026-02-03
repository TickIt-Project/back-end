package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.connectionwords;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spanish connection words (stop words) to ignore when analyzing sentences,
 * e.g. when comparing issue report descriptions for coincidence detection.
 * Includes articles, conjunctions, prepositions, pronouns, and common adverbs.
 */
public final class SpanishConnectionWords {

    private static final Set<String> WORDS = Collections.unmodifiableSet(Stream.of(
            // Articles
            "el", "la", "los", "las", "un", "una", "unos", "unas", "lo", "al", "del",
            // Conjunctions
            "y", "e", "o", "u", "pero", "aunque", "sino", "porque", "si", "que", "como",
            "cuando", "donde", "mientras", "pues", "así", "puesto", "aun", "aún", "ni",
            "mas", "más", "conque", "siempre", "nunca", "también", "tampoco", "además",
            // Prepositions
            "a", "de", "en", "con", "por", "para", "sin", "sobre", "entre", "durante",
            "desde", "hasta", "ante", "bajo", "hacia", "mediante", "según", "tras",
            "contra", "versus", "vía", "excepto", "salvo", "menos", "más",
            // Pronouns
            "yo", "tú", "él", "ella", "nosotros", "nosotras", "vosotros", "vosotras",
            "ellos", "ellas", "me", "te", "se", "nos", "os", "les", "le", "lo", "la",
            "mi", "tu", "su", "mis", "tus", "sus", "nuestro", "nuestra", "nuestros",
            "nuestras", "vuestro", "vuestra", "vuestros", "vuestras", "suyo", "suya",
            "suyos", "suyas", "mío", "mía", "tuyo", "tuya", "este", "esta", "estos",
            "estas", "ese", "esa", "esos", "esas", "aquel", "aquella", "aquellos",
            "aquellas", "algo", "alguien", "alguno", "alguna", "algunos", "algunas",
            "ninguno", "ninguna", "ningunos", "ningunas", "todo", "toda", "todos",
            "todas", "otro", "otra", "otros", "otras", "mismo", "misma", "mismos",
            "mismas", "qué", "quién", "quiénes", "cuál", "cuáles", "cuánto", "cuánta",
            "cuántos", "cuántas", "cuyo", "cuya", "cuyos", "cuyas",
            // Common adverbs and auxiliaries
            "muy", "más", "menos", "bien", "mal", "aquí", "ahí", "allí", "ahora",
            "hoy", "ayer", "antes", "después", "luego", "entonces", "así", "casi",
            "bastante", "demasiado", "poco", "mucho", "solo", "solamente", "tan",
            "tanto", "ya", "aún", "todavía", "siempre", "nunca", "jamás", "a veces",
            "ser", "estar", "haber", "tener", "hacer", "poder", "deber", "querer",
            "es", "son", "era", "eran", "fue", "fueron", "ha", "han", "había", "habían",
            "he", "has", "hemos", "habéis", "fue", "fueron", "será", "serán",
            "está", "están", "estaba", "estaban", "estuvo", "estuvieron", "estará",
            "hay", "había", "hubo", "habrá", "tiene", "tienen", "tenía", "tenían"
    ).collect(Collectors.toSet()));

    private SpanishConnectionWords() {
        // Utility class - prevent instantiation
    }

    /**
     * Returns the set of Spanish connection words to ignore during text analysis.
     * All words are in lowercase for case-insensitive comparison.
     */
    public static Set<String> getWords() {
        return WORDS;
    }

    /**
     * Returns true if the given word (case-insensitive) should be ignored during analysis.
     */
    public static boolean shouldIgnore(String word) {
        return word != null && WORDS.contains(word.toLowerCase().trim());
    }
}
