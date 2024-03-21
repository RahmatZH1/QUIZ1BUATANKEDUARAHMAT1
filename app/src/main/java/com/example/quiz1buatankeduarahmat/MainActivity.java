package com.example.quiz1buatankeduarahmat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    public static final String[] languanges = {"Select Languanges", "English", "Indonesia", "Spanyol"};

    EditText Nama, Kode, Jumlah;
    Button proses;
    RadioGroup radioGroup;
    RadioButton rbGOLD, rbSILVER, rbNORMAL;
    private Object Activity;
    private Object activity;
    private String langCode;
    private Object activty;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nama = findViewById(R.id.editTextNama);
        Kode = findViewById(R.id.editTextKodeBarang);
        Jumlah = findViewById(R.id.editTextJumlahBarang);
        proses = findViewById(R.id.TombolProses);
        rbGOLD = findViewById(R.id.rbGOLD);
        rbSILVER = findViewById(R.id.rbSILVER);
        rbNORMAL = findViewById(R.id.rbNORMAL);
        radioGroup = findViewById(R.id.radioGroup);

        proses.setOnClickListener(v -> {
            String namaPembeli = Nama.getText().toString().trim();
            String kodeBarang = Kode.getText().toString().trim();
            int jumlahBarang = Integer.parseInt(Jumlah.getText().toString().trim());

            if (kodeBarang.isEmpty()) {
                Toast.makeText(MainActivity.this, "Harap masukkan kode barang!", Toast.LENGTH_SHORT).show();
                return;
            }

            double hargaBarang = getHargaBarang(kodeBarang);
            if (hargaBarang == -1) {
                Toast.makeText(MainActivity.this, "Kode barang tidak valid!", Toast.LENGTH_SHORT).show();
                return;
            }

            String merekBarang = getMerekBarang(kodeBarang);
            if (merekBarang == null) {
                Toast.makeText(MainActivity.this, "Kode barang tidak valid!", Toast.LENGTH_SHORT).show();
                return;
            }
            double totalHarga = hargaBarang * jumlahBarang;
            double diskon = hitungDiskon(totalHarga);
            double totalBayar = totalHarga - diskon - hitungPotonganHarga(totalHarga);
            double potonganHarga = hitungPotonganHarga(totalHarga);
            tampilkanBon(namaPembeli, kodeBarang, merekBarang, hargaBarang, jumlahBarang, totalHarga, potonganHarga, diskon, totalBayar);
        });
    }

    private double getHargaBarang(String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "SGS":
                return 12999999;
            case "AA5":
                return 9999999;
            case "MP3":
                return 28999999;
            default:
                return -1;
        }
    }

    private String getMerekBarang(String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "SGS":
                return "Samsung Galaxy S";
            case "AA5":
                return "Acer Aspire 5";
            case "MP3":
                return "MACBOOK PRO M3";
            default:
                return null;
        }
    }

    private double hitungPotonganHarga(double totalHarga) {
        double potonganHarga = 0;
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.rbGOLD) {
            potonganHarga = totalHarga * 0.1;
        } else if (selectedRadioButtonId == R.id.rbSILVER) {
            potonganHarga = totalHarga * 0.05;
        } else if (selectedRadioButtonId == R.id.rbNORMAL) {
            potonganHarga = totalHarga * 0.02;
        }
        return potonganHarga;
    }

    private double hitungDiskon(double totalHarga) {
        if (totalHarga > 10000000) {
            return 100000;
        }
        return 0;
    }

    private void tampilkanBon(String namaPembeli, String kodeBarang, String merekBarang, double hargaBarang, int jumlahBarang, double totalHarga, double potonganHarga, double diskon, double totalBayar) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String hargabarangsemua = "Rp " + decimalFormat.format(hargaBarang);
        String hargaJumlahsemua = "Rp " + decimalFormat.format(jumlahBarang);
        String hargatotalsemua = "Rp " + decimalFormat.format(totalHarga);
        String hargabayarsemua = "Rp " + decimalFormat.format(totalBayar);
        String hargaPotongansemua = "Rp " + decimalFormat.format(potonganHarga);
        String Diskontotal = "Rp " + decimalFormat.format(diskon);

        // Buat pesan bon belanja dalam format teks
        String bonMessage = "Transaksi Hari Ini : " + "\n\n"
                + "Nama Pembeli: " + namaPembeli + "\n\n"
                + "Kode Barang: " + kodeBarang + "\n\n"
                + "Merek Barang: " + merekBarang + "\n\n"
                + "Harga Barang Satuan: " + hargabarangsemua + "\n\n"
                + "Jumlah Barang Dibeli: " + hargaJumlahsemua + "\n\n"
                + "Total Harga: " + hargatotalsemua + "\n\n"
                + "Diskon MemberShip : " + hargaPotongansemua + "\n\n"
                + "Diskon Harga : " + Diskontotal + "\n\n"
                + "Total Pembayaran: " + hargabayarsemua;

        Intent intent = new Intent(MainActivity.this, Teks_activity.class);
        // Sertakan data bon belanja ke dalam Intent
        intent.putExtra("bonMessage", bonMessage);
        // Mulai activity BonBelanjaActivity
        startActivity(intent);
    }

}
