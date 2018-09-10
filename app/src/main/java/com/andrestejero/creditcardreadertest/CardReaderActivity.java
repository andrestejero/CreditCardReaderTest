package com.andrestejero.creditcardreadertest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CardReaderActivity extends AppCompatActivity {

    private EditText reader;
    private TextView number;
    private TextView name;
    private TextView date;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);

        reader = (EditText) findViewById(R.id.reader);
        number = (TextView) findViewById(R.id.number);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        Button button = (Button) findViewById(R.id.buttonClean);

        reader.setInputType(InputType.TYPE_NULL);

        reader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // FIXME: 3/9/18 cambiar "_" por "?"
                if (charSequence.length() > 0 && charSequence.toString().charAt(charSequence.length() - 1) == '?') {
                    if (first) {
                        parseCreditCard(charSequence);
                        first = false;
                    } else {
                        reader.setText(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanForm();
            }
        });
    }

    private void cleanForm() {
        reader.setText(null);
        number.setText(null);
        name.setText(null);
        date.setText(null);
        first = true;
    }

    private void parseCreditCard(@NonNull CharSequence charSequence) {
        // FIXME: 3/9/18 cambiar "&" por "\\^"
        if (charSequence.toString().contains("\u005E")) {
            String split[] = charSequence.toString().split("\\^");
            if (split.length > 2) {
                number.setText(split[0].substring(2));
                name.setText(split[1].trim());
                String year = split[2].substring(0, 2);
                String month = split[2].substring(2, 4);
                date.setText(getString(R.string.date, month, year));
            }
        }
    }
}
