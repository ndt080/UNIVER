package Core.Services;

import Core.Models.Sentence;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Parser service
 */
public class Parser {
    /**
     *  Parsing file text to ArrayList of sentences
     * @param file input file
     * @return ArrayList of sentences
     * @throws IOException Exception
     */
    public ArrayList<Sentence> textToSentences(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        ArrayList<Sentence> text = new ArrayList<Sentence>();

        String sentence = "";
        String[] sentences;
        String line = in.readLine();
        String readLine = line;

        while (readLine != null) {
            if (readLine.equals("")) {
                readLine = in.readLine();
                line += readLine;
                continue;
            }

            int i;
            int index = 0;
            String character = "";

            if ("\u2026.!?".indexOf(line.charAt(line.length() - 1)) > -1) {
                character += line.charAt(line.length() - 1);
            }
            sentences = line.split("[.?\u2026]+");

            for (i = 0; i < sentences.length - 1; i++) {
                sentence = sentences[i] + line.charAt(sentences[i].length() + index);
                index += sentence.length();
                text.add(new Sentence(sentence));
            }
            sentence = sentences[i] + character;
            if (!character.equals("")) {
                text.add(new Sentence(sentence));
                sentence = "";
            }
            readLine = in.readLine();
            line = sentence + " " + readLine;
        }
        return text;
    }

    /**
     * Parsing file text to ArrayList of sentences with Locale
     * @param file input file
     * @param language language of text
     * @param country dialect of which country
     * @return ArrayList of sentences
     */
    public ArrayList<Sentence> textToSentencesWithLocale(File file, String language, String country){
        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        try {
            Scanner sc = new Scanner(file);
            StringBuilder text = new StringBuilder();

            while (sc.hasNextLine())
            {
                text.append(sc.nextLine()).append(" ");
            }
            sc.close();

            Locale currentLocale = new Locale(language, country);
            BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
            sentenceIterator.setText(text.toString());
            int boundary = sentenceIterator.first();
            int lastBoundary = 0;
            while (boundary != BreakIterator.DONE) {
                boundary = sentenceIterator.next();
                if(boundary != BreakIterator.DONE){
                    sentences.add(new Sentence(text.substring(lastBoundary, boundary)));
                }
                lastBoundary = boundary;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentences;
    }
}
