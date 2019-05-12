package com.example.saurabh_pc.medcare3;

public class Constants {

    public static final String ip_address= "saurabhfit.000webhostapp.com";
    private static final String ROOT_URL="http://"+ip_address+"/MedCare/v1/";
    public static final String URL_REGISTER = ROOT_URL+"registerUser.php";
    public static final String URL_LOGIN = ROOT_URL+"userLogin.php";
    public static final String URL_USER_DETAILS = ROOT_URL+"userDetails.php";
    public static final String URL_USER_UPDATE_INFO = ROOT_URL+"updateInfo.php";
    public static final String URL_FILTER_DOCTOR_LiST = ROOT_URL+"filterDoctorsList.php";
    public static final String URL_REVIEW_LIST = ROOT_URL+"review.php";
    public static final String URL_SHOW_REVIEW = ROOT_URL+"showReview.php";
}