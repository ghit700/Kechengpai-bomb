package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ketangpai.base.BaseActivity;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.bean.Subject;
import com.ketangpai.nan.ketangpai.teacher.R;

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
        final Button btnAddExamSumit = (Button) holder.getViewById(R.id.btn_add_exam_submit);
        int i = position;
        i++;
        tvAddExamMultiple.setText(i + ": " + "简答题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteItem(position);
            }
        });

        btnAddExamSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setScore(Integer.parseInt(etAddExamGrade.getText().toString()));
                } else if (etAddExamGrade.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入分数");
                    return;
                }
                if (!etAddExamTitle.getText().toString().equals("")) {
                    item.setTitle(etAddExamTitle.getText().toString());
                    btnAddExamSumit.setVisibility(View.GONE);
                } else {
                    ((BaseToolbarActivity) mContext).sendToast("请输入题目");
                }
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
        final CheckBox cbAddExamA = (CheckBox) holder.getViewById(R.id.cb_add_exam_a);
        final CheckBox cbAddExamB = (CheckBox) holder.getViewById(R.id.cb_add_exam_b);
        final CheckBox cbAddExamC = (CheckBox) holder.getViewById(R.id.cb_add_exam_c);
        final CheckBox cbAddExamD = (CheckBox) holder.getViewById(R.id.cb_add_exam_d);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        final Button btnAddExamSumit = (Button) holder.getViewById(R.id.btn_add_exam_submit);

        int i = position;
        final boolean[] solution = new boolean[4];
        i++;
        tvAddExamMultiple.setText(i + ": " + "多选题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
        btnAddExamSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etAddExamA.getText().toString().equals("") || etAddExamB.getText().toString().equals("") ||
                        etAddExamC.getText().toString().equals("") || etAddExamD.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入选项题目");
                    return;
                } else if (!etAddExamA.getText().toString().equals("") && !etAddExamB.getText().toString().equals("") &&
                        !etAddExamC.getText().toString().equals("") && !etAddExamD.getText().toString().equals("")) {
                    item.getContent().clear();
                    item.getContent().add(0, etAddExamA.getText().toString());
                    item.getContent().add(1, etAddExamB.getText().toString());
                    item.getContent().add(2, etAddExamC.getText().toString());
                    item.getContent().add(3, etAddExamD.getText().toString());
                }

                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setScore(Integer.parseInt(etAddExamGrade.getText().toString()));
                } else if (etAddExamGrade.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入分数");
                    return;
                }
                if (etAddExamTitle.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入题目");
                    return;
                } else if (!etAddExamTitle.getText().toString().equals("")) {
                    item.setTitle(etAddExamTitle.getText().toString());
                }


                if (!solution[0] && !solution[1] && !solution[2] && !solution[3]) {
                    ((BaseToolbarActivity) mContext).sendToast("请选择正确答案");
                    return;
                } else {
                    item.setSolution(solution[0] + "," + solution[1] + "," + solution[2] + "," + solution[3]);
                    btnAddExamSumit.setVisibility(View.GONE);
                }
            }
        });

        cbAddExamA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    solution[0] = true;
                } else {
                    solution[0] = false;
                }
            }
        });
        cbAddExamB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    solution[1] = true;
                } else {
                    solution[1] = false;
                }
            }
        });
        cbAddExamC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    solution[2] = true;
                } else {
                    solution[2] = false;
                }
            }
        });
        cbAddExamD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    solution[3] = true;
                } else {
                    solution[3] = false;
                }
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
        RadioGroup rgAddExam = (RadioGroup) holder.getViewById(R.id.rg_add_exam);
//        final RadioButton rbAddExamA = (RadioButton) holder.getViewById(R.id.rb_add_exam_a);
//        final RadioButton rbAddExamB = (RadioButton) holder.getViewById(R.id.rb_add_exam_b);
//        final RadioButton rbAddExamC = (RadioButton) holder.getViewById(R.id.rb_add_exam_c);
//        final RadioButton rbAddExamD = (RadioButton) holder.getViewById(R.id.rb_add_exam_d);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        final Button btnAddExamSumit = (Button) holder.getViewById(R.id.btn_add_exam_submit);

        int i = position;
        i++;
        tvAddExamSingle.setText(i + ": " + "单选题");
        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });


        rgAddExam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_add_exam_a) {
                    item.setSolution("a");
                } else if (checkedId == R.id.rb_add_exam_a) {
                    item.setSolution("b");
                } else if (checkedId == R.id.rb_add_exam_a) {
                    item.setSolution("c");
                } else {
                    item.setSolution("d");

                }
            }
        });
        btnAddExamSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etAddExamA.getText().toString().equals("") && !etAddExamB.getText().toString().equals("") &&
                        !etAddExamC.getText().toString().equals("") && !etAddExamD.getText().toString().equals("")) {
                    item.getContent().clear();
                    item.getContent().add(0, etAddExamA.getText().toString());
                    item.getContent().add(1, etAddExamB.getText().toString());
                    item.getContent().add(2, etAddExamC.getText().toString());
                    item.getContent().add(3, etAddExamD.getText().toString());

                } else if (etAddExamA.getText().toString().equals("") || etAddExamB.getText().toString().equals("") ||
                        etAddExamC.getText().toString().equals("") || etAddExamD.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入选项题目");
                    return;
                }

                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setScore(Integer.parseInt(etAddExamGrade.getText().toString()));
                } else if (etAddExamGrade.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入分数");
                    return;
                }

                if (!etAddExamTitle.getText().toString().equals("")) {
                    item.setTitle(etAddExamTitle.getText().toString());
                } else if (etAddExamTitle.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入题目");
                    return;
                }

                if (!item.getSolution().equals("")) {
                    btnAddExamSumit.setVisibility(View.GONE);
                } else {
                    ((BaseToolbarActivity) mContext).sendToast("请选择正确答案");
                }

            }
        });


    }

    private void bindJudgeData(ViewHolder holder, final int position, final Subject item) {
        TextView tvAddExamJudge = (TextView) holder.getViewById(R.id.tv_add_exam_judge);
        final EditText etAddExamGrade = (EditText) holder.getViewById(R.id.et_add_exam_grade);
        final EditText etAddExamTitle = (EditText) holder.getViewById(R.id.et_add_exam_title);
        RadioGroup rgAddExam = (RadioGroup) holder.getViewById(R.id.rg_add_exam);
        final Button btnAddExamCancle = (Button) holder.getViewById(R.id.btn_add_exam_cancle);
        final Button btnAddExamSumit = (Button) holder.getViewById(R.id.btn_add_exam_submit);

        int i = position;
        i++;
        tvAddExamJudge.setText(i + ": " + "判断题");

        rgAddExam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_add_exam_true) {
                    item.setSolution("true");
                } else if (checkedId == R.id.rb_add_exam_false) {
                    item.setSolution("false");
                }
            }
        });

        btnAddExamCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        btnAddExamSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etAddExamGrade.getText().toString().equals("")) {
                    item.setScore(Integer.parseInt(etAddExamGrade.getText().toString()));
                } else if (etAddExamGrade.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入分数");
                    return;
                }
                if (!etAddExamTitle.getText().toString().equals("")) {
                    item.setTitle(etAddExamTitle.getText().toString());
                } else if (etAddExamTitle.getText().toString().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请输入题目");
                    return;
                }
                if (item.getSolution().equals("")) {
                    ((BaseToolbarActivity) mContext).sendToast("请选择答案");
                } else if (!item.getSolution().equals("")) {
                    btnAddExamSumit.setVisibility(View.GONE);
                }
            }
        });


    }

}