/* Nama-nama Bakso
Bakso kecil
Bakso Urat Kecil
Bakso Urat Besar
Baksi isi Telur
Bakso isi Cincang
Bakso isi Keju
Tahu Bakso
mi
 */

public class Bakso {
    private String nama;
    private int harga;
    private int stock;

    Bakso(String nama, int harga, int stock){
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void tambahStock(int jumlahStock){
        this.stock += jumlahStock;
    }

    public int getStock() {
        return stock;
    }
}
