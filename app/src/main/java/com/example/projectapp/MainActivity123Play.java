package com.example.projectapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import java.util.Random;


public class MainActivity123Play extends AppCompatActivity {

    private TextView textViewNumbers;
    private GridLayout gridLayoutCards;
    private List<Integer> displayedNumbers;
    private int currentNumberIndex = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity123_play);

        textViewNumbers = findViewById(R.id.textViewNumbers);
        gridLayoutCards = findViewById(R.id.gridLayoutCards);

        // Sırasıyla gösterilecek sayıları oluştur
        createDisplayedNumbers();

        // Kartları oluştur
        createCards();
    }

    private void createDisplayedNumbers() {
        // Rastgele sayılar oluştur
        displayedNumbers = generateRandomNumbers(3); // 3 rastgele sayı oluştur
        // İlk sayıyı göster
        showNextNumber();
    }
    private List<Integer> generateRandomNumbers(int count) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt(10)); // 0-9 arasında rastgele bir sayı ekle
        }
        return numbers;
    }


    private void showNextNumber() {
        if (currentNumberIndex < displayedNumbers.size()) {
            // Ekranda bir sonraki sayıyı göster
            textViewNumbers.setText(String.valueOf(displayedNumbers.get(currentNumberIndex)));
            currentNumberIndex++;
            // 3 saniye sonra bir sonraki sayıyı göster
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNextNumber();
                }
            }, 2300); // 3000 milisaniye = 3 saniye
        } else {
            // Tüm sayılar gösterildi, kart seçimine geç
            enableCardSelection();
            currentNumberIndex = 0;
        }
    }

    private void enableCardSelection() {
        // Kart seçimini etkinleştir
        for (int i = 0; i < gridLayoutCards.getChildCount(); i++) {
            View child = gridLayoutCards.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setEnabled(true); // Kartları tıklanabilir yap
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkUserSelection((Button) v);
                        if (currentNumberIndex == displayedNumbers.size()) {
                            createDisplayedNumbers();
                            showNextNumber();
                        }
                    }
                });
            }
        }
    }

    private void checkUserSelection(Button selectedButton) {
        int expectedNumber = displayedNumbers.get(currentNumberIndex - 1);
        int selectedNumber = Integer.parseInt(selectedButton.getText().toString());
        if (selectedNumber == expectedNumber) {
            // Kullanıcının seçimi doğruysa
            selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
            // Doğru butonu devre dışı bırak
            selectedButton.setEnabled(false);
            // Bir sonraki sayıyı göster
            showNextNumber();
        } else {
            // Kullanıcının seçimi yanlış
            // Uygun geribildirim sağla veya oyunu sıfırla
            selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }


    private void createCards() {
        for (int i = 0; i < gridLayoutCards.getChildCount(); i++) {
            View child = gridLayoutCards.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                // Her karta bir rakam ata
                button.setText(String.valueOf(i+1));
                // Kartların tıklanabilir olmasını sağla
                button.setEnabled(false);
            }
        }
    }
}



