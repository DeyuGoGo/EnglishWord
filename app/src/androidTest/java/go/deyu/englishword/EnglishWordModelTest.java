package go.deyu.englishword;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.Random;

import data.EnglishWord;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import model.EnglishWordInterface;
import model.EnglishWordModel;

/**
 * Created by huangeyu on 15/8/28.
 */

public class EnglishWordModelTest extends AndroidTestCase{

    protected EnglishWordInterface model ;
    protected Random r;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        r = new Random();
        RealmConfiguration testConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.deleteRealm(testConfig);
        model = new EnglishWordModel(testConfig);
    }



    @Test
    public void testAddWrod(){
        EnglishWord ew = getRandomWrod();
        model.addWord(ew);
        model.refresh();
        EnglishWord modelew = model.getEnglishWords().get(0);
        assertEquals(modelew.getEnglish_wrod(),ew.getEnglish_wrod());
        assertEquals(modelew.getCustom_wrod(), ew.getCustom_wrod());
        model.clearWrods();
    }

    protected EnglishWord getRandomWrod(){
        String a = "" + r.nextInt();
        String b = "" + r.nextInt();
        EnglishWord ew = new EnglishWord();
        ew.setEnglish_wrod(a);
        ew.setCustom_wrod(b);
        return ew;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        model.clearWrods();
        model.close();
    }
}
