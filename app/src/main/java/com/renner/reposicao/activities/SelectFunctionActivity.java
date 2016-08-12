package com.renner.reposicao.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renner.reposicao.R;

/**
 * Created by Dico
 */
public class SelectFunctionActivity extends AppCompatActivity {

    private LinearLayout btnSelectVendas;
    private LinearLayout btnSelectEstoque;
    private ImageView imgSelectVendas;
    private ImageView imgSelectEstoque;
    private TextView txtSelectVendas;
    private TextView txtSelectEstoque;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_function);

        btnSelectVendas = (LinearLayout) findViewById(R.id.btnSelectVendas);
        btnSelectEstoque = (LinearLayout) findViewById(R.id.btnSelectEstoque);
        imgSelectVendas = (ImageView) findViewById(R.id.imgSelectVendas);
        imgSelectEstoque = (ImageView) findViewById(R.id.imgSelectEstoque);
        txtSelectVendas = (TextView) findViewById(R.id.txtSelectVendas);
        txtSelectEstoque = (TextView) findViewById(R.id.txtSelectEstoque);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btnSelectVendas.setOnClickListener(selectFunction);
        btnSelectEstoque.setOnClickListener(selectFunction);
    }

    View.OnClickListener selectFunction = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSelectVendas) {
                selectVendas();
            } else if (v.getId() == R.id.btnSelectEstoque) {
                selectEstoque();
            }
        }
    };

    private void selectVendas() {
        btnSelectVendas.setBackgroundResource(R.drawable.bg_select_function_button_on);
        imgSelectVendas.setImageResource(R.drawable.img_venda_on);
        txtSelectVendas.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.select_function_text_on));

        btnSelectEstoque.setBackgroundResource(R.drawable.bg_select_function_button_off);
        imgSelectEstoque.setImageResource(R.drawable.img_estoque_off);
        txtSelectEstoque.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.select_function_text_off));
    }

    private void selectEstoque() {
        btnSelectEstoque.setBackgroundResource(R.drawable.bg_select_function_button_on);
        imgSelectEstoque.setImageResource(R.drawable.img_estoque_on);
        txtSelectEstoque.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.select_function_text_on));

        btnSelectVendas.setBackgroundResource(R.drawable.bg_select_function_button_off);
        imgSelectVendas.setImageResource(R.drawable.img_venda_off);
        txtSelectVendas.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.select_function_text_off));
    }

}
