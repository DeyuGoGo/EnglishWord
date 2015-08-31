package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import data.Question;
import go.deyu.englishword.R;

/**
 * Created by huangeyu on 15/8/28.
 */
public class ExamHeadFragment extends BaseExamFragment {

    @Bind(R.id.tv_title)TextView mTitle_tv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_head, container, false);
    }


    @Override
    public void OnNext(Question mNextElement) {
        mTitle_tv.setText("Now is " + mQueue.getNumber() + " Question");
    }
}
