package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import data.EnglishWord;
import go.deyu.englishword.R;


/**
 * Created by huangeyu on 15/5/19.
 */
public class MainBodySwipeListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<EnglishWord> mEnglishWord;
    private ArrayList<SwipeLayoutListener> mListeners ;


    public MainBodySwipeListViewAdapter(Activity activity, List<EnglishWord> EnglishWord) {
        this.mContext = activity;
        this.mLayoutInflater = activity.getLayoutInflater();
        this.mEnglishWord = EnglishWord;
        mListeners = new ArrayList<SwipeLayoutListener>();

    }


    @Override
    public int getCount() {
        return mEnglishWord.size();
    }

    @Override
    public Object getItem(int position) {
        return mEnglishWord.get(position);
    }

//    TODO Not need
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View generateView(final int position, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.main_body_swipe_list_item, null);
        EnglishWord ew = mEnglishWord.get(position);
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        EnglishWord ew = mEnglishWord.get(position);
        setViewValue(convertView, position, ew);
        setViewListener(convertView, position, ew);
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
        swipeLayout.close();
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    private void setViewValue(View v, int position, EnglishWord ew) {
        TextView eng_Tv = (TextView)v.findViewById(R.id.tv_english_word);
        TextView cus_Tv = (TextView)v.findViewById(R.id.tv_custom_word);
        eng_Tv.setText(ew.getEnglish_wrod());
        cus_Tv.setText(ew.getCustom_wrod());
    }

    private void setViewListener(View v, final int position, EnglishWord ew) {
        Button deleteBtn =(Button)(v.findViewById(R.id.delete));
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemDelete(position);
            }
        });
    }

    public void OnItemDelete(int position) {
        for(SwipeLayoutListener l : mListeners){
            if(l==null)continue;
            l.OnDeleteClick(position);
        }
    }

    public void OnItemStateChanged(int position, int state) {
        for(SwipeLayoutListener l : mListeners){
            if(l==null)continue;
        }
    }
    public void registerSwipeLayoutListener(SwipeLayoutListener listener){
        if(listener==null)return;
        mListeners.add(listener);
    }

    public void unregisterSwipeLayoutListener(SwipeLayoutListener listener){
        if(listener==null) return;
        if(mListeners.indexOf(listener)>0)mListeners.remove(listener);
    }

    public interface SwipeLayoutListener {
        public void OnDeleteClick(int position);
        public void OnStateChanged(int position, int state);
    }
}
