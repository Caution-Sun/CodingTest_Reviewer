package org.techtown.codingtest_reviewer;

import android.view.View;

public interface OnTestClickListener {
    public void onItemClick(TestAdapter.ViewHolder holder, View view, int position);
}
