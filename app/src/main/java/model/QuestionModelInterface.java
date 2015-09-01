package model;

import java.util.List;

import data.Question;

/**
 * Created by huangeyu on 15/8/29.
 */
public interface QuestionModelInterface {
    public List<Question> getRandomQuestions(int many);
}
