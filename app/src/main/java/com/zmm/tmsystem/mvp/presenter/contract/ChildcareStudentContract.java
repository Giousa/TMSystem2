package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ChildcareListBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SchoolBean;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/21
 * Time:下午5:49
 */

public interface ChildcareStudentContract {

    interface IChildcareStudentModel{

        Observable<BaseBean<List<ChildcareStudentBean>>> queryAllChildcareStudents(String id);

        Observable<BaseBean<ChildcareStudentBean>> updateChildcareStudent(int type,String id,int level,String content);

        Observable<BaseBean<String>> deleteChildcareStudent(String id);

        Observable<BaseBean<ChildcareStudentBean>> findChildcareStudentById(String id);

        Observable<BaseBean<List<SchoolBean>>> querySchools();

        Observable<BaseBean<String>> uploadPics(String t_id, String listJson, MultipartBody.Part [] part);

        //上传学生头像
        Observable<BaseBean<StudentBean>> uploadChildcareStudentPic(String id, MultipartBody.Part file);

        //获取学生消费详情
        Observable<BaseBean<MoneyBean>> getMoneyByStudentId(String studentId);


    }

    interface ChildcareStudentView extends BaseView{

        void querySuccess(List<ChildcareStudentBean> childcareStudentBeans);
        void updateSuccess(ChildcareStudentBean childcareStudentBean);
        void deleteSuccess();
        void queryMoney(MoneyBean moneyBean);



    }
}
