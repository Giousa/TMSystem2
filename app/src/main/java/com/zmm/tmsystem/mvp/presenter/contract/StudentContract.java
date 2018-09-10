package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:下午1:21
 */

public interface StudentContract {

    interface IStudentModel{

        Observable<BaseBean<StudentBean>> addStudent(StudentBean studentBean);

        Observable<BaseBean<StudentBean>> addStudentAndPic(
                String tId,String name,int gender,long birthday,String phone,String address,String guardian1,
                String guardian1Phone,String guardian2,String guardian2Phone, MultipartBody.Part file);

        Observable<BaseBean<String>> updateStudent(StudentBean studentBean);

        Observable<BaseBean<String>> deleteStudent(String id);

        Observable<BaseBean<String>> removeStudent(String id);

        Observable<BaseBean<String>> returnStudent(String id);

        Observable<BaseBean<List<StudentBean>>> queryAllStudentsByTeacherId(String id);

        Observable<BaseBean<List<StudentBean>>> queryRemoveStudentsByTeacherId(String id);

        //添加托管学生
        Observable<BaseBean<String>> addChildcareStudents(String termId,List<String> idList);

        //上传学生头像
        Observable<BaseBean<StudentBean>> uploadStudentPic(String id, MultipartBody.Part file);


        //根据id获取学生
        Observable<BaseBean<StudentBean>> getStudentById(String id);



    }

    interface StudentView extends BaseView{

        void inputSuccess(int type,String content);

        void addSuccess(StudentBean studentBean);

        void addChildcareStudentSuccess(String msg);

        void updateSuccess();

        void deleteStudent(String msg);

        void querySuccess(List<StudentBean> studentBeans);

        void queryStudent(StudentBean studentBean);
    }
}
