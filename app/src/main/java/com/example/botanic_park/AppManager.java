package com.example.botanic_park;

import com.example.botanic_park.Information.*;
import com.example.botanic_park.PaymentAndQR.PaymentPopUpActivity;
import com.example.botanic_park.PaymentAndQR.scan_QR;
import com.example.botanic_park.PlantSearch.PlantBookItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AppManager {
    private static AppManager instance = null;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }


    private ArrayList<PlantBookItem> list = null;
    private ArrayList<PlantBookItem> plantsToday = null;
    public int collectionCount = 0;

    public ArrayList<PlantBookItem> getList() {
        return list;
    }

    public void setList(ArrayList<PlantBookItem> list) {
        this.list = list;
    }

    public ArrayList<PlantBookItem> getPlantsToday() {
        return plantsToday;
    }

    public void setPlantsToday(ArrayList<PlantBookItem> plantsToday) {
        this.plantsToday = plantsToday;
    }

    private FloatingActionButton floatingActionButton;
    public void setMenuFloatingActionButton (FloatingActionButton floatingActionButton) {this.floatingActionButton = floatingActionButton;}
    public FloatingActionButton getMenuFloatingActionButton() { return floatingActionButton; }

    private MainActivity mainActivity;
    public void setMainActivity(MainActivity mainActivity) {this.mainActivity = mainActivity; }
    public MainActivity getMainActivity(){ return  mainActivity; }

    private PaymentPopUpActivity paymentPopUpActivity;
    public void setPaymentPopUpActivity (PaymentPopUpActivity paymentPopUpActivity) {this.paymentPopUpActivity = paymentPopUpActivity; }
    public PaymentPopUpActivity getPaymentPopUpActivity() { return  paymentPopUpActivity; }

    private scan_QR  scan_qr;
    public void setScan_qr(scan_QR  scan_qr) {this.scan_qr = scan_qr; }
    public scan_QR getScan_qr(){ return  scan_qr; }

    private InconvenienceActivity inconvenienceActivity;
    public void setInconvenienceActivity(InconvenienceActivity  inconvenienceActivity) {this.inconvenienceActivity = inconvenienceActivity; }
    public InconvenienceActivity getInconvenienceActivity(){ return  inconvenienceActivity; }

    private InconvenienceDetailPostActivity inconvenienceDetailPostActivity;
    public void setInconvenienceDetailPostActivity(InconvenienceDetailPostActivity  scan_qr) {this.inconvenienceDetailPostActivity = inconvenienceDetailPostActivity; }
    public InconvenienceDetailPostActivity getInconvenienceDetailPostActivity(){ return  inconvenienceDetailPostActivity; }

    private RegistrationPostInInconvenienceActivity registrationPostInInconvenienceActivity;
    public void setRegistrationPostInInconvenienceActivity(RegistrationPostInInconvenienceActivity  registrationPostInInconvenienceActivity) {this.registrationPostInInconvenienceActivity = registrationPostInInconvenienceActivity; }
    public RegistrationPostInInconvenienceActivity getRegistrationPostInInconvenienceActivity(){ return  registrationPostInInconvenienceActivity; }

    private InformationUseActivity informationUseActivity;
    public void setInformationUseActivity(InformationUseActivity  informationUseActivity) {this.informationUseActivity = informationUseActivity; }
    public InformationUseActivity getInformationUseActivity(){ return  informationUseActivity; }

    private WayToComeActivity wayToComeActivity;
    public void setWayToComeActivity(WayToComeActivity  wayToComeActivity) {this.wayToComeActivity = wayToComeActivity; }
    public WayToComeActivity getWayToComeActivity(){ return  wayToComeActivity; }

    private com.example.botanic_park.Information.NewsActivity newsActivity;
    public void setNewsActivity(com.example.botanic_park.Information.NewsActivity  newsActivity) {this.newsActivity = newsActivity; }
    public com.example.botanic_park.Information.NewsActivity getNewsActivity(){ return  newsActivity; }

    private com.example.botanic_park.WebViewActivity webViewActivity;
    public void setWebViewActivity(com.example.botanic_park.WebViewActivity  webViewActivity) {this.webViewActivity = webViewActivity; }
    public com.example.botanic_park.WebViewActivity getWebViewActivity(){ return  webViewActivity; }
    }

