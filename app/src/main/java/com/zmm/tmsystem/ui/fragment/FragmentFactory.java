package com.zmm.tmsystem.ui.fragment;

import java.util.HashMap;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/8
 * Email:65489469@qq.com
 */
public class FragmentFactory {

    public static HashMap<Integer, ProgressFragment> hashMap = new HashMap<>();

    public static ProgressFragment createFragment(int position) {
        //内存中如果已经有当前根据索引生成的fragment,复用之前的fragment对象,内存中没有索引指向的fragment对象,创建
        ProgressFragment fragment = hashMap.get(position);
        if(fragment != null && fragment.isVisible()){
            return fragment;
        }else{
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new ManageFragment();
                    break;
                case 2:
                    fragment = new ChatFragment();
                    break;
                case 3:
                    fragment = new CommentFragment();
                    break;
            }
            //集合将创建过的fragment,管理起来
            hashMap.put(position, fragment);
            return fragment;
        }
    }

}
