package com.number.picker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.num.picker.R;

import java.util.ArrayList;
import java.util.List;

public class NumberPickerDialog extends DialogFragment implements ClickLisner, View.OnClickListener {


    private static int MAX_PAGE_ITEM_LIMIT = 70;
    private static int COLUMN_LIMIT = 7;
    private static int MAX_PAGE = 5;

    List<NumberPickFragment> fragments;
    NumberPagerAdapter ama;

    RelativeLayout btn_close;

    GetCounter getCounter;

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    String indexValue = "0";

    String title = "";

    ImageButton btn_left, btn_right;


    int numberOfColumns = 5;
    int itemCountPerPage = 50;


    int maxNumber = 100;
    int numberOfPages = 0;
    int selectedPage = 0;

    int startNumber = 0;

    ViewPager vpPager;

    public void setGetCounter(GetCounter getCounter) {
        this.getCounter = getCounter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_dialog_layout, container);

        title = getArguments().getString("title");


        btn_close = (RelativeLayout) view.findViewById(R.id.btn_close);
        TextView textView = (TextView) view.findViewById(R.id.txt_title);
        textView.setText(title);


        vpPager = (ViewPager) view.findViewById(R.id.viewpager);
        btn_left = (ImageButton) view.findViewById(R.id.btn_left);
        btn_right = (ImageButton) view.findViewById(R.id.btn_right);

        numberOfPages = (int) Math.ceil((double) maxNumber / itemCountPerPage);

        if (MAX_PAGE <= numberOfPages) {
            numberOfPages = MAX_PAGE;
        }

        fragments = getFragments();
        ama = new NumberPagerAdapter(getChildFragmentManager(), fragments);
        vpPager.setAdapter(ama);


        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPage = position;
                forDisableButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view.findViewById(R.id.btn_done).setOnClickListener(this);
        btn_right.setOnClickListener(this);
        btn_left.setOnClickListener(this);


        getDialog().setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getCounter.onBack();
                    dismiss();
                }
                return true;
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        int mValue = Integer.parseInt(indexValue) - startNumber;
        selectedPage = (mValue == 0) ? 0 : (mValue / itemCountPerPage);

        if (selectedPage == (numberOfPages - 1)) {
            // if (selectedPage == 0) {
            vpPager.setCurrentItem(selectedPage);

        } else {
            vpPager.setCurrentItem(selectedPage);
        }

        forDisableButton();

        if ((selectedPage == 0) && ((numberOfPages - 1) == 0)) {
            btn_left.setVisibility(View.GONE);
            btn_right.setVisibility(View.GONE);
        }

        ViewTreeObserver vto = vpPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                vpPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // int width = vpPager.getMeasuredWidth();
                int height = vpPager.getMeasuredHeight();


                ViewGroup.LayoutParams btnLeftParams = btn_left.getLayoutParams();
                btnLeftParams.height = height;
                btn_left.setLayoutParams(btnLeftParams);

                ViewGroup.LayoutParams btnRightParams = btn_right.getLayoutParams();
                btnRightParams.height = height;
                btn_right.setLayoutParams(btnRightParams);


            }
        });


        return view;
    }


    void nextPage() {
        if (selectedPage < numberOfPages) {
            selectedPage++;
            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

        forDisableButton();
        vpPager.setCurrentItem(selectedPage);

    }


    void forDisableButton() {
        if (selectedPage >= (numberOfPages - 1)) {
            selectedPage = (numberOfPages - 1);
            btn_right.setEnabled(false);
            btn_right.setAlpha(0.3f);

            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

        if (selectedPage <= 0) {
            selectedPage = 0;
            btn_left.setEnabled(false);
            btn_left.setAlpha(0.3f);


        }


        if (selectedPage < (numberOfPages - 1)) {
            btn_right.setEnabled(true);
            btn_right.setAlpha(1.0f);
        }

        if (selectedPage > 0) {
            btn_left.setEnabled(true);
            btn_left.setAlpha(1.0f);
        }

    }

    void prevousPage() {
        if (selectedPage > 0) {
            selectedPage--;
            btn_right.setEnabled(true);
            btn_right.setAlpha(1.0f);
        }

        forDisableButton();
        vpPager.setCurrentItem(selectedPage);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                if (getCounter != null) {
                    getCounter.counterValue(NumberPickerDialog.this, indexValue);
                }

//                dismiss();
                break;

            case R.id.btn_left:
                prevousPage();
                break;

            case R.id.btn_right:
                nextPage();
                break;


        }
    }


    @Override
    public void itemClick(int item, String countIndex) {
        for (NumberPickFragment f : fragments) {
            if (f.adapter instanceof GridAdapter) {
                ((GridAdapter) f.adapter).setSelectedValue("-1");
                f.adapter.notifyDataSetChanged();
            }
        }

        //UtilityFunctions.handleToast(getActivity(), "called-->" + countIndex);
        indexValue = countIndex;
    }


    private List<NumberPickFragment> getFragments() {

        List fList = new ArrayList();


        int lastPageItem = itemCountPerPage - ((numberOfPages * itemCountPerPage) - maxNumber);

        for (int i = 0; i < numberOfPages; i++) {
            NumberPickFragment f = NumberPickFragment.newInstance("Fragment " + (i + 1), (i + 1), indexValue);
            f.setClickLisner(this);
            f.setStartNumber(startNumber);
            f.setNumberOfColumns(numberOfColumns);
            if (i == numberOfPages - 1) {
                f.setItemCount(lastPageItem);
            } else {
                f.setItemCount(itemCountPerPage);
            }

            f.setItemCountPerPage(itemCountPerPage);
            fList.add(f);
        }

        return fList;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (getCounter != null) {
            getCounter.dismissMethod();
        }
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        if (numberOfColumns >= COLUMN_LIMIT) {
            this.numberOfColumns = COLUMN_LIMIT;
        }
    }

    public void setItemCountPerPage(int itemCountPerPage) {
        this.itemCountPerPage = itemCountPerPage;
        if (itemCountPerPage >= MAX_PAGE_ITEM_LIMIT) {
            this.itemCountPerPage = MAX_PAGE_ITEM_LIMIT;
        }

        if (maxNumber < itemCountPerPage) {
            this.itemCountPerPage = maxNumber;
        }

    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }


}