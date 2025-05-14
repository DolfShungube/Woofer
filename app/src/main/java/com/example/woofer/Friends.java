package com.example.woofer;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Friends {


    public  void getChats(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);


            //JSONArray jsonArray = jsonObject.getJSONArray("CARS");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2= jsonArray.getJSONObject(i);
              System.out.println(jsonObject2);
              System.out.println("DOLF IS IN");


            }


        }

        catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }


    public void generateChatLayout( String Name,int userID,LinearLayout parentLayout, Context context){

        LinearLayout layout = new LinearLayout(context);

        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int marginBottom = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
        params.setMargins(0, 0, 0, marginBottom);

        int padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());

        layout.setId(userID);
        layout.setLayoutParams(params);
        layout.setBackground(ContextCompat.getDrawable(context, R.drawable.box_background));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(padding, padding, padding, padding);


        ImageView userImage= new ImageView(context);
        int imageSize = 60;
        int imageSizePx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, imageSize, context.getResources().getDisplayMetrics());




        LinearLayout.LayoutParams Imageparams = new LinearLayout.LayoutParams(imageSizePx, imageSizePx);
       //userImage.setId("userImage"+userID)
        userImage.setLayoutParams(Imageparams);
        userImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        userImage.setImageResource(R.drawable.images);



        TextView friendsName = new TextView(context);
        LinearLayout.LayoutParams friendsNameParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int marginStart = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics()
        );
       // friendsName.setId("Name"+userID);
        friendsNameParams.setMarginStart(marginStart);
        friendsName.setLayoutParams(friendsNameParams);
        friendsName.setText(Name);
        friendsName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);




        layout.addView(userImage);
        layout.addView(friendsName);
        parentLayout.addView(layout);

    }
}
