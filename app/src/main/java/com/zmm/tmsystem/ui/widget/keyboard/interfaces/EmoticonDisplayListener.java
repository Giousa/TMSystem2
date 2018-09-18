package com.zmm.tmsystem.ui.widget.keyboard.interfaces;

import android.view.ViewGroup;

import com.zmm.tmsystem.ui.widget.keyboard.adpater.EmoticonsAdapter;

public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
