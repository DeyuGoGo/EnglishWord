package model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.EnglishWord;
import data.Question;

/**
 * Created by huangeyu on 15/8/29.
 */
public class QuestionModel implements QuestionModelInterface{

    private List<EnglishWord> mEnglishWords;
    protected Random r ;

    public QuestionModel(@NonNull List<EnglishWord> EnglishWords){
        this.mEnglishWords = EnglishWords;
        this.r = new Random();
    }

    @Override
    public List<Question> getRandomQuestions(int many) {
        int a[] =  makeRandomNumberArray(many , mEnglishWords.size());
        List<Question> questions = new ArrayList<Question>();
        for(int i = 0 ; i < many ; i++){
            Question q = new Question();
            EnglishWord question =  mEnglishWords.get(a[i]);
            q.setQuestionWord(question);
            q.setAnsWords(getRandomWrods(4));
            if(!q.getAnsWords().contains(question)) {
                int currentAnswerLocation = makeRandomNumberArray(1,4)[0];
                q.getAnsWords().set(currentAnswerLocation, question);
            }
            questions.add(q);
        }
        return questions;
    }

    public List<EnglishWord> getRandomWrods(int many){
        List<EnglishWord> words  = new ArrayList<EnglishWord>();
        int a[] =  makeRandomNumberArray(many , mEnglishWords.size());
        for(int i = 0 ; i < many ; i ++){
            words.add(mEnglishWords.get(a[i]));
        }
        return words;
    }

    public int[] makeRandomNumberArray(int pick , int range){
        int[] a = makeNumberArray(range);
        for(int i = 0 ; i < pick ; i++){
            int random = r.nextInt(range);
            int temp = a[i];
            a[i] = a[random];
            a[random] = temp;
        }
        return a;
    }

    private int[] makeNumberArray(int range){
        int a[] = new int[range];
        for(int i = 0 ; i < range ; i ++){
            a[i] = i;
        }
        return a;
    }
}