package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import go.deyu.englishword.MainActivity;
import model.EnglishWordModel;

/**
 * Created by huangeyu on 15/5/20.
 */
public class BaseFragment extends Fragment {

    protected EnglishWordModel model;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ((MainActivity)getActivity()).getEnglishWordModel();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
