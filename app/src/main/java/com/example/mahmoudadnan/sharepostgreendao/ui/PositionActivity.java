package com.example.mahmoudadnan.sharepostgreendao.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.mahmoudadnan.sharepostgreendao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_TYPE;

public class PositionActivity extends AppCompatActivity {

    @BindView(R.id.parentBtn) Button parentBTN;
    @BindView(R.id.teacherBtn) Button teacherBTN;
    @BindView(R.id.leadBtn) Button leadBTN;
    @BindView(R.id.studentBtn) Button studentBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    @OnClick(R.id.parentBtn)
    public void asParent() {
        String parentText = parentBTN.getText().toString();
        Intent intent = new Intent(this, Welcome.class);
        intent.putExtra(USER_TYPE, parentText);
        startActivity(intent);
    }

    @OnClick(R.id.teacherBtn)
    public void asTeacher() {
        String teacherText = teacherBTN.getText().toString();
        Intent intent = new Intent(this, Welcome.class);
        intent.putExtra(USER_TYPE, teacherText);
        startActivity(intent);
    }

    @OnClick(R.id.leadBtn)
    public void asLead() {
        String leadText = leadBTN.getText().toString();
        Intent intent = new Intent(this, Welcome.class);
        intent.putExtra(USER_TYPE, leadText);
        startActivity(intent);
    }

    @OnClick(R.id.studentBtn)
    public void asStudent() {
        String studentText = studentBTN.getText().toString();
        Intent intent = new Intent(this, Welcome.class);
        intent.putExtra(USER_TYPE, studentText);
        startActivity(intent);
    }
}
