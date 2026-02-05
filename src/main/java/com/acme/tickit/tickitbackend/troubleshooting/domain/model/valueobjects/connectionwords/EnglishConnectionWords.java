package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.connectionwords;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * English connection words (stop words) to ignore when analyzing sentences,
 * e.g. when comparing issue report descriptions for coincidence detection.
 * Includes articles, conjunctions, prepositions, pronouns, and common auxiliaries.
 */
public final class EnglishConnectionWords {

    private static final Set<String> WORDS = Collections.unmodifiableSet(Stream.of(
            // Articles
            "a", "an", "the",
            // Conjunctions
            "and", "but", "or", "nor", "so", "yet", "for", "because", "as", "if", "than",
            "although", "though", "while", "whereas", "until", "unless", "when", "whether",
            "after", "before", "since", "once", "that", "which", "who", "whom", "whose",
            // Prepositions
            "of", "in", "to", "for", "with", "on", "at", "by", "from", "about", "into",
            "through", "during", "before", "after", "above", "below", "between", "under",
            "again", "further", "then", "once", "here", "there", "when", "where", "why",
            "how", "all", "each", "few", "more", "most", "other", "some", "such", "no",
            "only", "own", "same", "than", "too", "very", "just", "can", "will", "shall",
            "may", "must", "ought", "could", "would", "should", "might", "need", "dare",
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your",
            "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her",
            "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs",
            "themselves", "what", "which", "this", "these", "those", "am", "is", "are",
            "was", "were", "been", "being", "have", "has", "had", "having", "do", "does",
            "did", "doing", "up", "down", "out", "off", "over"
    ).collect(Collectors.toSet()));

    private EnglishConnectionWords() {
        // Utility class - prevent instantiation
    }

    /**
     * Returns the set of English connection words to ignore during text analysis.
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
