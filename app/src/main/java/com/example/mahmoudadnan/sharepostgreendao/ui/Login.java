package com.example.mahmoudadnan.sharepostgreendao.ui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.DaoSession;
import com.example.mahmoudadnan.sharepostgreendao.model.Parent;
import com.example.mahmoudadnan.sharepostgreendao.model.ParentDao;
import com.example.mahmoudadnan.sharepostgreendao.model.SchoolLead;
import com.example.mahmoudadnan.sharepostgreendao.model.SchoolLeadDao;
import com.example.mahmoudadnan.sharepostgreendao.model.Student;
import com.example.mahmoudadnan.sharepostgreendao.model.StudentDao;
import com.example.mahmoudadnan.sharepostgreendao.model.Teacher;
import com.example.mahmoudadnan.sharepostgreendao.model.TeacherDao;
import com.example.mahmoudadnan.sharepostgreendao.utils.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_EMAIL;
import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_TYPE;

public class Login extends AppCompatActivity {

    @BindView(R.id.SignInEmailET) TextInputLayout emailTxt;
    @BindView(R.id.SignInPassET) TextInputLayout passTxt;
    @BindView(R.id.SignInBTN) Button signIn;

    boolean isExist;

    String type;

    String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        type = intent.getStringExtra(USER_TYPE);
        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.SignInBTN)
    public void login() {
        String email = emailTxt.getEditText().getText().toString().trim();
        String password = passTxt.getEditText().getText().toString().trim();

        if (type.equals("Parent")){

            List<Parent> parentList = getAppDaoSession().getParentDao().queryBuilder().where(ParentDao.Properties.Email.eq(email)).limit(1).list();
            if (parentList != null && parentList.size() != 0){
                emailText = parentList.get(0).getEmail();
//            Log.e("Email is", emailTxt);
                if (email.equals(emailText)){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Toast.makeText(Login.this, "Login as Parent", Toast.LENGTH_LONG).show();
                    intent.putExtra(USER_TYPE, "Parent");
                    intent.putExtra(USER_EMAIL, email);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();

            }

        }
        else if (type.equals("Teacher")){
            List<Teacher> teacherList = getAppDaoSession().getTeacherDao().queryBuilder().where(TeacherDao.Properties.Email.eq(email)).limit(1).list();
           if (teacherList != null && teacherList.size() != 0){
               emailText = teacherList.get(0).getEmail();
               if (email.equals(emailText)){
                   Intent intent = new Intent(Login.this, MainActivity.class);
                   Toast.makeText(Login.this, "Login as teacher", Toast.LENGTH_LONG).show();
                   intent.putExtra(USER_EMAIL, email);
                   intent.putExtra(USER_TYPE, "Teacher");
                   startActivity(intent);
                   finish();
               }
               else {
                   Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();
               }
           }
           else {
               Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();

           }

        }
        else if (type.equals("School Lead")){
            List<SchoolLead> schoolLeadList = getAppDaoSession().getSchoolLeadDao().queryBuilder().where(SchoolLeadDao.Properties.Email.eq(email)).limit(1).list();
            if (schoolLeadList != null && schoolLeadList.size() != 0){
                emailText = schoolLeadList.get(0).getEmail();
                if (email.equals(emailText)){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Toast.makeText(Login.this, "Login as school lead", Toast.LENGTH_LONG).show();
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "School Lead");
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();

            }

        }
        else if (type.equals("Student")){
            List<Student> studentList = getAppDaoSession().getStudentDao().queryBuilder().where(StudentDao.Properties.Email.eq(email)).limit(1).list();
            if (studentList != null && studentList.size() != 0){
                emailText = studentList.get(0).getEmail();
                if (email.equals(emailText)){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Toast.makeText(Login.this, "Login as student", Toast.LENGTH_LONG).show();
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "Student");
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "This user not found", Toast.LENGTH_LONG).show();

            }

        }
    }
    private DaoSession getAppDaoSession() {
        return ((App)getApplication()).getDaoSession();
    }

}
