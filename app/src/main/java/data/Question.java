package data;

import java.util.List;

/**
 * Created by huangeyu on 15/8/29.
 */
public class Question {

    private EnglishWord QuestionWord ;
    private List<EnglishWord> AnsWords ;

    public List<EnglishWord> getAnsWords() {
        return AnsWords;
    }

    public void setAnsWords(List<EnglishWord> ansWords) {
        AnsWords = ansWords;
    }

    public EnglishWord getQuestionWord() {
        return QuestionWord;
    }

    public void setQuestionWord(EnglishWord questionWord) {
        QuestionWord = questionWord;
    }
}
