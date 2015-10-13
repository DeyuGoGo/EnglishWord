package go.deyu.englishword;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import model.EnglishWordInterface;
import model.EnglishWordModel;

/**
 * Created by huangeyu on 15/8/28.
 */
public class BaseFragmentActivityWithEWM extends FragmentActivity{

    protected EnglishWordInterface englishWordModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        englishWordModel = new EnglishWordModel(this);
    }

    public EnglishWordInterface getEnglishWordModel() {
        return englishWordModel;
    }
}
