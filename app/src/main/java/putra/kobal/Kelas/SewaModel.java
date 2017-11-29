package putra.kobal.Kelas;

import com.google.android.gms.nearby.messages.Strategy;

/**
 * Created by Glory on 10/27/2017.
 */

public class SewaModel {
    String nama;
    String nope;
    String email;
    String alamatAsal;
    String alamatTujuan;
    String origin;
    String destination;
    String biaya;
    String jarak;
    String waktu;
    String jenis;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public void setAlamatAsal(String alamatAsal) {
        this.alamatAsal = alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public SewaModel(String nama, String nope, String email,
                     String alamatAsal, String alamatTujuan, String origin, String destination,String biaya
                    ,String jarak,String waktu,String jenis) {
        this.nama = nama;
        this.nope = nope;
        this.email = email;
        this.alamatAsal = alamatAsal;
        this.alamatTujuan = alamatTujuan;
        this.origin = origin;
        this.destination = destination;
        this.biaya = biaya;
        this.jarak = jarak;
        this.waktu = waktu;
        this.jenis = jenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNope() {
        return nope;
    }

    public void setNope(String nope) {
        this.nope = nope;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
