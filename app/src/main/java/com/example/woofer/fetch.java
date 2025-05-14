package com.example.woofer;
import android.app.Activity;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


class fetch {

    private String Theurl;
    public void myUrlBuilder(String url){
        this.Theurl=url;

    }

    public void getData(Activity activity,  requestHandler handler) {
        System.out.println(activity);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Theurl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {

                    throw new IOException("Error" + response);
                }

                final String responseData = response.body().string();
                activity.runOnUiThread(() -> handler.processResponse(responseData));
            }
        });
    }
}





