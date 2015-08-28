package model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import data.EnglishWord;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by huangeyu on 15/8/22.
 */
public class EnglishWordModel {

    private Context mContext;
    private Realm realm;
    private RealmResults<EnglishWord> englishWords;
    private List<OnModelDataChangeListener> mListeners = new ArrayList<OnModelDataChangeListener>();

    public EnglishWordModel(Context context){
        this.mContext = context;
        realm = Realm.getInstance(mContext);
        RealmQuery<EnglishWord> query = realm.where(EnglishWord.class);
        englishWords = query.findAll();
    }

    public void refresh(){
        RealmQuery<EnglishWord> query = realm.where(EnglishWord.class);
        englishWords = query.findAll();
    }


    public void addWord(final String engWord , final String customWord){
        realm.executeTransaction(new Realm.Transaction() {
                                     @Override
                                     public void execute(Realm realm) {
                                         EnglishWord ew = realm.createObject(EnglishWord.class);
                                         ew.setEnglish_wrod(engWord);
                                         ew.setCustom_wrod(customWord);
                                     }
                                 }
        );
        onDataChange();
    }

    public void deleteWord(final EnglishWord ew) {
        realm.beginTransaction();
        ew.removeFromRealm();
        realm.commitTransaction();
        onDataChange();
    }

    public List<EnglishWord> getEnglishWords(){
        return englishWords;
    }

    public void registerOnModelDataChangeListener(@NonNull OnModelDataChangeListener listener){
        mListeners.add(listener);
    }

    public void unregisterOnModelDataChangeListener(@NonNull OnModelDataChangeListener listener){
        if(mListeners.indexOf(listener)==-1)return;
        mListeners.remove(listener);
    }

    private void onDataChange(){
        for(OnModelDataChangeListener l : mListeners){
            l.onChange();
        }
    }
}
