package go.deyu.englishword;


import org.junit.Test;

import java.util.List;

import data.Question;
import model.QuestionModel;
import model.QuestionModelInterface;

/**
 * Created by huangeyu on 15/8/29.
 */
public class QuestionModelTest extends EnglishWordModelTest{

    private QuestionModelInterface mQmodel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mQmodel = new QuestionModel(model.getEnglishWords());
    }

    @Test
    public void testNoWrodMakeQuestion() {
        try{
            List<Question> Questions = mQmodel.getRandomQuestions(10);
            fail();
        }catch(IllegalArgumentException e){
        }
    }


    @Test
    public void testMakeQuestions(){
        addWords(20);
        assertTrue(model.getEnglishWords().size() == 20);
        List<Question> Questions = mQmodel.getRandomQuestions(10);
        assertTrue(Questions.size()==10);

    }

    protected void addWords(int many){
        for(int i = 0 ; i < many ; i++){
            model.addWord(getRandomWrod());
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
