package retrofit;

import com.example.myapi.models.JsonResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyAPI {


//    @Headers("Content-Type: application/json")
//    @POST("users/")
//    static Call<user> saveUser(@Body String body) {
//        return null;
//    }


    @FormUrlEncoded
    @POST("register.php")
    Call<JsonResponse> mRegister(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<JsonResponse> mLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
