package com.num.picker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.number.picker.GetCounter;
import com.number.picker.NumberPick;
import com.number.picker.NumberPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {


    @BindView(R.id.tv_num_value)
    TextView tv_num_value;

    String dialogTitle = "Number Picker";
    Activity activity;
    String selectedNumber = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        activity = this;
    }


    @OnClick(R.id.btn_num_pick)
    public void btnClick() {

        NumberPick.pickNumber(activity,
                dialogTitle,     //dialog title
                selectedNumber,  //value selection
                0,               // starting number
                50,              // total number of items (here-0+80 means 0 to 79)  (eg: 2+80 means 2 to 81)
                20,               //number of items per page
                4,                // number of columns in a row
                new GetCounter() {
                    @Override
                    public void counterValue(NumberPickerDialog dialogFragment, String index) {    // get selected value
                        selectedNumber = index;
                        tv_num_value.setText(selectedNumber);
                        dialogFragment.dismiss();

                    }

                    @Override
                    public void dismissMethod() {       //called when dialog dismissed


                    }

                    @Override
                    public void onBack() {              //called when device back button pressed


                    }
                });
    }
}
