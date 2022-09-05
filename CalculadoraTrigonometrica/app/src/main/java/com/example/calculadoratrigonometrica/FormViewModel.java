package com.example.calculadoratrigonometrica;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class FormViewModel extends ViewModel {

    private Drawable degreeImage;

    public Drawable getDegreeImage() {
        return degreeImage;
    }

    public void setDegreeImage(Drawable degreeImage) {
        this.degreeImage = degreeImage;
    }
}
