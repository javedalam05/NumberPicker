package com.number.picker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jalam on 15-01-2018.
 */

public class NumberPick {


    public static void pickNumber(
            Activity activity,      //activity instance
            String dialogTitle,     //title of picker
            String selectedNumber,  //default value selected number of picker
            int startNumber,        //starting value of picker (ex: if gives 2 picker start from 2, as for 3,4,5)
            int maxNumber,          // total number of items depened on the startNumber (ex: startNumber=2 and maxNumber = 20,
                                    // means picker will be 2 to 21)
            int itemCountPerPage,   // number grid item per page
            int numOfColumns,       //number of columns in a row in grid
            GetCounter getCounter   //interface for getting values
    ) {

        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        Bundle args = new Bundle();
        args.putString("title", dialogTitle);               // title of number picker dialog

        numberPickerDialog.setArguments(args);              //arguments for title of dialog
        numberPickerDialog.setStartNumber(startNumber);
        numberPickerDialog.setIndexValue(selectedNumber);   //for selected value selection
        numberPickerDialog.setMaxNumber(maxNumber);               //total item visible in dialog
        numberPickerDialog.setItemCountPerPage(itemCountPerPage);         //for item visible on per page
        numberPickerDialog.setNumberOfColumns(numOfColumns);           //number of columns in a row

        numberPickerDialog.setGetCounter(getCounter);

        numberPickerDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "");
    }

}
