public class Menu {
    private String nama;
    private int harga;
    private int stock;

    Menu(String nama, int harga, int stock){
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
    }

    Menu(String nama, int harga){
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
