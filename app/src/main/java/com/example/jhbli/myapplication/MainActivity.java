package com.example.jhbli.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    System.out.println("TTS OK");
                    //tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    public void btTTSClick(View v){
        EditText et = (EditText)findViewById(R.id.editText);
        tts.setPitch(2);
        tts.setSpeechRate(1);
        tts.speak(et.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
    }

    public void onSttClick(View v){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);    //음성인식 앱 띄워라
        //파라미터 3개
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");   //한국어
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말을 하세요.");   //타이틀
        startActivityForResult(i, 2000);   //결과값을 받을 때. 2000 = 음성인식 결과
    }

    public void btCallClick(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1111-2222"));
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("ok" + requestCode + " " + resultCode);

        ArrayList<String> words = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        String word = words.get(0);

        if(word.contains("이미지")){
            Intent i = new Intent(Intent.ACTION_VIEW);
            String a = word.replace("이미지", "");

            try {
                a = URLEncoder.encode(a, "UTF-8");
            } catch (UnsupportedEncodingException e) {

            }
            Uri u = Uri.parse("http://search.naver.com/search.naver?where=image&query=" + a);
            i.setData(u);
            startActivity(i);
        }
        for(int i = 0; i < words.size(); i++){
            System.out.println("-->" + words.get(i));
        }
    }


    public void btWebClick(View v){
        Intent i = new Intent(Intent.ACTION_VIEW);
        String a = null;
        try {
            EditText et = (EditText)findViewById(R.id.editText);
            a = URLEncoder.encode(et.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        Uri u = Uri.parse("http://www.google.co.kr/maps/place/" + a);
        i.setData(u);
        startActivity(i);
    }
    public void btImageClick(View v){
        Intent i = new Intent(Intent.ACTION_VIEW);
        String a = null;
        try {
            EditText et = (EditText)findViewById(R.id.editText);
            a = URLEncoder.encode(et.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        Uri u = Uri.parse("http://search.naver.com/search.naver?where=image&query=" + a);
        i.setData(u);
        startActivity(i);
    }
}
