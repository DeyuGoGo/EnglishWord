package fragment;

import android.os.Bundle;

import data.Question;
import go.deyu.englishword.ExamActivity;
import model.ObserverQueue;

/**
 * Created by huangeyu on 15/5/20.
 */
public abstract class BaseExamFragment extends BaseFragment implements ObserverQueue.OnNextListener<Question> {

    protected ObserverQueue<Question> mQueue;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = ((ExamActivity)getActivity()).getmObserverQueue();
        mQueue.registerOnNextListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mQueue.unregisterOnNextListener(this);
    }
}
