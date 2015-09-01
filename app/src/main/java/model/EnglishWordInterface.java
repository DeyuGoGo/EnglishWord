package model;

import android.support.annotation.NonNull;

import java.util.List;

import data.EnglishWord;

/**
 * Created by huangeyu on 15/8/28.
 */
public interface EnglishWordInterface {
    public void refresh();
    public List<EnglishWord> getEnglishWords();
    public void addWord(final EnglishWord ew);
    public void deleteWord(final EnglishWord ew);
    public void registerOnModelDataChangeListener(@NonNull OnModelDataChangeListener listener);
    public void unregisterOnModelDataChangeListener(@NonNull OnModelDataChangeListener listener);
    public void clearWrods();
    public void close();
}
