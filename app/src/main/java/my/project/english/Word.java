package my.project.english;

import java.util.List;

public class Word {

    private String word;
    private String translation;
    private List <String> examples;
    private String collocations;

    public Word(String word, String translation, List<String> examples, String collocations) {
        this.word = word;
        this.translation =  translation;
        this.examples = examples;
        this.collocations = collocations;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public List <String> getExamples() {
        return examples;
    }

    public String getCollocations() {
        return collocations;
    }
}