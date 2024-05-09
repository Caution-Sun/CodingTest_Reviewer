package com.example.codingtest_reviewe.recyclerView;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.codingtest_reviewe.R;

public class TaskItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private onItemSwipeListener listener;
    public TaskItemTouchHelperCallback(onItemSwipeListener listener){
        this.listener = listener;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeThreshold(viewHolder);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction == ItemTouchHelper.LEFT){
            //EDIT
            listener.editItem(viewHolder.getAdapterPosition());
        }else if(direction == ItemTouchHelper.RIGHT){
            //DELETE
            listener.removeItem(viewHolder.getAdapterPosition());
        }
    }

    // clearView after Swiped Event
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //super.clearView(recyclerView, viewHolder);
        getDefaultUIUtil().clearView(viewHolder.itemView.findViewById(R.id.itemLayout));
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue * 3;
    }


    // Swipe Item without background
    // when swiped, set background imageviews' Visibility
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View deleteImage = viewHolder.itemView.findViewById(R.id.imageDelete);
            View editImage = viewHolder.itemView.findViewById(R.id.imageEdit);
            View itemView = viewHolder.itemView.findViewById(R.id.itemLayout);
            if(dX > 0){
                deleteImage.setVisibility(View.VISIBLE);
                editImage.setVisibility(View.INVISIBLE);
                getDefaultUIUtil().onDraw(c, recyclerView, itemView, dX, dY, actionState, isCurrentlyActive);
            }else if(dX < 0){
                deleteImage.setVisibility(View.INVISIBLE);
                editImage.setVisibility(View.VISIBLE);
                getDefaultUIUtil().onDraw(c, recyclerView, itemView, dX, dY, actionState, isCurrentlyActive);
            }
        }


    }

}
