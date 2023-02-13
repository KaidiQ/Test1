package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView = this.findViewById(R.id.wbv_webView);
        //通过getSettings方法可以设置浏览器的属性
        //setJavaScriptEnabled让webView支持JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //保证一个网页跳转另一个网页时，仍在webView中打开
        webView.setWebViewClient(new WebViewClient());
        //展示指定的url网页
        webView.loadUrl("https://player.bilibili.com/player.html?aid=890296743");
    }
}
