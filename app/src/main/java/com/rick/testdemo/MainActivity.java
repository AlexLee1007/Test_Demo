package com.rick.testdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rick.testdemo.base.BaseView;
import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.test.TestContract;
import com.rick.testdemo.test.TestModel;
import com.rick.testdemo.test.TestPresenter;

public class MainActivity extends BaseView<TestPresenter, TestContract.View> {

    private Button main_btn;
    private TextView main_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }


    private void initView() {
        main_btn = this.findViewById(R.id.main_btn);
        main_tv = this.findViewById(R.id.main_tv);
    }

    private void initListener() {
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取游戏 默认请求第一页
                mPresenter.getContract().requestMsg("1");
            }
        });
    }


    @Override
    public TestContract.View getContract() {
        return new TestContract.View() {
            @Override
            public void handlerMsgResult(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                main_tv.setText(str);
            }
        };
    }

    @Override
    public TestPresenter getPresenter() {
        return new TestPresenter();
    }

}
