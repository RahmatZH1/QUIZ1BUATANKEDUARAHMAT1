package com.example.quiz1buatankeduarahmat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Teks_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teks);

        // Ambil pesan bon belanja dari Intent
        String bonMessage = getIntent().getStringExtra("bonMessage");

        // Tampilkan pesan bon belanja pada TextInputEditText
        TextView textViewDeskripsi = findViewById(R.id.editTextDeskripsi);
        textViewDeskripsi.setText(bonMessage);

        Button btnShare = findViewById(R.id.buttonShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bagikan pesan menggunakan intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, bonMessage);
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_button)));
            }
        });
    }
}
