package com.number.picker;

public interface GetCounter {
    void counterValue(NumberPickerDialog dialogFragment, String index);  // get selected value

    void dismissMethod();   //called when dialog dismissed

    void onBack();          //called when device back button pressed
}