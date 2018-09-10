package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.SchoolBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午10:30
 */

public interface TeacherContract {

    public interface ITeacherModel{

        Observable<BaseBean<TeacherBean>> updateTeacherInfo(TeacherBean teacherBean);

        Observable<BaseBean<TeacherBean>> uploadTeacherPic(String id, MultipartBody.Part file);

        Observable<BaseBean<TeacherBean>> updateTeacherByType(String id, int type, String content);

        Observable<BaseBean<List<SchoolBean>>> querySchools();
    }

    public interface TeacherView extends BaseView {


        void updateSuccess(String title,TeacherBean teacherBean);

    }
}
