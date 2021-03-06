package com.example.easyhelp.API;

import com.example.easyhelp.RegistrationAndLogin.ForgetPassword.Items.ForgetPasswordItems;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.BeforeRegistrationUserCheckAPI;
import com.example.easyhelp.Bloods.Item.BloodReportListAPI;
import com.example.easyhelp.Bloods.Item.BloodRequestAPI;
import com.example.easyhelp.ChangePassword.Items.ChangePasswordItems;
import com.example.easyhelp.Construction.Item.ConstructionItems;
import com.example.easyhelp.RegistrationAndLogin.Login.Item.LoginAPIElements;
import com.example.easyhelp.Profile.Item.ProfileItemAPI;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.CountryDivisionDistrictThana;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.RegistrationItems;
import com.example.easyhelp.WelcomeSpeech.WelcomeSpeechAPIElements;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PlaceHolderAPI
{


   @GET("institute.php")
    Call<WelcomeSpeechAPIElements> getWelcomeSpeech();

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginAPIElements> getLoginInfo(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("category") String category
    );

    @FormUrlEncoded
    @POST("construction.php")
    Call<ConstructionItems> getConstructionInfo(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("password.php")
    Call<ChangePasswordItems> getChangePasswordInfo(
            @Field("user_name") String username,
            @Field("password") String oldPassword,
            @Field("old_password") String confirmOldPassword,
            @Field("new_password") String newPassword,
            @Field("again_password") String confirmNewPassword

    );

    @FormUrlEncoded
    @POST("myprofile.php")
    Call<ProfileItemAPI> getProfileDetails(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("join.php")
    Call<RegistrationItems> getRegistration(
            @Field("mobile") String mobile,
            @Field("category") String category,
            @Field("referral_id") String referral_id,
            @Field("first_password") String first_password,
            @Field("re_password") String re_password,
            @Field("name") String name,
            @Field("email") String email,
            @Field("nid") String nid,
            @Field("country") String country,
            @Field("division") String division,
            @Field("district") String district,
            @Field("ps") String ps,
            @Field("blood") String blood,
            @Field("address") String address
    );


    /*Blood Report List*/
    @FormUrlEncoded
    @POST("bloods.php")
    Call<BloodReportListAPI> getBloodList(
            @Field("user_id") int user_id
    );

    /*Blood Request API*/
    @FormUrlEncoded
    @POST("blood.php")
    Call<BloodRequestAPI> getRequestBlood(
            @Field("user_name") String user_name,
            @Field("blood") String blood
    );

    /*User Number Check on BeforeRegistrationUserCheck*/
    @FormUrlEncoded
    @POST("check.php")
    Call<BeforeRegistrationUserCheckAPI> getUserCheck(
            @Field("user_check") String user_check
    );

    /*Country list api*/
    @FormUrlEncoded
    @POST("rbp_list.php")
    Call<CountryDivisionDistrictThana> getCountryList(
            @Field("country_check") String country_list
    );

    /*Country=>Division list api*/
    @FormUrlEncoded
    @POST("rbp_list.php")
    Call<CountryDivisionDistrictThana> getDivisionList(
            @Field("division_check") String division_check,
            @Field("Country_ID") String Country_ID
    );

    /*Country=>Division=>District list api*/
    @FormUrlEncoded
    @POST("rbp_list.php")
    Call<CountryDivisionDistrictThana> getDistrict(
            @Field("district_check") String district_check,
            @Field("Country_ID") String Country_ID,
            @Field("Division_ID") String DivisionID
    );

    /*Country=>Division=>District=>Thana list api*/
    @FormUrlEncoded
    @POST("rbp_list.php")
    Call<CountryDivisionDistrictThana> getThana(
            @Field("thana_check") String thana_check,
            @Field("Country_ID") String Country_ID,
            @Field("Division_ID") String DivisionID,
            @Field("District_ID") String District_ID
    );

    /*Forget Password API*/
 @FormUrlEncoded
 @POST("forget_password_change.php")
 Call<ForgetPasswordItems> getForgetPassword(
           @Field("user_name") String user_name,
           @Field("category") String category,
           @Field("new_password") String new_password,
           @Field("again_password") String again_password
 );

}


