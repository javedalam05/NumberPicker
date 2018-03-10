package com.number.picker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.num.picker.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class NumberPickFragment extends Fragment {




    public void setClickLisner(ClickLisner clickLisner) {
        this.clickLisner = clickLisner;
    }

    ClickLisner clickLisner;

    String selectedValue = "0";

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setItemCountPerPage(int itemCountPerPage) {
        this.itemCountPerPage = itemCountPerPage;
    }

    int numberOfColumns = 5;
    int itemCountPerPage = 50;



    int startNumber = 0;

    int itemCount = 50;


    public static final NumberPickFragment newInstance(String message, int item,
                                                       String indexValue
    ) {
        NumberPickFragment f = new NumberPickFragment();
        Bundle bdl = new Bundle(item);
        f.item = item;
        f.selectedValue = indexValue;
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }


    private int item;
    public RecyclerView.Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.number_grid_frag, container, false);


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvNumbers);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        int counter = (item - 1) * itemCountPerPage;

        adapter = new GridAdapter(getActivity(), counter, itemCountPerPage, itemCount, startNumber);

        ((GridAdapter) adapter).setSelectedValue(selectedValue);

        ((GridAdapter) adapter).setClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String value) {

                if (clickLisner != null) {
                    clickLisner.itemClick(item, value);
                }

                ((GridAdapter) adapter).setSelectedValue(value);
                adapter.notifyDataSetChanged();
            }
        });


//        }


        recyclerView.setAdapter(adapter);


        return v;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }


    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }
}