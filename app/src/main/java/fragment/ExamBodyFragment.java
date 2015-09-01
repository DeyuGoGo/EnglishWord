package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import data.Question;
import exception.QueueEmptyException;
import go.deyu.englishword.MainActivity;
import go.deyu.englishword.R;
import tts.AndroidTTS;
import tts.TTStoSpeak;

/**
 * Created by huangeyu on 15/8/28.
 */
public class ExamBodyFragment extends BaseExamFragment {

    private List<Button> AnsButtons;

    private TTStoSpeak TTS ;

    private int total = 0, score = 0 ;

    private final Handler mUIHandler;

    private static final int WHAT_ARG_CORRECT = 0;
    private static final int WHAT_ARG_ERROR = 1;
    private static final int WHAT_ANS_QUESTION =0x01;
    private static final int WHAT_FINISH_QUESTION =0x99;

    @Bind(R.id.tv_question)TextView mQuestionTv;

    @Bind(R.id.tv_score)TextView mScoreTv;

    @Bind(R.id.fl_finish_question)FrameLayout mFinishFL;

    @Bind(R.id.ll_quetion)LinearLayout mQuestionLL;


    @Bind(R.id.btn_ans1)Button mAnsBtn1;

    @Bind(R.id.btn_ans2)Button mAnsBtn2;

    @Bind(R.id.btn_ans3)Button mAnsBtn3;

    @Bind(R.id.btn_ans4)Button mAnsBtn4;

    @OnClick({R.id.btn_ans1 , R.id.btn_ans2,R.id.btn_ans3,R.id.btn_ans4})
    public void ans(View v){
        boolean isCorrect = ((Button) v).getText().toString().equals(mQueue.getLastPullElement().getQuestionWord().getCustom_wrod());
        int arg = isCorrect ? WHAT_ARG_CORRECT : WHAT_ARG_ERROR;
//      0 No to use
        mUIHandler.sendMessage(mUIHandler.obtainMessage(WHAT_ANS_QUESTION, arg, 0));
    }
    @OnClick(R.id.imgbtn_voice)
    public void playQuestionVoice(){
        TTS.speak(mQueue.getLastPullElement().getQuestionWord().getEnglish_wrod());
    }

    @OnClick(R.id.btn_finish)
    public void finishExam(){
        startActivity(new Intent(getActivity(),MainActivity.class));
        getActivity().finish();
    }



    public ExamBodyFragment(){
        mUIHandler = new Handler(Looper.getMainLooper()) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_body, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TTS = new AndroidTTS(getActivity());
        setupAnsButtons();
        try {
            mQueue.next();
        } catch (QueueEmptyException e) {
            Toast.makeText(getActivity(),"Error Queue tell to Deyu , please" ,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TTS.close();
    }

    @Override
    public void OnNext(Question mNextElement) {
        String QuestionWord = mNextElement.getQuestionWord().getEnglish_wrod();
        mQuestionTv.setText(QuestionWord);
        for(int i = 0; i < AnsButtons.size(); i++){
            AnsButtons.get(i).setText(mNextElement.getAnsWords().get(i).getCustom_wrod());
        }
        playQuestionVoice();
    }

    private void setupAnsButtons(){
        AnsButtons = new ArrayList<Button>();
        AnsButtons.add(mAnsBtn1);
        AnsButtons.add(mAnsBtn2);
        AnsButtons.add(mAnsBtn3);
        AnsButtons.add(mAnsBtn4);
    }

    private void finishQuestion(){
        mQuestionLL.setVisibility(View.INVISIBLE);
        mFinishFL.setVisibility(View.VISIBLE);
    }

    private void ansQuestion(int arg){
        total++;
        if(arg == WHAT_ARG_CORRECT){
            score ++;
        }
        if(arg == WHAT_ARG_ERROR){
        }
        refreshScore();
    }

    private void refreshScore(){
        mScoreTv.setText("Score " + total + "/" + score);
    }
}
