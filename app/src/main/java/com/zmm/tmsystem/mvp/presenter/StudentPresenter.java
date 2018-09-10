package com.zmm.tmsystem.mvp.presenter;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.TeacherCacheUtil;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.mvp.presenter.contract.StudentContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;
import com.zmm.tmsystem.ui.widget.DateSelectView;
import com.zmm.tmsystem.ui.widget.SimpleInputDialog;
import com.zmm.tmsystem.ui.widget.SingleSelectView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:下午2:24
 */

public class StudentPresenter extends BasePresenter<StudentContract.IStudentModel,StudentContract.StudentView> {


    private String title = null;
    private String hint = null;
    private int type;
    private String name;
    private LinearLayout mRootView;
    private int mScreenWidth;
    private List<String> mList;



    @Inject
    public StudentPresenter(StudentContract.IStudentModel model, StudentContract.StudentView view) {
        super(model, view);
    }

    /**
     * 获取所有学生
     * @param id
     */
    public void queryAllStudents(String id) {

        mModel.queryAllStudentsByTeacherId(id)
                .compose(RxHttpResponseCompat.<List<StudentBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<StudentBean>>(mContext) {

                    @Override
                    public void onNext(List<StudentBean> studentBeans) {
                        mView.querySuccess(studentBeans);
                    }
                });
    }


    /**
     * 获取移除学生
     * @param id
     */
    public void queryRemoveStudents(String id) {
        mModel.queryRemoveStudentsByTeacherId(id)
                .compose(RxHttpResponseCompat.<List<StudentBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<StudentBean>>(mContext) {

                    @Override
                    public void onNext(List<StudentBean> studentBeans) {
                        mView.querySuccess(studentBeans);
                    }
                });
    }

    /**
     * 根据id获取学生信息
     * @param id
     */
    public void getStudentById(String id) {

        mModel.getStudentById(id)
                .compose(RxHttpResponseCompat.<StudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StudentBean>(mContext) {

                    @Override
                    public void onNext(StudentBean studentBean) {
                        mView.queryStudent(studentBean);
                    }

                });

    }

    /**
     * 添加学生
     * @param studentBean
     */
    public void addStudent(StudentBean studentBean) {

        mModel.addStudent(studentBean)
                .compose(RxHttpResponseCompat.<StudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StudentBean>(mContext) {

                    @Override
                    public void onNext(StudentBean studentBean) {
                        mView.addSuccess(studentBean);
                    }

                });

    }

    /**
     * 添加学生+头像
     * @param path
     */
    public void addStudentAndPic(String tId,String name,int gender,long birthday,String phone,String address,String guardian1,
                                 String guardian1Phone,String guardian2,String guardian2Phone,String path) {

        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);


        mModel.addStudentAndPic(tId,name,gender,birthday,phone,address,guardian1,guardian1Phone,guardian2,guardian2Phone,part)
                .compose(RxHttpResponseCompat.<StudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StudentBean>(mContext) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        ToastUtils.SimpleToast(mContext,"正在添加学生...");
                    }

                    @Override
                    public void onNext(StudentBean studentBean) {
                        mView.addSuccess(studentBean);
                    }

                });

    }

    /**
     * 更新学生数据 这里不做任何网络请求，仅仅只是为了填充学生信息，最后在点击事件中统一上传
     * @param type
     * @param name
     * @param rootView
     * @param screenWidth
     */
    public void updateStudentData(int type, String name, LinearLayout rootView, int screenWidth) {

        this.type = type;
        this.name = name;
        mRootView = rootView;
        mScreenWidth = screenWidth;
        if(mList != null){
            mList.clear();
            mList = null;
        }
        switch (type){

            case Constant.TYPE_STUDENT_ICON:
//                uloadIcon();
                break;
            case Constant.TYPE_STUDENT_NAME:
                title = "姓名";
                hint = "请输入您的姓名";
                inputString(false);
                break;
            case Constant.TYPE_STUDENT_GENDER:
                title = "性别";
                mList = new ArrayList<>();
                mList.add("女");
                mList.add("男");

                selectString();
                break;
            case Constant.TYPE_STUDENT_BIRTHDAY:
                title = "生日";
                selectBirthday();
                break;
            case Constant.TYPE_STUDENT_PHONE:
                title = "联系电话";
                hint = "请输入联系电话";
                inputString(true);
                break;
            case Constant.TYPE_STUDENT_ADDRESS:
                title = "地址";
                hint = "请输入您的地址";
                inputString(false);
                break;
            case Constant.TYPE_STUDENT_GUARDIAN1:
                title = "监护人";
                hint = "请输入监护人姓名";
                inputString(false);
                break;
            case Constant.TYPE_STUDENT_GUARDIANPHONE1:
                title = "监护人电话";
                hint = "请输入监护人电话";
                inputString(true);
                break;
            case Constant.TYPE_STUDENT_GUARDIAN2:
                title = "备用监护人";
                hint = "请输入监护人姓名";
                inputString(false);
                break;
            case Constant.TYPE_STUDENT_GUARDIANPHONE2:
                title = "监护人电话";
                hint = "请输入监护人电话";
                inputString(true);
                break;

        }
    }

    /**
     * 选择生日
     */
    private void selectBirthday() {


        String strBirthday = ACache.get(mContext).getAsString(Constant.STUDENT_BIRTHDAY);

        DateSelectView dateSelectView = new DateSelectView(mContext,mRootView,mScreenWidth,strBirthday);

        dateSelectView.setOnDateClickListener(new DateSelectView.OnDateClickListener() {
            @Override
            public void onDateClick(String date) {
                mView.inputSuccess(type,date);
            }
        });
    }


    /**
     * 输入框修改
     */
    private void inputString(boolean isNumberType) {

        final SimpleInputDialog simpleInputDialog = new SimpleInputDialog(mContext, title, hint,name,isNumberType);

        simpleInputDialog.setOnClickListener(new SimpleInputDialog.OnClickListener() {

            @Override
            public void onCancel() {
                simpleInputDialog.dismiss();
                mView.dismissLoading();
            }

            @Override
            public void onConfirm(String content) {
                simpleInputDialog.dismiss();
                if(TextUtils.isEmpty(content)){
                    return;
                }
                mView.dismissLoading();
                mView.inputSuccess(type,content);

            }
        });

        simpleInputDialog.show();

        //这里的功能，是用来显示 makeWindowDark()  背景变暗
        mView.showLoading();
    }



    /**
     * 选择框修改
     */
    private void selectString() {


        SingleSelectView singleSelectView = new SingleSelectView(mContext,mRootView,mScreenWidth,title,mList);

        singleSelectView.setOnSelectClickListener(new SingleSelectView.OnSelectClickListener() {
            @Override
            public void onCancel() {

                mView.dismissLoading();
            }

            @Override
            public void onConfirm(int position,String content) {
                mView.dismissLoading();
                mView.inputSuccess(type,content);
            }
        });

        mView.showLoading();
    }


    /**
     * 删除学生
     * @param id
     */
    public void deleteStudent(String id) {



        mModel.deleteStudent(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                    @Override
                    public void onNext(String s) {
                        mView.deleteStudent(s);
                    }
                });
    }

    /**
     * 移除学生
     * @param id
     */
    public void removeStudent(String id) {
        mModel.removeStudent(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                    @Override
                    public void onNext(String s) {
                        mView.deleteStudent(s);
                    }
                });

    }

    /**
     * 还原学生
     * @param id
     */
    public void returnStudent(String id) {
        mModel.returnStudent(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                    @Override
                    public void onNext(String s) {
                        mView.deleteStudent(s);
                    }
                });
    }

    /**
     * 更新学生资料
     * @param studentBean
     */
    public void updateStudent(StudentBean studentBean) {

        mModel.updateStudent(studentBean)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                    @Override
                    public void onNext(String s) {
                        mView.updateSuccess();
                    }
                });
    }

    /**
     * 添加托管学生或者补习班学生
     * @param intExtra
     * @param data
     */
    public void addSubStudents(int intExtra, List<StudentBean> data) {

        if(intExtra == 1) {
            //托管学生
            TermBean termBean = (TermBean) ACache.get(mContext).getAsObject(Constant.TERM);
            String id = termBean.getId();

            List<String> list = new ArrayList<>();
            for (StudentBean studentBean:data) {
                list.add(studentBean.getId());
            }

            System.out.println("加载新托管学生 id = "+id);

            mModel.addChildcareStudents(id,list)
                    .compose(RxHttpResponseCompat.<String>compatResult())
                    .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                        @Override
                        public void onNext(String s) {

                            mView.addChildcareStudentSuccess(s);
                            System.out.println("s = "+s);
                        }
                    });


        }else {
            //补习学生

        }

    }


    /**
     * 上传头像
     * @param id
     * @param path
     */
    public void uploadStudentPic(String id,String path) {

        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);


        mModel.uploadStudentPic(id,part)
                .compose(RxHttpResponseCompat.<StudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StudentBean>(mContext) {
                    @Override
                    public void onNext(StudentBean studentBean) {
                        System.out.println("studentBean = "+studentBean);
                        mView.queryStudent(studentBean);
                    }
                });
    }



}
