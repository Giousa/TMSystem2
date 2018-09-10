package com.zmm.tmsystem.bean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/21
 * Time:下午5:44
 */

public class ChildcareStudentBean extends BaseEntity {

    private String id;

    private StudentBean student;

    private CommentsBean comments;

    private String childcareId;

    private String school;

    private String grade;

    private int gradeLevel;

    private String teacher;

    private String teacherPhone;

    public CommentsBean getComments() {
        return comments;
    }

    public void setComments(CommentsBean comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public String getChildcareId() {
        return childcareId;
    }

    public void setChildcareId(String childcareId) {
        this.childcareId = childcareId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    @Override
    public String toString() {
        return "ChildcareStudentBean{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", mCommentsBean=" + comments +
                ", childcareId='" + childcareId + '\'' +
                ", school='" + school + '\'' +
                ", grade='" + grade + '\'' +
                ", gradeLevel=" + gradeLevel +
                ", teacher='" + teacher + '\'' +
                ", teacherPhone='" + teacherPhone + '\'' +
                '}';
    }
}
