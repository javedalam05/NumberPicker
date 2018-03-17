# NumberPicker

Create a simple Number Picker according to your theme using this Custom NumberPicker project.

![alt text](https://github.com/javedalam05/NumberPicker/blob/master/device-2018-01-24-215646.png)
![alt text](https://github.com/javedalam05/NumberPicker/blob/master/device-2018-01-24-215747.png)
![alt text](https://github.com/javedalam05/NumberPicker/blob/master/device-2018-01-24-215646.png)
```

##	How to use NumberPicker:
```
Simply Call following method according to your requirement

```
NumberPick.pickNumber(activity,
                dialogTitle,     //dialog title
                selectedNumber,  //value selection
                2,               // starting number
                80,              // total number of items (here-0+80 means 0 to 79)  (eg: 2+80 means 2 to 81)
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
 ```

```
If you want to change color of picker, simply change color code of colorPrimary in color.xml file

```
<color name="colorPrimary">#3F51B5</color>
```
