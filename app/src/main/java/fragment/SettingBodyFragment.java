package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import butterknife.Bind;
import go.deyu.englishword.R;
import go.deyu.englishword.SettingConfig;
import go.deyu.util.LOG;

/**
 * Created by huangeyu on 15/5/18.
 */
public class SettingBodyFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    @Bind(R.id.switch_setting_sereen_lock_test)Switch mScreenLockSwitch ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_body, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScreenLockSwitch.setChecked(SettingConfig.getScreenLockStatus(getActivity()));
        mScreenLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LOG.d(TAG,"isChecked : " + isChecked);
                SettingConfig.setScreenLockStatus(getActivity(),isChecked);
            }
        });
    }
}

