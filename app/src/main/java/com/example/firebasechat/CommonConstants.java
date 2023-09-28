package com.example.firebasechat;


import android.Manifest;

import com.google.firebase.appcheck.interop.BuildConfig;


public interface CommonConstants {

    String folderName = "/HBSFastMoney";

    String SHARED_PREF = "MyPreferences";

    String OTP = "OTP";
    String[] CAM_STORAGE_PERM = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};


    //Git
    /*String STAGING = "http://192.168.100.52/HBS/";
    String HOST_MAT = "http://192.168.100.52/HBS/Matrimonyapi/";
    String HOST_LOAN = "http://192.168.100.52/HBS/loanappapi/";
    String HOST_FAMILY = "http://192.168.100.52/HBS/familyapi/";
    String HOST_CLUB = "http://192.168.100.52/HBS/clubapi/";*/


    //test
 /*   String STAGING = "https://akprojects.co/hbschit.com/staging/";
    String HOST_MAT = "https://akprojects.co/hbschit.com/staging/Matrimonyapi/";
    String HOST_LOAN = "https://akprojects.co/hbschit.com/staging/loanappapi/";
    String HOST_FAMILY = "https://akprojects.co/hbschit.com/staging/familyapi/";
    String HOST_CLUB = "https://akprojects.co/hbschit.com/staging/clubapi/";*/


    //Live


    String HOST_MAT = "https://hbschit.com/matrimonyapi/";
    String HOST_FAMILY = "https://hbschit.com/familyapi/";
    String HOST_CLUB = "https://hbschit.com/clubapi/";
    String HOST_LOAN = "https://hbschit.com/loanappapi/";
    String STAGING = "https://hbschit.com/";

    // String HOST_MAT = "https://akprojects.co/hbschit.com/staging/Matrimonyapi/"; //Test
    // String HOST_MAT = "https://hbschit.com/matrimonyapi/"; //Live


    //String HOST_LOAN = "https://akprojects.co/hbschit.com/staging/loanappapi/"; //Test


    // String HOST_FAMILY = "https://akprojects.co/hbschit.com/staging/familyapi/"; //Test
    //  String HOST_FAMILY = "https://hbschit.com/familyapi/"; //Live

    //   String HOST_CLUB = "http://192.168.100.52/HBS/clubapi/"; //Git

    //String HOST_CLUB = "https://akprojects.co/hbschit.com/staging/clubapi/"; //Test
    //String HOST_CLUB = "https://hbschit.com/clubapi/"; //Live

    //  String WEBVIEW_HOST = "http://192.168.100.52/HBS/filter/filterpage?";  //Git

    String WEBVIEW_HOST = "https://akprojects.co/hbschit.com/staging/filter/filterpage?";  //TEST
    // String WEBVIEW_HOST = "https://hbschit.com/filter/filterpage?"; //LIVE

    String FIRE_BASE_URL = "https://hbs-chit-fund-8241c.firebaseio.com/";//akproject

    //String FIRE_BASE_URL = "https://hbslife-e3740-default-rtdb.firebaseio.com/";  //developers


    //  String STAGING = "https://hbschit.com/" ;
    // String STAGING = "https://akprojects.co/hbschit.com/staging/";
    //  String STAGING = "http://192.168.100.52/HBS/"; //git
    // String MAIN_URL = STAGING + "api/";
    String MAIN_URL = STAGING + "api/";
    String PROFILE_UPDATE_URL = "profile/update/step/";

    String statesJson = "{\n" +
            "    \"AN\":\"Andaman and Nicobar Islands\",\n" +
            "    \"AP\":\"Andhra Pradesh\",\n" +
            "    \"AR\":\"Arunachal Pradesh\",\n" +
            "    \"AS\":\"Assam\",\n" +
            "    \"BR\":\"Bihar\",\n" +
            "    \"CG\":\"Chandigarh\",\n" +
            "    \"CH\":\"Chhattisgarh\",\n" +
            "    \"DN\":\"Dadra and Nagar Haveli\",\n" +
            "    \"DD\":\"Daman and Diu\",\n" +
            "    \"DL\":\"Delhi\",\n" +
            "    \"GA\":\"Goa\",\n" +
            "    \"GJ\":\"Gujarat\",\n" +
            "    \"HR\":\"Haryana\",\n" +
            "    \"HP\":\"Himachal Pradesh\",\n" +
            "    \"JK\":\"Jammu and Kashmir\",\n" +
            "    \"JH\":\"Jharkhand\",\n" +
            "    \"KA\":\"Karnataka\",\n" +
            "    \"KL\":\"Kerala\",\n" +
            "    \"LA\":\"Ladakh\",\n" +
            "    \"LD\":\"Lakshadweep\",\n" +
            "    \"MP\":\"Madhya Pradesh\",\n" +
            "    \"MH\":\"Maharashtra\",\n" +
            "    \"MN\":\"Manipur\",\n" +
            "    \"ML\":\"Meghalaya\",\n" +
            "    \"MZ\":\"Mizoram\",\n" +
            "    \"NL\":\"Nagaland\",\n" +
            "    \"OR\":\"Odisha\",\n" +
            "    \"PY\":\"Puducherry\",\n" +
            "    \"PB\":\"Punjab\",\n" +
            "    \"RJ\":\"Rajasthan\",\n" +
            "    \"SK\":\"Sikkim\",\n" +
            "    \"TN\":\"Tamil Nadu\",\n" +
            "    \"TS\":\"Telangana\",\n" +
            "    \"TR\":\"Tripura\",\n" +
            "    \"UP\":\"Uttar Pradesh\",\n" +
            "    \"UK\":\"Uttarakhand\",\n" +
            "    \"WB\":\"West Bengal\"\n" +
            "}";
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    String paymentMethods = "Payment Methos<br/>&#8226; HDFC Account Number 50200033580258 and IFSC HDFC0001860<br/>\n" +

            "&#8226; UPI Payment hbschit@hdfcbank<br/>";

}
