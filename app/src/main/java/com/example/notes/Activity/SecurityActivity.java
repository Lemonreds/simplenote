package com.example.notes.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notes.Manager.SecurityManager;
import com.example.notes.View.MsgToast;
import com.example.ui.R;

/**
 * 密码
 */
public class SecurityActivity extends AppCompatActivity implements View.OnClickListener{


    public final static int MODEL_VERIFY = 0;  //验证密码模式
    public final static int MODEL_EDIT = 1; //设置密码模式

    private SecurityManager sManager;

    //视图显示
    private TextView title;
    private TextView info;
    private TextView passWord;

    private int passWordNumber;
    private StringBuilder inputPassWord;
    private StringBuilder oldInputPassWord;

    private int model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        sManager= new SecurityManager(this);
        initView();

        modelSetting();
        reset();
    }

    private void modelSetting(){

        model = getIntent().getIntExtra("model",0);

        if(model == MODEL_VERIFY){
            if(!sManager.isHavePassWord()){
                finish();
            }else{

                title.setText(getResources().getString(R.string.comfirm_t_sec));
            }
        }

        if(model == MODEL_EDIT){


            Toolbar mToolbar =(Toolbar)findViewById(R.id.toolbar);
            mToolbar.setNavigationIcon(R.drawable.pic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            });

            if(!sManager.isHavePassWord()){
                title.setText(getResources().getString(R.string.set_t_sec));
            }else{
                title.setText(getResources().getString(R.string.reset_t_sec));
            }
        }

    }

    private void initView(){
        title = (TextView) findViewById(R.id.title_toolbar);
        info =(TextView)findViewById(R.id.info_security);
        passWord = (TextView)findViewById(R.id.password_security);


        TextView cancel = (TextView)findViewById(R.id.btn_cancel);
         cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passWordNumber !=0) {
                    inputPassWord.deleteCharAt(inputPassWord.length() - 1);
                    passWordNumber--;
                    setPassWord();
                }

            }
        });
        TextView findPassWord =(TextView) findViewById(R.id.findPassword_security);
        findPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgToast.showToast(SecurityActivity.this,getResources().getString(R.string.forget_sec));
            }
        });

        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
    }

    /**
     * 显示视图
     */
    private void setPassWord(){

        int i;
        StringBuilder  hideChar = new StringBuilder(4);
        for(i=0;i<passWordNumber;i++){
            hideChar.append('●');
        }
        for(int j=i;j<4;j++){
            hideChar.append('○');
        }
        passWord.setText(hideChar.toString());
    }


    @Override
    public void onClick(View v) {

        if(passWordNumber != 4) {
            passWordNumber++;
            inputPassWord.append (   ((Button) v).getText().toString()   );
            setPassWord();
        }
        //验证
        if(passWordNumber == 4){
            verify();
        }
    }

    /**
     * 验证
     */
    private  void verify () {

        String input = inputPassWord.toString();

        if (sManager.isHavePassWord()) {//有密码

            if (model == MODEL_EDIT) {//修改模式

                if (sManager.isRightPassWord(input)) {
                    sManager.clearPassWord();
                    MsgToast.showToast(this, getResources().getString(R.string.clear_sec));
                    finish();
                } else {
                    MsgToast.showToast(this, getResources().getString(R.string.defeat_sec));
                    reset();
                }


            } else if (model == MODEL_VERIFY) {//验证模式

                if (sManager.isRightPassWord(input)) {
                    finish();
                }else{
                    MsgToast.showToast(this, getResources().getString(R.string.wrong_sec));
                    info.setText(getResources().getString(R.string.agian_sec));
                    reset();
                }
            }


        } else {//没有密码


            if(oldInputPassWord == null){
                oldInputPassWord = inputPassWord;
                info.setText(getResources().getString(R.string.comfirm_sec));
                reset();
            }
            else {


                if(oldInputPassWord.toString().equals(inputPassWord.toString())) {
                    sManager.setPassWord(input);
                    MsgToast.showToast(this, getResources().getString(R.string.done_sec));
                    finish();
                }else{
                    oldInputPassWord = null;
                    reset();
                    MsgToast.showToast(this, getResources().getString(R.string.diffrent_sec));
                    info.setText(getResources().getString(R.string.input_sec));
                }
            }


        }
    }



    private void reset(){
        //初始化
        inputPassWord = new StringBuilder();
        passWordNumber = 0;
        setPassWord();
    }

}
