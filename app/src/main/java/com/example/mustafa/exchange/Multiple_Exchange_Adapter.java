package com.example.mustafa.exchange;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Multiple_Exchange_Adapter extends ArrayAdapter<String> {

    private final ArrayList<String> useremail;
    private final ArrayList<String> userImage;
    private final ArrayList<String> userComment;
    private final ArrayList<String> userdesired_1kişi;


    ImageView sendNoti;
    private final ArrayList<String> useremail1;
    private final ArrayList<String> userImage1;
    private final ArrayList<String> userComment1;
    private final ArrayList<String> userdesired_2kişi;



    private final Activity context;


    public Multiple_Exchange_Adapter(ArrayList<String> useremail, ArrayList<String> userImage, ArrayList<String> userComment, ArrayList<String> userdesired_1kişi, ArrayList<String> useremail1, ArrayList<String> userImage1, ArrayList<String> userComment1, ArrayList<String> userdesired_2kişi, Activity context) {
        super(context, R.layout.multiple_exchange_list,useremail);
        this.useremail = useremail;
        this.userImage = userImage;
        this.userComment = userComment;
        this.userdesired_1kişi = userdesired_1kişi;
        this.userdesired_2kişi = userdesired_2kişi;
        this.useremail1 = useremail1;
        this.userImage1 = userImage1;
        this.userComment1 = userComment1;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.multiple_exchange_list,null,true);

        TextView useremailText=(TextView) customView.findViewById(R.id.username_1kişi);
        TextView commentText=(TextView) customView.findViewById(R.id.itemname_1kişi);
        ImageView imageView =(ImageView) customView.findViewById(R.id.image_1kişi);
        TextView userdesired=(TextView) customView.findViewById(R.id.desiredthing);

        sendNoti=(ImageView)customView.findViewById(R.id.multiplenoti);


        TextView useremailText2=(TextView) customView.findViewById(R.id.username_2kişi);
        TextView commentText2=(TextView) customView.findViewById(R.id.itemname_2kişi);
        ImageView imageView2 =(ImageView) customView.findViewById(R.id.imageView_2kişi);
        TextView userdesired_2=(TextView) customView.findViewById(R.id.desiredthing1);


        useremailText.setText(useremail.get(position));
        userdesired.setText(userdesired_1kişi.get(position));
        useremailText2.setText(useremail1.get(position));
        userdesired_2.setText(userdesired_2kişi.get(position));

        commentText.setText(userComment.get(position));
        commentText2.setText(userComment1.get(position));
        // Picasso.with(context).load(userImage.get(position)).into(imageView);
        Picasso.get()
                .load(userImage.get(position))
                .resize(150, 150)
                .centerCrop()
                .into(imageView);

        Picasso.get()
                .load(userImage1.get(position))
                .resize(150, 150)
                .centerCrop()
                .into(imageView2);


        sendNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
sendNotification(FragmentMultiple.kişi_1_email);
sendNotification(FragmentMultiple.kişi_2_email);
            }
        });

        return customView;
    }
    private void sendNotification(final String send_Email)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic N2NhOWY4NWEtODE1Yi00ZWFmLTg1YWMtMmE3YmFhZWRlMDg5");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"f3813aa0-546a-4555-aa56-cf23bc652836\","
                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_Email + "\"}],"

                                + "\"headings\": {\"en\":\""+MainActivity.userEmail+" \"},"
                                + "\"contents\": {\"en\":\" Sizinle değişim yapmak istiyor\"},"
                                + "\"buttons\":  [{\"id\": \"id1\", \"text\": \"Onayla\", \"icon\": \"ic_menu_share\"}, {\"id\": \"id2\", \"text\": \"İptal\", \"icon\": \"ic_menu_send\"}]"
                                + "}";



                        //   System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        //  System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        //  System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }


}