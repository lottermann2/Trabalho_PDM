package com.example.trabalho_pdm.model;

import androidx.annotation.NonNull;

public class Rastreamento {
    private int registro_id;
    private String longitude;
    private String latitude;

    public Rastreamento(int registro_id, String longitude, String latitude) {
        this.registro_id = registro_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getRegistro_id() {
        return registro_id;
    }

    public void setRegistro_id(int registro_id) {
        this.registro_id = registro_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @NonNull
    @Override
    public String toString() {
        return (latitude + " " + longitude);
    }
}
