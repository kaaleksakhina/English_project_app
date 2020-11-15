package my.project.english;

public class Word {

    private String word;
    private String translation;
    private String examples;

    public Word(String word, String translation, String examples) {
        this.word = word;
        this.translation =  translation;
        this.examples = examples;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }


    public String getExamples() {
        return examples;
    }

}