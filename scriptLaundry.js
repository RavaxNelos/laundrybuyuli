function hitung() {
    let nama = document.getElementById("nama").value;
    let berat = document.getElementById("berat").value;

    let harga = 5000;
    let total = berat * harga;

    document.getElementById("result").innerText =
        "Halo " + nama + ", total: Rp " + total;
}