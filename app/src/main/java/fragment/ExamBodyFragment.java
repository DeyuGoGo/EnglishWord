package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import data.Question;
import go.deyu.englishword.R;
import tts.AndroidTTS;
import tts.TTStoSpeak;

/**
 * Created by huangeyu on 15/8/28.
 */
public class ExamBodyFragment extends BaseExamFragment {

    private List<Button> AnsButtons;

    private TTStoSpeak TTS ;

    @Bind(R.id.tv_question)TextView mQuestionTv;

    @OnClick(R.id.imgbtn_voice)
    public void playQuestionVoice(){
        TTS.speak(mQueue.getLastPullElement().getQuestionWord().getEnglish_wrod());
    }

    @Bind(R.id.btn_ans1)Button mAnsBtn1;

    @Bind(R.id.btn_ans2)Button mAnsBtn2;

    @Bind(R.id.btn_ans3)Button mAnsBtn3;

    @Bind(R.id.btn_ans4)Button mAnsBtn4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_body, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TTS = new AndroidTTS(getActivity());
        setupAnsButtons();
        mQueue.next();
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
}
