package go.deyu.englishword;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.EnglishWord;
import data.ExamMode;
import data.Question;
import exception.QueueEmptyException;
import go.deyu.util.LOG;
import model.EnglishWordInterface;
import model.EnglishWordModel;
import model.ObserverQueue;
import model.QuestionModel;
import model.QuestionModelInterface;
import tts.TTStoSpeak;

/**
 * Created by huangeyu on 15/10/15.
 */
public class ScreenLockView extends RelativeLayout implements ObserverQueue.OnNextListener<Question>{

    private List<Button> AnsButtons;

    private TTStoSpeak TTS ;

    private final String TAG = getClass().getSimpleName();

    private Handler mUIHandler =  mUIHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_ANS_QUESTION:
                    ansQuestion(msg.arg1);
                    try {
                        mQueue.next();
                    } catch (QueueEmptyException e) {
                        mUIHandler.sendEmptyMessage(WHAT_FINISH_QUESTION);
                    }
                    break;
                case WHAT_FINISH_QUESTION:
                    finishQuestion();
                    break;

            }
        }
    };

    private ExamConfig config;

    private QuestionModelInterface mQuestionModel;
    private ObserverQueue<Question> mQueue;
    private EnglishWordInterface englishWordModel ;

    private static final int WHAT_ARG_CORRECT = 0;
    private static final int WHAT_ARG_ERROR = 1;
    private static final int WHAT_ANS_QUESTION =0x01;
    private static final int WHAT_FINISH_QUESTION =0x99;

    @Bind(R.id.tv_question)TextView mQuestionTv;

    @Bind(R.id.tv_score)TextView mScoreTv;

    @Bind(R.id.btn_ans1)Button mAnsBtn1;

    @Bind(R.id.btn_ans2)Button mAnsBtn2;

    @Bind(R.id.btn_ans3)Button mAnsBtn3;

    @Bind(R.id.btn_ans4)Button mAnsBtn4;

    @Bind(R.id.imgbtn_voice)ImageButton mVoiceImgBtn;

    @OnClick({R.id.btn_ans1 , R.id.btn_ans2,R.id.btn_ans3,R.id.btn_ans4})
    public void ans(View v){
        int i = AnsButtons.indexOf(v);
        Question q = mQueue.getLastPullElement();
        boolean isCorrect = q.getQuestionWord().getEnglish_wrod().equals(q.getAnsWords().get(i).getEnglish_wrod()) ;
        int arg = isCorrect ? WHAT_ARG_CORRECT : WHAT_ARG_ERROR;
//      0 No to use
        mUIHandler.sendMessage(mUIHandler.obtainMessage(WHAT_ANS_QUESTION, arg, 0));
    }
    @OnClick(R.id.imgbtn_voice)
    public void playQuestionVoice(){
        TTS.speak(mQueue.getLastPullElement().getQuestionWord().getEnglish_wrod());
    }

    public ScreenLockView(Context context) {
        super(context);
        LOG.d(TAG, "ScreenLockView(Context context)");
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.fragment_exam_body, this);
        setBackgroundResource(R.drawable.destophorrock);
        config = new ExamConfig(ExamMode.ENGLISH);
        englishWordModel = new EnglishWordModel(getContext());
        prepareQuestionQueue();
        ButterKnife.bind(this);
        hideNoUseViews(config);
        setupAnsButtons();
        LOG.d(TAG, "init");
        try {
            mQueue.next();
        } catch (QueueEmptyException e) {
            LOG.d(TAG , "QueueEmptyException : " + e);
            Toast.makeText(getContext(), "Error Queue tell to Deyu , please", Toast.LENGTH_LONG).show();
        }
    }

    private void prepareQuestionQueue(){
        LOG.d(TAG,"englishWordModel.getEnglishWords() size : " + englishWordModel.getEnglishWords().size());
        mQuestionModel = new QuestionModel(englishWordModel.getEnglishWords());
        mQueue = new ObserverQueue<Question>(mQuestionModel.getRandomQuestions(1));
        mQueue.registerOnNextListener(this);
    }

    @Override
    public void OnNext(Question mNextElement) {
        setupQuestionViews(mNextElement);
        if(config.isHasVoice())
            playQuestionVoice();
    }


    private void setupAnsButtons(){
        AnsButtons = new ArrayList<Button>();
        AnsButtons.add(mAnsBtn1);
        AnsButtons.add(mAnsBtn2);
        AnsButtons.add(mAnsBtn3);
        AnsButtons.add(mAnsBtn4);
    }

    private void setupQuestionViews(Question mNextElement){
        LOG.d(TAG , "setupQuestionViews mNextElement : " +mNextElement.getAnsWords() + mNextElement.getQuestionWord());
        EnglishWord QuestionEnglishWord = mNextElement.getQuestionWord();
        String QuestionWord  = config.getQuestionLanguage() == ExamConfig.ENGLISH ? QuestionEnglishWord.getEnglish_wrod() : QuestionEnglishWord.getCustom_wrod();
        mQuestionTv.setText(QuestionWord);
        for(int i = 0; i < AnsButtons.size(); i++){
            EnglishWord AnsEnglishWord = mNextElement.getAnsWords().get(i);
            String AnsString = config.getAnswerLanguage() == ExamConfig.ENGLISH ? AnsEnglishWord.getEnglish_wrod() : AnsEnglishWord.getCustom_wrod();
            AnsButtons.get(i).setText(AnsString);
        }
    }

    private void finishQuestion(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(this);
        mQueue.unregisterOnNextListener(this);
    }

    private void ansQuestion(int arg){
        if(arg == WHAT_ARG_ERROR){
            mQueue.addAll(mQuestionModel.getRandomQuestions(1));
        }
    }
    private void hideNoUseViews(ExamConfig config){
        mScoreTv.setVisibility(GONE);
        if(config.isHasVoice())
            mQuestionTv.setVisibility(GONE);
        else
            mVoiceImgBtn.setVisibility(GONE);
    }
}
