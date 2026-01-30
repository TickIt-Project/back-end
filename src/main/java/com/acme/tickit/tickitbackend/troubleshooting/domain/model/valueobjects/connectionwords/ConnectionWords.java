package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.connectionwords;

import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Language;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Provides access to connection words (stop words) by language for text analysis.
 * Used when comparing issue report descriptions to ignore words that add little semantic value.
 */
public final class ConnectionWords {

    private static final String WORD_DELIMITER = "\\s+";
    private static final String PUNCTUATION_PATTERN = "^\\p{Punct}+|\\p{Punct}+$";

    private ConnectionWords() {
        // Utility class - prevent instantiation
    }

    /**
     * Returns the set of connection words for the given language.
     *
     * @param language the language (EN, ES)
     * @return unmodifiable set of words to ignore during analysis
     * @throws IllegalArgumentException if language is null
     */
    public static Set<String> getWords(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        return switch (language) {
            case EN -> EnglishConnectionWords.getWords();
            case ES -> SpanishConnectionWords.getWords();
        };
    }

    /**
     * Returns true if the given word should be ignored when analyzing text in the specified language.
     */
    public static boolean shouldIgnore(String word, Language language) {
        if (language == null) {
            return false;
        }
        return switch (language) {
            case EN -> EnglishConnectionWords.shouldIgnore(word);
            case ES -> SpanishConnectionWords.shouldIgnore(word);
        };
    }

    /**
     * Extracts meaningful words from a sentence, filtering out connection words.
     *
     * @param sentence the text to analyze (e.g., "I have a problem with my email today")
     * @param language the language (EN, ES)
     * @return array of words that should not be ignored (e.g., ["problem", "email", "today"])
     */
    public static String[] filterMeaningfulWords(String sentence, Language language) {
        if (sentence == null || sentence.isBlank()) {
            return new String[0];
        }
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        Set<String> stopWords = getWords(language);
        return Arrays.stream(sentence.split(WORD_DELIMITER))
                .map(word -> word.replaceAll(PUNCTUATION_PATTERN, ""))
                .filter(word -> !word.isBlank())
                .filter(word -> !stopWords.contains(word.toLowerCase()))
                .map(String::toLowerCase)
                .distinct()
                .toArray(String[]::new);
    }

    /**
     * Finds meaningful words that appear in both sentences, useful for coincidence detection.
     *
     * @param sentence1 first sentence to compare
     * @param sentence2 second sentence to compare
     * @param language  the language (EN, ES)
     * @return array of meaningful words present in both sentences
     */
    public static String[] findCommonMeaningfulWords(String sentence1, String sentence2, Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        Set<String> words1 = new LinkedHashSet<>(Arrays.asList(filterMeaningfulWords(sentence1, language)));
        Set<String> words2 = Set.of(filterMeaningfulWords(sentence2, language));
        return words1.stream()
                .filter(words2::contains)
                .toArray(String[]::new);
    }
}
