package fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.OnClick;
import data.EnglishWord;
import data.ExamMode;
import go.deyu.englishword.ExamActivity;
import go.deyu.englishword.R;

/**
 * Created by huangeyu on 15/5/18.
 */
public class MainHeadFragment extends BaseFragment {

    private LinearLayout dialog_add_LL;

    @BindString(R.string.confirm)
    String mConfirmString;

    @BindString(R.string.cancel)
    String mCancelString;

    @BindString(R.string.add_word_dialog_title)
    String mAddDialogTitle;

    @OnClick(R.id.btn_add)
    void addMessage() {
        showAddDialog();
    }

    @OnClick(R.id.btn_exam)
    void exam(){
        if(model.getEnglishWords().size()<10){
            Toast.makeText(getActivity(), "Words not enough , plesae add word above ten", Toast.LENGTH_SHORT).show();
            return;
        }
        showEaxmDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_head, container, false);
    }


    private void showAddDialog() {
        dialog_add_LL =(LinearLayout)LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_word, null , false);
        final EditText etEngWord ;
        final EditText etCustomWord;
        etEngWord = (EditText)dialog_add_LL.findViewById(R.id.et_english);
        etCustomWord = (EditText)dialog_add_LL.findViewById(R.id.et_custom);
        new AlertDialog.Builder(getActivity()).setTitle(mAddDialogTitle).setIcon(
                android.R.drawable.ic_dialog_info).setView(
                dialog_add_LL).setPositiveButton(mConfirmString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EnglishWord ew = new EnglishWord();
                ew.setEnglish_wrod(etEngWord.getText().toString());
                ew.setCustom_wrod(etCustomWord.getText().toString());
                model.addWord(ew);
            }
        })
                .setNegativeButton(mCancelString, null).show();
    }

    private void showEaxmDialog(){
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.btn_star)
                .setTitle("Exam")
                .setMessage("Choice exam mode ")
                .setPositiveButton("Listen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoExamActivity(ExamMode.LISTEN);
                    }
                })
                .setNegativeButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoExamActivity(ExamMode.ENGLISH);
                    }
                })
                .setNeutralButton("Translation", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoExamActivity(ExamMode.TRANSLATION);
                    }
                })
                .create();
        dialog.show();
    }
    private void gotoExamActivity(ExamMode mode){
        Intent i = new Intent(getActivity(),ExamActivity.class);
        i.putExtra(ExamActivity.EXTRA_KEY_EXAM_MODE , mode);
        startActivity(i);
        getActivity().finish();
    }
}

