package com.example.student;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText e_xm, e_bj, e_xh, e_id;
    TextView t_1;
    Button b_add, b_allsee, b_clearsee, b_alldel, b_delid, b_seeid, b_updid, feibiao;
    DBAdapter dbAdapter;
    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e_xm = findViewById(R.id.e_xm);
        e_bj = findViewById(R.id.e_bj);
        e_xh = findViewById(R.id.e_xh);
        b_add = findViewById(R.id.b_add);
        b_allsee = findViewById(R.id.b_allsee);
        b_clearsee = findViewById(R.id.b_clearall);
        b_alldel = findViewById(R.id.b_delall);
        b_delid = findViewById(R.id.b_delid);
        b_seeid = findViewById(R.id.b_seeid);
        b_updid = findViewById(R.id.b_updid);
        e_id = findViewById(R.id.e_id);
        t_1 = findViewById(R.id.t_1);
        spinner1 = findViewById(R.id.spinner_xy);
        spinner2 = findViewById(R.id.spinner_zz);
        feibiao = findViewById(R.id.btn_feibiao);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        feibiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        //数据添加
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                People t = new People();
                t.Name = e_xm.getText().toString();
                t.Banji = e_bj.getText().toString();
                t.Xuehao = e_xh.getText().toString();
                t.xueyuan = spinner1.getSelectedItem().toString();
                t.zhengzhi = spinner2.getSelectedItem().toString();
                long colunm = dbAdapter.insert(t);
                if (colunm == -1) {
                    t_1.setText("添加过程错误！");
                } else {
                    t_1.setText("成功添加数据，ID：" + String.valueOf(colunm));
                }
            }
        });
        //全部显示
        b_allsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                People[] peoples = dbAdapter.queryAllData();
                if (peoples == null) {
                    t_1.setText("数据库中没有数据");
                    return;
                }
                String t = "数据库:\n";
                for (int i = 0; i < peoples.length; ++i) {
                    t += peoples[i].toString() + "\n";
                }
                t_1.setText(t);
            }
        });
        //清除显示
        b_clearsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_1.setText("");
            }
        });
        //全部清除
        b_alldel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAdapter.deleteAllData();
                t_1.setText("已删除所有数据！");
            }
        });
        //ID删除
        b_delid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(e_id.getText().toString());
                long result = dbAdapter.deleteOneData(id);
                String msg = "删除ID为" + e_id.getText().toString() + "的数据" + (result > 0 ? "成功" : "失败");
                t_1.setText(msg);
            }
        });
        //ID查询
        b_seeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(e_id.getText().toString());
                People people[] = dbAdapter.queryOneData(id);
                if (people == null) {
                    t_1.setText("Id为" + id + "的记录不存在！");
                } else {
                    t_1.setText("查询成功：\n" + people[0].toString());
                }
            }
        });
        //ID更新
        b_updid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(e_id.getText().toString());
                People t = new People();
                t.Name = e_xm.getText().toString();
                t.Banji = e_bj.getText().toString();
                t.Xuehao = e_xh.getText().toString();
                t.xueyuan = spinner1.getSelectedItem().toString();
                t.zhengzhi = spinner2.getSelectedItem().toString();
                long n = dbAdapter.updateOneData(id, t);
                if (n < 0) {
                    t_1.setText("更新过程错误！");
                } else {
                    t_1.setText("成功更新数据，" + String.valueOf(n) + "条");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbAdapter.close();
    }
}

