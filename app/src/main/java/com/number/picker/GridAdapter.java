package com.number.picker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.num.picker.R;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    String value = "1";

    private int counter = 0;

    int itemCounter = 50;
    int lastItem = 50;

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    String selectedValue = "0";
    int startNumber = 0;


    // data is passed into the constructor
    public GridAdapter(Context context, int counter, int itemCounter, int lastItem,
                       int startNumber) {
        this.mInflater = LayoutInflater.from(context);   //for inflation
        this.counter = counter;                          //item text visible
        this.itemCounter = itemCounter;                  //total number of items visible
        this.lastItem = lastItem;                        //last item of the visible item
        this.startNumber = startNumber;                  //picker start from this number
    }


    public String getValue() {
        return value;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.number_grid_item, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // String animal = mData[position];

        if (position < lastItem) {
            String textValue = "" + (counter + position + startNumber);
            holder.myTextView.setText(textValue);

            if (textValue.equalsIgnoreCase(selectedValue)) {

                holder.rl_click.setBackgroundResource(R.drawable.circle_grid_blue);
                holder.myTextView.setTextColor(Color.WHITE);
            } else {
                holder.rl_click.setBackgroundResource(R.drawable.circle_shape_light_gray);
                holder.myTextView.setTextColor(Color.parseColor("#9c9797"));
            }
            holder.rl_click.setVisibility(View.VISIBLE);
        } else {
            holder.rl_click.setVisibility(View.INVISIBLE);
        }


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return itemCounter;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        RelativeLayout rl_click;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.tv_p1);
            rl_click = (RelativeLayout) itemView.findViewById(R.id.rl_click);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() < lastItem) {
                value = "" + (counter + getAdapterPosition() + startNumber);

                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getAdapterPosition(), value);
                }
            }
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}