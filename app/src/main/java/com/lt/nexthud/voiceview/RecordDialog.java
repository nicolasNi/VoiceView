package com.lt.nexthud.voiceview;

/**
 * Created by nikaihua on 17/2/7.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.lt.nexthud.voiceview.RecodeManager.SoundAmplitudeListen;

import java.io.IOException;

public class RecordDialog {
    private Context context;
    private ImageView progress;	//显示录音的振幅
    private ImageView mic_icon;//显示录音状态图标
    private TextView dialog_title;//显示录音振幅

    private Drawable[]progressImg=new Drawable[7];//显示录音振幅图片缓存
    private View dialog_view;//录音对话视图
    private AlertDialog dialog;//录音对话框
    RecodeManager recodeManager=new RecodeManager();


    public RecordDialog(Context context){
        this.context=context;
        dialog_view=LayoutInflater.from(context).inflate(R.layout.dialog_sound, null);

        //初始化振幅图片
        progressImg[0]=context.getResources().getDrawable(R.mipmap.mic_1);
        progressImg[1]=context.getResources().getDrawable(R.mipmap.mic_2);
        progressImg[2]=context.getResources().getDrawable(R.mipmap.mic_3);
        progressImg[3]=context.getResources().getDrawable(R.mipmap.mic_4);
        progressImg[4]=context.getResources().getDrawable(R.mipmap.mic_5);
        progressImg[5]=context.getResources().getDrawable(R.mipmap.mic_6);
        progressImg[6]=context.getResources().getDrawable(R.mipmap.mic_7);
//        dialog = new AlertDialog.Builder(context,R.style.Translucent_NoTitle).create();
        dialog=new AlertDialog.Builder(context).create();
        dialog.setView(dialog_view);

        dialog.show();

        progress=(ImageView) dialog_view.findViewById(R.id.sound_progress);
        mic_icon=(ImageView) dialog.findViewById(R.id.mic);
        dialog_title=(TextView) dialog.findViewById(R.id.title);
        dialog_title.setVisibility(View.GONE);
        try {
            recodeManager.setSoundAmplitudeListen(onSoundAmplitudeListen);
            recodeManager.startRecordCreateFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private SoundAmplitudeListen onSoundAmplitudeListen=new SoundAmplitudeListen() {

        @SuppressWarnings("deprecation")
        @Override
        public void amplitude(int amplitude, int db, int value) {
            // TODO Auto-generated method stub
            if(value>=6){
                value=6;
            }
            progress.setBackgroundDrawable(progressImg[value]);
        }
    };


    private OnClickListener onCancel=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dialog.cancel();
            dialog.dismiss();
        }
    };

    public void displayDialog()
    {
        dialog.show();
    }

    public void closeDialog() {
        // TODO Auto-generated method stub
        dialog.cancel();
        dialog.dismiss();
    }
}