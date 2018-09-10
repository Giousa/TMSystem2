package com.zmm.tmsystem.mvp.presenter;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.imagepicker.bean.ImageItem;
import com.zmm.tmsystem.bean.ChildcareListBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SchoolBean;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.TeacherCacheUtil;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.mvp.presenter.contract.ChildcareStudentContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;
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
 * Date:2018/7/2
 * Time:上午11:53
 */

public class ChildcareStudentPresenter extends BasePresenter<ChildcareStudentContract.IChildcareStudentModel,ChildcareStudentContract.ChildcareStudentView> {


    private String title = null;
    private String hint = null;
    private int type;
    private String name;
    private LinearLayout mRootView;
    private int mScreenWidth;
    private List<String> mList;
    private ChildcareStudentBean mChildcareStudentBean;
    private int index = 0;


    @Inject
    public ChildcareStudentPresenter(ChildcareStudentContract.IChildcareStudentModel model, ChildcareStudentContract.ChildcareStudentView view) {
        super(model, view);
    }

    /**
     * 查询所有托管学生
     * @param id
     */
    public void queryAllChildcareStudents(String id) {

        mModel.queryAllChildcareStudents(id)
                .compose(RxHttpResponseCompat.<List<ChildcareStudentBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<ChildcareStudentBean>>(mContext) {

                    @Override
                    public void onNext(List<ChildcareStudentBean> childcareStudentBeans) {
                        mView.querySuccess(childcareStudentBeans);

                    }
                });

    }

    /**
     * 删除托管学生
     * @param id
     */
    public void deleteChildcareStudent(String id) {
        mModel.deleteChildcareStudent(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onNext(String s) {
                        mView.deleteSuccess();
                    }
                });
    }

    /**
     * 更新数据
     * @param type
     * @param name
     * @param rootView
     * @param screenWidth
     * @param childcareStudentBean
     */
    public void updateChildcareStudentData(int type, String name, LinearLayout rootView, int screenWidth, ChildcareStudentBean childcareStudentBean) {

        this.type = type;
        this.name = name;
        mRootView = rootView;
        mScreenWidth = screenWidth;
        mChildcareStudentBean = childcareStudentBean;
        if(mList != null){
            mList.clear();
            mList = null;
        }
        switch (type){

            case Constant.TYPE_STUDENT_SCHOOL:
                title = "学校";
                mList = new ArrayList<>();

                queryAllSchools();
                break;
            case Constant.TYPE_STUDENT_GRADE:
                title = "年级";
                mList = new ArrayList<>();
                mList.add("幼儿园");
                mList.add("一年级");
                mList.add("二年级");
                mList.add("三年级");
                mList.add("四年级");
                mList.add("五年级");
                mList.add("六年级");
                mList.add("七年级");
                mList.add("八年级");
                mList.add("九年级");
                mList.add("高一");
                mList.add("高二");
                mList.add("高三");
                selectString();
                break;
            case Constant.TYPE_STUDENT_TEACHER:
                title = "班主任";
                hint = "请输入班主任姓名";
                inputString(false);
                break;
            case Constant.TYPE_STUDENT_TEACHER_PHONE:
                title = "班主任电话";
                hint = "请输入班主任电话";
                inputString(true);
                break;

        }

    }

    /**
     * 查询学校
     */
    private void queryAllSchools() {
        mModel.querySchools()
                .compose(RxHttpResponseCompat.<List<SchoolBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<SchoolBean>>(mContext) {

                    @Override
                    public void onNext(List<SchoolBean> schoolBeans) {
                        if(schoolBeans != null && schoolBeans.size() > 0){

                            for (SchoolBean school:schoolBeans) {
                                mList.add(school.getName());
                            }

                            selectString();
                        }
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
                update2Server(type,content);

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
                index = position;
                update2Server(type,content);
            }
        });

        mView.showLoading();
    }

    /**
     * 更新数据到服务器
     * @param type
     * @param content
     */
    private void update2Server(int type, String content) {

        if(TextUtils.isEmpty(content)){
            mView.showError("内容不能为空");
            return;
        }
        mModel.updateChildcareStudent(type,mChildcareStudentBean.getId(),index,content)
                .compose(RxHttpResponseCompat.<ChildcareStudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<ChildcareStudentBean>(mContext) {
                    @Override
                    public void onNext(ChildcareStudentBean childcareStudentBean) {
                        ToastUtils.SimpleToast(mContext,"更新成功");
                        mView.updateSuccess(childcareStudentBean);
                    }
                });
    }

    /**
     * 根据id，获取托管学生信息
     * @param id
     */
    public void findChildcareStudentById(String id) {
        mModel.findChildcareStudentById(id)
                .compose(RxHttpResponseCompat.<ChildcareStudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<ChildcareStudentBean>(mContext) {
                    @Override
                    public void onNext(ChildcareStudentBean childcareStudentBean) {
                        mView.updateSuccess(childcareStudentBean);
                    }
                });
    }

    /**
     * 上传多图片测试
     * @param images
     */
    public void uploadImages(List<ImageItem> images) {

        MultipartBody.Part [] part = new MultipartBody.Part[images.size()];

        for (int i = 0; i < images.size(); i++) {
            File file= new File(images.get(i).path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            System.out.println("图片名称file.getName() = "+file.getName());
            part[i] = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }

        String t_id = "1010101010001";

        List<TermBean> termBeans = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            TermBean termBean = new TermBean();
            termBean.setTitle("标题_"+i);
            termBean.setId("托管id_"+i);
            termBeans.add(termBean);
        }

        Gson gson = new Gson();
        String s = gson.toJson(termBeans);
        System.out.println("s = "+s);

        mModel.uploadPics(t_id,s,part)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onNext(String s) {
                        System.out.println("多图片上传s = "+s);
                    }
                });
    }

    /**
     * 上传学生头像
     * @param childcareStudentBean
     * @param path
     */
    public void uploadStudentPic(final ChildcareStudentBean childcareStudentBean, String path) {
        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);


        mModel.uploadChildcareStudentPic(childcareStudentBean.getStudent().getId(),part)
                .compose(RxHttpResponseCompat.<StudentBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StudentBean>(mContext) {
                    @Override
                    public void onNext(StudentBean studentBean) {
                        System.out.println("studentBean = "+studentBean);
                        childcareStudentBean.setStudent(studentBean);
                        mView.updateSuccess(childcareStudentBean);
                    }
                });

    }

    /**
     * 获取学生消费详情
     * @param id
     */
    public void getMoneyByStudentId(String id) {

        mModel.getMoneyByStudentId(id)
                .compose(RxHttpResponseCompat.<MoneyBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<MoneyBean>(mContext) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        mView.queryMoney(moneyBean);
                    }
                });
    }
}
