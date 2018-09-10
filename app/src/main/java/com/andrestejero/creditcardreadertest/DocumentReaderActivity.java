package com.andrestejero.creditcardreadertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DocumentReaderActivity extends AppCompatActivity {

    private EditText reader;
    private TextView number;
    private TextView name;
    private TextView date;
    private TextView gender;
    private ProgressBar progressBar;
    private boolean first = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_reader);

        reader = (EditText) findViewById(R.id.reader);
        number = (TextView) findViewById(R.id.number);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        gender = (TextView) findViewById(R.id.gender);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button button = (Button) findViewById(R.id.buttonClean);

        reader.setInputType(InputType.TYPE_NULL);
        progressBar.setVisibility(View.GONE);

        reader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (first) {
                    startListener();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanForm();
            }
        });
    }

    private void startListener() {
        first = false;
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                parseDocument(reader.getText());
            }
        }, 1000);
    }

    private void cleanForm() {
        reader.setText(null);
        number.setText(null);
        name.setText(null);
        date.setText(null);
        gender.setText(null);
        first = true;
    }

    private void parseDocument(@NonNull CharSequence charSequence) {
        progressBar.setVisibility(View.GONE);
        // FIXME: 10/9/18 cambiar "\"" por "@"
        if (charSequence.toString().contains("@")) {
            String split[] = charSequence.toString().split("@");
            if (split.length == 17) {
                number.setText(split[1]);
                name.setText(getString(R.string.document_name, split[5], split[4]));
                date.setText(getString(R.string.document_date, split[7]));
                if ("M".equalsIgnoreCase(split[8])) {
                    gender.setText(getString(R.string.document_gender, "Masculino"));
                } else if ("F".equalsIgnoreCase(split[8])) {
                    gender.setText(getString(R.string.document_gender, "Femenino"));
                }
            } else if (split.length == 8 || split.length == 9) {
                number.setText(split[4]);
                name.setText(getString(R.string.document_name, split[2], split[1]));
                date.setText(getString(R.string.document_date, split[6]));
                if ("M".equalsIgnoreCase(split[3])) {
                    gender.setText(getString(R.string.document_gender, "Masculino"));
                } else if ("F".equalsIgnoreCase(split[3])) {
                    gender.setText(getString(R.string.document_gender, "Femenino"));
                }
            }
        }
    }

    /*
    "28585572
    "A
    "1
    "TEJERO
    "ANDRES
    "ARGENTINA
    "16-03-1981
    "M
    "13-10-2011
    "00076115549
    "8000
    "13-10-2026
    "273
    "0
    "ILRÑ2.20 CÑ110927.01 )GM-EXE'MOVE'HM=
    "UNIDAD ·13 ÇÇ S-NÑ 0040:2008::0010

    00131165955
    "SPROVIERO
    "FERNANDO LUIS
    "M
    "30860661
    "A
    "13-03-1984
    "22-08-2012

    00457740518
    "DE TITTO
    "JULIAN ELOY
    "M
    "36697536
    "B
    "22-01-1992
    "23-09-2016
    "206

    00396207270"MORRONE SAVOY"PABLO NICOLAS"M"35323621"B"25-07-1990"01-09-2015"201
    "33200877    "A"1"BATTELLI CELANO"NICOLAS ANDRES"ARGENTINA"13-08-1987"M"29-01-2011"00036285710"3314 "29-01-2026"139"0"ILRÑ01.2 CÑ100817.01"UNIDAD ·02 ÇÇ S-NÑ 0040:2008::0006

     */
}
