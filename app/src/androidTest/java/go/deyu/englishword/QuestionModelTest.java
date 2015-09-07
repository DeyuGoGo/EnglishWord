package go.deyu.englishword;


import org.junit.Test;

import java.util.List;

import data.Question;
import model.QuestionModel;

/**
 * Created by huangeyu on 15/8/29.
 */
public class QuestionModelTest extends EnglishWordModelTest{

    private QuestionModel mQmodel;

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
        addRandomWords(20);
        assertTrue(model.getEnglishWords().size() == 20);
        List<Question> Questions = mQmodel.getRandomQuestions(10);
        assertTrue(Questions.size()==10);

    }

//  測試隨機但不重複
    @Test
    public void testMakeRandomNumber(){
        for(int i = 0 ; i < 10 ; i++) {
//            產生從隨機亂數陣列。
            int[] abc = mQmodel.makeRandomNumberArray(10, 10+i);
//          走訪所有陣列元素
            for (int a : abc) {
//                查看重複幾次
                int same = 0;
//                走訪所有元素看有幾個a，理論上只能有一個
                for (int b : abc) {
                    if (a == b) {
                        same++;
                    }
                }
//                只有一個不然測試失敗
                assertTrue(same == 1);
            }
        }
    }

    protected void addRandomWords(int many){
        for(int i = 0 ; i < many ; i++){
            model.addWord(getRandomWrod());
        }
    }



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
