package go.deyu.englishword;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import fragment.MainBodyFragment;
import fragment.MainHeadFragment;
import fragment.NavigationDrawerFragment;
import fragment.SettingBodyFragment;
import fragment.SettingHeadFragment;
import go.deyu.util.LOG;

public class MainActivity extends BaseFragmentActivityWithEWM implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final String TAG = getClass().getSimpleName();
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            return;
        }
//            Drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /**
     * do change HeadFragment
     *
     * @param headfragment fragment want to replace old fragment
     */
    private void changeHeadFragment(Fragment headfragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_head_container, headfragment);
        transaction.commit();
    }

    /**
     * do change BodyFragment
     *
     * @param bodyfragment fragment want to replace old fragment
     */
    private void changeBodyFragment(Fragment bodyfragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_body_container, bodyfragment);
        transaction.commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        LOG.d(TAG, "onNavigationDrawerItemSelected position :" + position);
        switch (position) {
            case 0:
                changeHeadFragment(new MainHeadFragment());
                changeBodyFragment(new MainBodyFragment());
                break;
            case 1:
                changeHeadFragment(new SettingHeadFragment());
                changeBodyFragment(new SettingBodyFragment());
                break;
        }
    }


}
