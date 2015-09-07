package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import go.deyu.englishword.BaseFragmentActivityWithEWM;
import model.EnglishWordInterface;

/**
 * Created by huangeyu on 15/5/20.
 */
public abstract class BaseFragment extends Fragment {

    protected EnglishWordInterface model;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ((BaseFragmentActivityWithEWM)getActivity()).getEnglishWordModel();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
