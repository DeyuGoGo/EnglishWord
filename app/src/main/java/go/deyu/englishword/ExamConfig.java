package go.deyu.englishword;

import data.ExamMode;

/**
 * Created by huangeyu on 15/9/3.
 */
public class ExamConfig{

    public static final int ENGLISH = 0x00 , TRANSLATIONS = 0x01;
    private final boolean hasVoice;
    private final boolean hasQuestionText;
    private final int QuestionLanguage;
    private final int AnswerLanguage;
    public ExamConfig(ExamMode e) {
        hasVoice = e == ExamMode.LISTEN ? true : false;
        hasQuestionText = e == ExamMode.LISTEN ? false : true;
        QuestionLanguage = e == ExamMode.ENGLISH ? ENGLISH : TRANSLATIONS;
        AnswerLanguage = e == ExamMode.TRANSLATION ? ENGLISH : TRANSLATIONS;
    }

    public boolean isHasQuestionText() {
        return hasQuestionText;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public int getAnswerLanguage() {
        return AnswerLanguage;
    }

    public int getQuestionLanguage() {
        return QuestionLanguage;
    }

    public static int getTRANSLATIONS() {
        return TRANSLATIONS;
    }
}
