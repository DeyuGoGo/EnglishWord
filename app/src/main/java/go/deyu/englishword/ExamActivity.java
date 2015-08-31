package go.deyu.englishword;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import fragment.ExamBodyFragment;
import fragment.ExamHeadFragment;

/**
 * Created by huangeyu on 15/8/28.
 */
public class ExamActivity extends BaseFragmentActivityWithEWM{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_head_container) != null && findViewById(R.id.fragment_body_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Fragment HeadFragment = new ExamHeadFragment();
            Fragment BodyFragment = new ExamBodyFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            HeadFragment.setArguments(getIntent().getExtras());
            BodyFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_head_container, HeadFragment)
                    .add(R.id.fragment_body_container, BodyFragment , "BodyFragment")
                    .commit();
        }
    }
}
