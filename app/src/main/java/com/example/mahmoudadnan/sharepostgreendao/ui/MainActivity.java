package com.example.mahmoudadnan.sharepostgreendao.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.DaoSession;
import com.example.mahmoudadnan.sharepostgreendao.model.Parent;
import com.example.mahmoudadnan.sharepostgreendao.model.ParentDao;
import com.example.mahmoudadnan.sharepostgreendao.model.Post;
import com.example.mahmoudadnan.sharepostgreendao.model.PostDao;
import com.example.mahmoudadnan.sharepostgreendao.model.SchoolLead;
import com.example.mahmoudadnan.sharepostgreendao.model.SchoolLeadDao;
import com.example.mahmoudadnan.sharepostgreendao.model.Student;
import com.example.mahmoudadnan.sharepostgreendao.model.StudentDao;
import com.example.mahmoudadnan.sharepostgreendao.model.Teacher;
import com.example.mahmoudadnan.sharepostgreendao.model.TeacherDao;
import com.example.mahmoudadnan.sharepostgreendao.ui.adapter.PostAdapter;
import com.example.mahmoudadnan.sharepostgreendao.utils.App;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE;
import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.IMAGE_GALLERY_REQUEST;
import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_EMAIL;
import static com.example.mahmoudadnan.sharepostgreendao.utils.Constants.USER_TYPE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.createET) EditText createPostET;
    @BindView(R.id.openIMG) ImageButton openImageBTN;
    @BindView(R.id.postBTN) Button postBTN;
    @BindView(R.id.postIMG) ImageView postImage;
    @BindView(R.id.postsRC)RecyclerView postsRecyclerView;

    Uri uri;

    PostAdapter adapter ;
    List<Post> posts;

    Post post;

    String email, type, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        postImage.setVisibility(View.GONE);

        Intent intent = getIntent();
        email = intent.getStringExtra(USER_EMAIL);
        type = intent.getStringExtra(USER_TYPE);

        posts = new ArrayList<>();
        posts.addAll(getAppDaoSession().getPostDao().loadAll());
        adapter = new PostAdapter(posts, getApplicationContext());

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        postsRecyclerView.setAdapter(adapter);
    }
    @OnClick(R.id.openIMG)
    public void chooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Choose image")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        takeImageIntent();
                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chooseImageGallary();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takeImageIntent(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
        }else {
            Intent takeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takeImage.resolveActivity(getPackageManager()) != null){
                startActivityForResult(takeImage, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        }
    }

    private void chooseImageGallary(){
        Intent imageGallaryIntent = new Intent(Intent.ACTION_PICK);
        imageGallaryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(imageGallaryIntent,"SELECT IMAGE"),IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeImageIntent();
        }else {
            Toast.makeText(this, "App need Camera Permission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap)extras.get("data");
                uri= getImageUri(getApplicationContext(), imageBitmap);
                postImage.setVisibility(View.VISIBLE);
                postImage.setImageURI(uri);

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Capture iamge cancelled", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                postImage.setVisibility(View.VISIBLE);
                postImage.setImageURI(uri);
                Log.e("Uri is :", String.valueOf(uri));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        List<Post> postList;
        switch (id){
            case R.id.action_settings:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, PositionActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;

            case R.id.parentPosts:
                postList = getAppDaoSession().getPostDao().queryBuilder().where(PostDao.Properties.OwnerType.eq("Parent")).list();
                adapter = new PostAdapter(postList, MainActivity.this);
                postsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.teacherPosts:
                postList = getAppDaoSession().getPostDao().queryBuilder().where(PostDao.Properties.OwnerType.eq("Teacher")).list();
                adapter = new PostAdapter(postList, MainActivity.this);
                postsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.leadPosts:
                postList = getAppDaoSession().getPostDao().queryBuilder().where(PostDao.Properties.OwnerType.eq("School Lead")).list();
                adapter = new PostAdapter(postList, MainActivity.this);
                postsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.studentPosts:
                postList = getAppDaoSession().getPostDao().queryBuilder().where(PostDao.Properties.OwnerType.eq("Student")).list();
                adapter = new PostAdapter(postList, MainActivity.this);
                postsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.allPosts:
                adapter = new PostAdapter(posts, MainActivity.this);
                postsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @OnClick(R.id.postBTN)
    public void createPost() {
        String postTxt = createPostET.getText().toString();
        String imageUri = String.valueOf(uri);

        switch (type){
            case "Parent":
                List<Parent> parentList = getAppDaoSession().getParentDao().queryBuilder().where(ParentDao.Properties.Email.eq(email)).limit(1).list();
                username = parentList.get(0).getUsername();

                if (!postTxt.isEmpty() || !imageUri.isEmpty()) {
                    post = new Post(null,postTxt, imageUri, username, "Parent");
                    getAppDaoSession().getPostDao().insert(post);
                    createPostET.setText("");
                    postImage.setVisibility(View.GONE);
                    adapter.add(post);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no post", Toast.LENGTH_LONG).show();
                }
                break;

            case "Teacher":
                List<Teacher> teacherList = getAppDaoSession().getTeacherDao().queryBuilder().where(TeacherDao.Properties.Email.eq(email)).limit(1).list();
                username = teacherList.get(0).getUsername();
                if (!postTxt.isEmpty() || !imageUri.isEmpty()) {
                    post = new Post(null, postTxt, imageUri, username, "Teacher");
                    getAppDaoSession().getPostDao().insert(post);
                    createPostET.setText("");
                    postImage.setVisibility(View.GONE);
                    adapter.add(post);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no post", Toast.LENGTH_LONG).show();
                }
                break;

            case "School Lead":
                List<SchoolLead> schoolLeadList = getAppDaoSession().getSchoolLeadDao().queryBuilder().where(SchoolLeadDao.Properties.Email.eq(email)).limit(1).list();
                username = schoolLeadList.get(0).getUsername();
                if (!postTxt.isEmpty() || !imageUri.isEmpty()) {
                    post = new Post(null,postTxt, imageUri, username, "School Lead");
                    getAppDaoSession().getPostDao().insert(post);
                    createPostET.setText("");
                    postImage.setVisibility(View.GONE);
                    adapter.add(post);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no post", Toast.LENGTH_LONG).show();
                }
                break;

            case "Student":
                List<Student> studentList = getAppDaoSession().getStudentDao().queryBuilder().where(StudentDao.Properties.Email.eq(email)).limit(1).list();
                username = studentList.get(0).getUsername();
                if (!postTxt.isEmpty() || !imageUri.isEmpty()) {
                    post = new Post(null,postTxt, imageUri, username, "Student");
                    getAppDaoSession().getPostDao().insert(post);
                    createPostET.setText("");
                    postImage.setVisibility(View.GONE);
                    adapter.add(post);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no post", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private DaoSession getAppDaoSession() {
        return ((App)getApplication()).getDaoSession();
    }

}
