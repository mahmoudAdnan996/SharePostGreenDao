package com.example.mahmoudadnan.sharepostgreendao.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.DaoSession;
import com.example.mahmoudadnan.sharepostgreendao.model.Parent;
import com.example.mahmoudadnan.sharepostgreendao.model.SchoolLead;
import com.example.mahmoudadnan.sharepostgreendao.model.Student;
import com.example.mahmoudadnan.sharepostgreendao.model.Teacher;
import com.example.mahmoudadnan.sharepostgreendao.utils.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_EMAIL;
import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_TYPE;

public class Register extends AppCompatActivity {

    @BindView(R.id.SignUpEmailET)
    TextInputLayout emailText;
    @BindView(R.id.SignUpNameET) TextInputLayout nameTex;
    @BindView(R.id.SignUpPassET) TextInputLayout passTex;
    @BindView(R.id.SignUpRpassET) TextInputLayout rPassTex;
    @BindView(R.id.signUpBTN) Button signUp;

    Parent parent;
    Teacher teacher;
    SchoolLead lead;
    Student student;

    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        type = intent.getStringExtra(USER_TYPE);
        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.signUpBTN)
    public void singUp() {
        String email = emailText.getEditText().getText().toString().trim();
        String username = nameTex.getEditText().getText().toString().trim();
        String password = passTex.getEditText().getText().toString().trim();
        String rPassword = rPassTex.getEditText().getText().toString().trim();

        parent = new Parent(null, email, username, password);
        teacher = new Teacher(null, email, username, password);
        student = new Student(null, email, username, password);
        lead = new SchoolLead(null, email, username, password);

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || rPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_LONG).show();
        }
        else {
            if (password.equals(rPassword)) {
                if (type.equals("Parent")){
                    getAppDaoSession().getParentDao().insert(parent);
                    Toast.makeText(getApplicationContext(), "Parent Added Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "Parent");
                    startActivity(intent);
                    finish();
                }
                else if (type.equals("Teacher")){
                    getAppDaoSession().getTeacherDao().insert(teacher);
                    Toast.makeText(getApplicationContext(), "Teacher Added Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "Teacher");
                    startActivity(intent);
                    finish();
                }
                else if (type.equals("School Lead")){
                    getAppDaoSession().getSchoolLeadDao().insert(lead);
                    Toast.makeText(getApplicationContext(), "Lead Added Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "School Lead");
                    startActivity(intent);
                    finish();
                }
                else if (type.equals("Student")){
                    getAppDaoSession().getStudentDao().insert(student);
                    Toast.makeText(getApplicationContext(), "Student Added Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra(USER_EMAIL, email);
                    intent.putExtra(USER_TYPE, "Student");
                    startActivity(intent);
                    finish();
                }
            } else if (password != rPassword) {
                Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_LONG).show();
            }
        }
    }
    private DaoSession getAppDaoSession() {
        return ((App)getApplication()).getDaoSession();
    }

}
