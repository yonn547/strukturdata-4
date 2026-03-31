import java.util.Scanner;

class Mahasiswa {
    String nim;
    String nama;

    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }
    
    @Override
    public String toString() {
        return nim + " - " + nama;
    }
}

public class ArrayMahasiswa {
    static Mahasiswa[] mhsArray = new Mahasiswa[10]; // Kapasitas 10
    static int count = 0; // Jumlah data saat ini
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== MENU ARRAY MAHASISWA (Count: " + count + "/10) ===");
            System.out.println("1. Insert at beginning");
            System.out.println("2. Insert at given position");
            System.out.println("3. Insert at end");
            System.out.println("4. Delete from beginning");
            System.out.println("5. Delete given position");
            System.out.println("6. Delete from end");
            System.out.println("7. Delete first occurrence (by NIM)");
            System.out.println("8. Show data");
            System.out.println("9. Exit");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline

            switch (choice) {
                case 1: insertAtBeginning(); break;
                case 2: insertAtPosition(); break;
                case 3: insertAtEnd(); break;
                case 4: deleteFromBeginning(); break;
                case 5: deleteAtPosition(); break;
                case 6: deleteFromEnd(); break;
                case 7: deleteFirstOccurrence(); break;
                case 8: showData(); break;
                case 9: System.out.println("Keluar program..."); break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (choice != 9);
    }

    // Input Helper
    static Mahasiswa inputData() {
        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        return new Mahasiswa(nim, nama);
    }

    // 1. Insert at Beginning
    static void insertAtBeginning() {
        if (count >= mhsArray.length) {
            System.out.println("Array Penuh!"); return;
        }
        // Geser semua elemen ke kanan dari index terakhir ke 0
        for (int i = count; i > 0; i--) {
            mhsArray[i] = mhsArray[i - 1];
        }
        mhsArray[0] = inputData();
        count++;
        System.out.println("Berhasil insert di awal.");
    }

    // 2. Insert at Given Position
    static void insertAtPosition() {
        if (count >= mhsArray.length) {
            System.out.println("Array Penuh!"); return;
        }
        System.out.print("Masukkan posisi (1 - " + (count + 1) + "): ");
        int pos = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (pos < 1 || pos > count + 1) {
            System.out.println("Posisi tidak valid!"); return;
        }

        int index = pos - 1; // Konversi ke index array (0-based)
        // Geser elemen ke kanan mulai dari index
        for (int i = count; i > index; i--) {
            mhsArray[i] = mhsArray[i - 1];
        }
        mhsArray[index] = inputData();
        count++;
        System.out.println("Berhasil insert di posisi " + pos);
    }

    // 3. Insert at End
    static void insertAtEnd() {
        if (count >= mhsArray.length) {
            System.out.println("Array Penuh!"); return;
        }
        mhsArray[count] = inputData();
        count++;
        System.out.println("Berhasil insert di akhir.");
    }

    // 4. Delete from Beginning
    static void deleteFromBeginning() {
        if (count <= 0) {
            System.out.println("Array Kosong!"); return;
        }
        // Geser semua elemen ke kiri
        for (int i = 0; i < count - 1; i++) {
            mhsArray[i] = mhsArray[i + 1];
        }
        count--;
        mhsArray[count] = null; // Hapus referensi terakhir (opsional tapi bersih)
        System.out.println("Data awal dihapus.");
    }

    // 5. Delete Given Position
    static void deleteAtPosition() {
        if (count <= 0) {
            System.out.println("Array Kosong!"); return;
        }
        System.out.print("Masukkan posisi hapus (1 - " + count + "): ");
        int pos = scanner.nextInt();
        scanner.nextLine();

        if (pos < 1 || pos > count) {
            System.out.println("Posisi tidak valid!"); return;
        }

        int index = pos - 1;
        // Geser elemen ke kiri menimpa index yang dihapus
        for (int i = index; i < count - 1; i++) {
            mhsArray[i] = mhsArray[i + 1];
        }
        count--;
        mhsArray[count] = null;
        System.out.println("Data posisi " + pos + " dihapus.");
    }

    // 6. Delete From End
    static void deleteFromEnd() {
        if (count <= 0) {
            System.out.println("Array Kosong!"); return;
        }
        count--;
        mhsArray[count] = null;
        System.out.println("Data terakhir dihapus.");
    }

    // 7. Delete First Occurrence (by NIM)
    static void deleteFirstOccurrence() {
        if (count <= 0) {
            System.out.println("Array Kosong!"); return;
        }
        System.out.print("Masukkan NIM yang akan dihapus: ");
        String targetNim = scanner.nextLine();

        int indexFound = -1;
        for (int i = 0; i < count; i++) {
            if (mhsArray[i].nim.equals(targetNim)) {
                indexFound = i;
                break;
            }
        }

        if (indexFound != -1) {
            // Lakukan penghapusan manual (geser kiri)
            for (int i = indexFound; i < count - 1; i++) {
                mhsArray[i] = mhsArray[i + 1];
            }
            count--;
            mhsArray[count] = null;
            System.out.println("Mahasiswa dengan NIM " + targetNim + " dihapus.");
        } else {
            System.out.println("Data tidak ditemukan.");
        }
    }

    // 8. Show Data
    static void showData() {
        System.out.println("--- Daftar Mahasiswa ---");
        if (count == 0) {
            System.out.println("(Kosong)");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println((i + 1) + ". " + mhsArray[i].toString());
            }
        }
    }
}