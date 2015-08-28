package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import adapter.MainBodySwipeListViewAdapter;
import butterknife.Bind;
import data.EnglishWord;
import go.deyu.englishword.R;
import go.deyu.util.LOG;
import model.OnModelDataChangeListener;

/**
 * Created by huangeyu on 15/5/18.
 */
public class MainBodyFragment extends BaseFragment implements OnModelDataChangeListener {

    private final String TAG = getClass().getSimpleName();
    private final Handler mUIHandler;
    private final int WHAT_MESSAGE_CHANGE = 0x0001;
    private final int WHAT_MESSAGE_ERROR = 0x0099;
    private MainBodySwipeListViewAdapter adapter;
    private MainBodySwipeListViewAdapter.SwipeLayoutListener listener ;

    @Bind(R.id.fragment_main_body_listview)ListView bodyListView;




    public MainBodyFragment(){
        mUIHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case WHAT_MESSAGE_CHANGE:
                        refreshData();
                        break;
                    case WHAT_MESSAGE_ERROR:
                        if(getActivity()!=null)Toast.makeText(getActivity() , "Oh~No~Some Error", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        listener = new MainBodySwipeListViewAdapter.SwipeLayoutListener() {
            @Override
            public void OnDeleteClick(int position) {
                deleteMessage(position);
            }
            @Override
            public void OnStateChanged(int position, int state) {
                LOG.d(TAG, "changeEnglishWordtate position : " + position + " state : " + state);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_body , container , false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.registerOnModelDataChangeListener(this);
        adapter = new MainBodySwipeListViewAdapter(getActivity() , model.getEnglishWords());
        adapter.registerSwipeLayoutListener(listener);
        bodyListView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUIHandler.sendEmptyMessage(WHAT_MESSAGE_CHANGE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.unregisterSwipeLayoutListener(listener);
        model.unregisterOnModelDataChangeListener(this);
    }


    @Override
    public void onChange() {
        mUIHandler.sendEmptyMessage(WHAT_MESSAGE_CHANGE);
    }

    private boolean deleteMessage(@NonNull int position){
        LOG.d(TAG, "deleteMessage word : " + position);
        model.deleteWord((EnglishWord)adapter.getItem(position));
        return true;
    }

    @UiThread
    private void refreshData(){
        LOG.d(TAG,"refresh Data and notifyDataSetChanged");
        model.refresh();
        adapter.notifyDataSetChanged();
    }

}
