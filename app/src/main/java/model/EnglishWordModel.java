package model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import data.EnglishWord;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by huangeyu on 15/8/22.
 */
public class EnglishWordModel implements EnglishWordInterface {

    private Context mContext;
    private Realm realm;
    private RealmResults<EnglishWord> englishWords;
    private List<OnModelDataChangeListener> mListeners = new ArrayList<OnModelDataChangeListener>();

    public EnglishWordModel(Context context){
        this.mContext = context;
        realm = Realm.getInstance(mContext);
        refresh();
    }

    public EnglishWordModel(RealmConfiguration realmConfiguration){
        realm = Realm.getInstance(realmConfiguration);
        refresh();
    }

    @Override
    public void refresh(){
        RealmQuery<EnglishWord> query = realm.where(EnglishWord.class);
        englishWords = query.findAll();
    }

    @Override
    public void addWord(final EnglishWord word){
        realm.executeTransaction(new Realm.Transaction() {
                                     @Override
                                     public void execute(Realm realm) {
                                         EnglishWord ew = realm.createObject(EnglishWord.class);
                                         ew.setEnglish_wrod(word.getEnglish_wrod());
                                         ew.setCustom_wrod(word.getCustom_wrod());
                                     }
                                 }
        );
        onDataChange();
    }

    @Override
    public void deleteWord(final EnglishWord ew) {
        realm.beginTransaction();
        ew.removeFromRealm();
        realm.commitTransaction();
        onDataChange();
    }

    @Override
    public void clearWrods(){
        realm.beginTransaction();
        realm.clear(EnglishWord.class);
        realm.commitTransaction();
    }

    @Override
    public void close() {
        if(realm!=null)
            realm.close();
    }

    @Override
    public List<EnglishWord> getEnglishWords(){
        return englishWords;
    }

    @Override
    public void registerOnModelDataChangeListener(@NonNull OnModelDataChangeListener listener){
        mListeners.add(listener);
    }

    @Override
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
