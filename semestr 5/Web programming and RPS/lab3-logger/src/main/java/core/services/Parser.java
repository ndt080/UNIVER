package core.services;

import core.consts.Locales;
import core.models.Sentence;
import core.models.exception.FileException;
import core.models.exception.ParsingException;
import core.models.exception.UnresolvedLocaleException;
import org.apache.logging.log4j.LogManager;

import java.io.File;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

/**
 * Parser service
 */
public class Parser {
    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(Parser.class);

    /**
     * Parsing file text to ArrayList of sentences with Locale
     *
     * @param file         input file
     * @param parserLocale parserLocale of text
     * @return ArrayList of sentences
     */
    public ArrayList<Sentence> textToSentencesWithLocale(File file, Locales parserLocale)
            throws ParsingException, FileException, UnresolvedLocaleException {
        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        StringBuilder text = new StringBuilder();

        try {
            Logger.info("Opening file...");
            Scanner sc = new Scanner(file);

            Logger.info("Read file...");
            while (sc.hasNextLine()) {
                text.append(sc.nextLine()).append(" ");
            }

            Logger.info("File has been read.");
            sc.close();
        } catch (IOException e) {
            throw new FileException("Unable to get text from file.", e);
        }

        if (!Arrays.asList(Locales.values()).contains(parserLocale)) {
            Logger.error("Error occurs on parserLocale setting.");
            throw new UnresolvedLocaleException(parserLocale + " Isn't supported");
        }

        Logger.info("Set parser locale.");
        Locale currentLocale = new Locale(parserLocale.toString(), parserLocale.toString());
        Logger.info("Start parsing.");
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
        sentenceIterator.setText(text.toString());
        int boundary = sentenceIterator.first();
        int lastBoundary = 0;
        while (boundary != BreakIterator.DONE) {
            boundary = sentenceIterator.next();
            if (boundary != BreakIterator.DONE) {
                sentences.add(new Sentence(text.substring(lastBoundary, boundary)));
            }
            lastBoundary = boundary;
        }
        Logger.info("Parsing file is over.");

        if (sentences.isEmpty()) {
            Logger.error("Error occurs on parsing. Result is null");
            throw new ParsingException("There is mo paragraphs or code blocks");
        }

        return sentences;
    }
}
