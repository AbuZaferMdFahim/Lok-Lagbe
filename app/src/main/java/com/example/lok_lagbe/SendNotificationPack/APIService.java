package com.example.lok_lagbe.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA5XC-uaw:APA91bFlpr35-DUQu6oV6XxARqIasF4LfJk07-MgeyDFLxJK9qt4X184HCX3TW-Krv8H7un7I0x46xVYfnF4Icx1xAeJN_cn47hPRFKXIQbLIgds0g_3y9EAwgCPn3Wd3ADbFqVGosq6" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

