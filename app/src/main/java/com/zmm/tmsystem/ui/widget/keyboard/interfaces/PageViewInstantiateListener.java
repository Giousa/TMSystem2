package com.zmm.tmsystem.ui.widget.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.zmm.tmsystem.ui.widget.keyboard.data.PageEntity;

public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
