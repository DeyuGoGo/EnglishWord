package data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by huangeyu on 15/8/21.
 */
@RealmClass
public class EnglishWord extends RealmObject {
    @PrimaryKey
    private String english_wrod ;
    private String custom_wrod ;

    public String getCustom_wrod() {
        return custom_wrod;
    }

    public void setCustom_wrod(String chinese_wrod) {
        this.custom_wrod = chinese_wrod;
    }

    public String getEnglish_wrod() {
        return english_wrod;
    }

    public void setEnglish_wrod(String english_wrod) {
        this.english_wrod = english_wrod;
    }
}
