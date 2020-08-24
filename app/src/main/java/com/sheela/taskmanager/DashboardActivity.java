package com.sheela.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sheela.taskmanager.api.UserAPI;
import com.sheela.taskmanager.model.User;
import com.sheela.taskmanager.url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
     ImageView imgProgileImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        imgProgileImg=findViewById(R.id.imgProgileImg);
        imgProgileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadCurrentUser();
            }
        });

    }
    private void loadCurrentUser() {

        UserAPI usersAPI = Url.getInstance().create(UserAPI.class);
        Call<User> userCall = usersAPI.getUserDetails(Url.token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DashboardActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = Url.imagePath +  response.body().getImage_id();

                //Picasso.get().load(imgPath).into(imgProgileImg);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}