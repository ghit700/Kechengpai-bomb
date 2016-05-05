package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Subject;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/5/4.
 */
public class AddExamAdapter extends BaseAdapter<Subject> {


    public AddExamAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    public int getItemViewType(int position) {
        int i = 0;
        switch (mDataList.get(position).getType()) {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 4;
                break;
        }
        return i;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        switch (viewType) {
            case 1:
                return R.layout.item_add_exam_judge;
            case 2:
                return R.layout.item_add_exam_single_selection;
            case 3:
                return R.layout.item_add_exam_multiple_selection;
            case 4:
                return R.layout.item_add_exam_short_answer;
        }
        return 1;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Subject item) {
        super.bindData(holder, position, item);
        switch (item.getType()) {
            case 1:
                bindJudgeData(holder, position, item);
                break;
            case 2:
                bindSingleData(holder, position, item);
                break;
            case 3:
                bindMultipleData(holder, position, item);
                break;
            case 4:
                bindShortData(holder, position, item);
                break;

            default:
                break;
        }
    }

    private void bindShortData(ViewHolder holder, final int position, final Subject item) {
        TextView tvAddExamMultiple = (TextView) holder.getViewById(R.id.tv_add_exam_short_answer);
        final EditText etAddExamGrade = (EditText) holder.getViewById(R.id.et_add_exam_grade);
        final EditText etAddExamTitle = (EditText) holder.getViewById(R.id.et_add_exam_title);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        int i = position;
        i++;
        tvAddExamMultiple.setText(i + ": " + "简答题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteItem(position);
            }
        });
        etAddExamGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setS_value(Integer.parseInt(etAddExamGrade.getText().toString()));
                }
            }
        });
        etAddExamTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTitle(etAddExamTitle.getText().toString());
            }
        });

    }

    private void bindMultipleData(ViewHolder holder, final int position, final Subject item) {
        TextView tvAddExamMultiple = (TextView) holder.getViewById(R.id.tv_add_exam_multiple);
        final EditText etAddExamGrade = (EditText) holder.getViewById(R.id.et_add_exam_grade);
        final EditText etAddExamTitle = (EditText) holder.getViewById(R.id.et_add_exam_title);
        final EditText etAddExamA = (EditText) holder.getViewById(R.id.et_add_exam_a);
        final EditText etAddExamB = (EditText) holder.getViewById(R.id.et_add_exam_b);
        final EditText etAddExamC = (EditText) holder.getViewById(R.id.et_add_exam_c);
        final EditText etAddExamD = (EditText) holder.getViewById(R.id.et_add_exam_d);
        final TextView tvAddExamA = (TextView) holder.getViewById(R.id.tv_add_exam_a);
        final TextView tvAddExamB = (TextView) holder.getViewById(R.id.tv_add_exam_b);
        final TextView tvAddExamC = (TextView) holder.getViewById(R.id.tv_add_exam_c);
        final TextView tvAddExamD = (TextView) holder.getViewById(R.id.tv_add_exam_d);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        int i = position;
        i++;
        tvAddExamMultiple.setText(i + ": " + "多选题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        tvAddExamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamA.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("a");
            }
        });
        tvAddExamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamB.setBackgroundResource(R.color.colorPrimary);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("b");
            }
        });
        tvAddExamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamC.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("c");
            }
        });
        tvAddExamD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamD.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("d");
            }
        });

        etAddExamA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(0, etAddExamA.getText().toString());
            }
        });
        etAddExamB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(1, etAddExamB.getText().toString());
            }
        });
        etAddExamC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(2, etAddExamC.getText().toString());
            }
        });
        etAddExamD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(3, etAddExamD.getText().toString());
            }
        });
        etAddExamGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setS_value(Integer.parseInt(etAddExamGrade.getText().toString()));
                }
            }
        });
        etAddExamTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTitle(etAddExamTitle.getText().toString());
            }
        });
    }

    private void bindSingleData(ViewHolder holder, final int position, final Subject item) {
        TextView tvAddExamSingle = (TextView) holder.getViewById(R.id.tv_add_exam_single);
        final EditText etAddExamGrade = (EditText) holder.getViewById(R.id.et_add_exam_grade);
        final EditText etAddExamTitle = (EditText) holder.getViewById(R.id.et_add_exam_title);
        final EditText etAddExamA = (EditText) holder.getViewById(R.id.et_add_exam_a);
        final EditText etAddExamB = (EditText) holder.getViewById(R.id.et_add_exam_b);
        final EditText etAddExamC = (EditText) holder.getViewById(R.id.et_add_exam_c);
        final EditText etAddExamD = (EditText) holder.getViewById(R.id.et_add_exam_d);
        final TextView tvAddExamA = (TextView) holder.getViewById(R.id.tv_add_exam_a);
        final TextView tvAddExamB = (TextView) holder.getViewById(R.id.tv_add_exam_b);
        final TextView tvAddExamC = (TextView) holder.getViewById(R.id.tv_add_exam_c);
        final TextView tvAddExamD = (TextView) holder.getViewById(R.id.tv_add_exam_d);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        int i = position;
        i++;
        tvAddExamSingle.setText(i + ": " + "单选题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        tvAddExamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamA.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("a");
            }
        });
        tvAddExamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamB.setBackgroundResource(R.color.colorPrimary);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("b");
            }
        });
        tvAddExamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamC.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamD.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("c");
            }
        });
        tvAddExamD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAddExamD.setBackgroundResource(R.color.colorPrimary);
                tvAddExamB.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamC.setBackgroundResource(R.drawable.bg_item_exam);
                tvAddExamA.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("d");
            }
        });

        etAddExamA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(0, etAddExamA.getText().toString());
            }
        });
        etAddExamB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(1, etAddExamB.getText().toString());
            }
        });
        etAddExamC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(2, etAddExamC.getText().toString());
            }
        });
        etAddExamD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.getContent().add(3, etAddExamD.getText().toString());
            }
        });
        etAddExamGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setS_value(Integer.parseInt(etAddExamGrade.getText().toString()));
                }
            }
        });
        etAddExamTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTitle(etAddExamTitle.getText().toString());
            }
        });



    }

    private void bindJudgeData(ViewHolder holder, final int position, final Subject item) {
        TextView tvAddExamJudge = (TextView) holder.getViewById(R.id.tv_add_exam_judge);
        final EditText etAddExamGrade = (EditText) holder.getViewById(R.id.et_add_exam_grade);
        final EditText etAddExamTitle = (EditText) holder.getViewById(R.id.et_add_exam_title);
        final Button btnAddExamTrue = (Button) holder.getViewById(R.id.btn_add_exam_true);
        final Button btnAddExamFalse = (Button) holder.getViewById(R.id.btn_add_exam_false);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);

        int i = position;
        i++;
        tvAddExamJudge.setText(i + ": " + "判断题");
        btnAddExamTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddExamTrue.setBackgroundResource(R.color.colorPrimary);
                btnAddExamFalse.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("true");
            }
        });
        btnAddExamFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddExamFalse.setBackgroundResource(R.color.colorPrimary);
                btnAddExamTrue.setBackgroundResource(R.drawable.bg_item_exam);
                item.setSolution("false");
            }
        });
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        etAddExamGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setS_value(Integer.parseInt(etAddExamGrade.getText().toString()));
                }
            }
        });
        etAddExamTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTitle(etAddExamTitle.getText().toString());
            }
        });

    }


}
