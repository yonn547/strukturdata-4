import java.util.Scanner;

// --- STEP 1: MEMBUAT STRUKTUR NODE (Konsep Linked List Bercabang) ---
class Node {
    int id;
    String nama;
    Node left, right;

    public Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        this.left = this.right = null;
    }
}

// --- STEP 2: KELAS UTAMA UNTUK LOGIKA BST ---
public class PenjualanBST {
    Node root;

    // 1. Fitur Tambah Data (Insert)
    public void insert(int id, String nama) {
        root = insertRecursive(root, id, nama);
    }

    private Node insertRecursive(Node root, int id, String nama) {
        if (root == null) {
            return new Node(id, nama);
        }
        if (id < root.id) {
            root.left = insertRecursive(root.left, id, nama);
        } else if (id > root.id) {
            root.right = insertRecursive(root.right, id, nama);
        }
        return root;
    }

    // 2. Fitur Cari Data (Search)
    public Node search(int id) {
        return searchRecursive(root, id);
    }

    private Node searchRecursive(Node root, int id) {
        if (root == null || root.id == id) return root;
        if (id < root.id) return searchRecursive(root.left, id);
        return searchRecursive(root.right, id);
    }

    // 3. Fitur Hapus Data (Delete)
    public void delete(int id) {
        root = deleteRecursive(root, id);
    }

    private Node deleteRecursive(Node root, int id) {
        if (root == null) return null;

        if (id < root.id) {
            root.left = deleteRecursive(root.left, id);
        } else if (id > root.id) {
            root.right = deleteRecursive(root.right, id);
        } else {
            // Node ditemukan
            // Kasus 1 & 2: Tanpa anak atau hanya satu anak
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Kasus 3: Dua anak (ambil Inorder Successor)
            root.id = findMin(root.right);
            // Cari nama untuk ID yang baru dipindahkan
            Node temp = search(root.id);
            if (temp != null) root.nama = temp.nama;
            
            root.right = deleteRecursive(root.right, root.id);
        }
        return root;
    }

    private int findMin(Node root) {
        int min = root.id;
        while (root.left != null) {
            min = root.left.id;
            root = root.left;
        }
        return min;
    }

    // 4. Fitur Traversal (Menjelajahi Pohon)
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.println("ID: " + node.id + " | Nama: " + node.nama);
            inorder(node.right);
        }
    }

    public void preorder(Node node) {
        if (node != null) {
            System.out.println("ID: " + node.id + " | Nama: " + node.nama);
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.println("ID: " + node.id + " | Nama: " + node.nama);
        }
    }

    // --- STEP 3: MAIN METHOD (MENU INTERAKTIF) ---
    public static void main(String[] args) {
        PenjualanBST bst = new PenjualanBST();
        Scanner input = new Scanner(System.in);

        // DATA OTOMATIS (100 ITEM)
        int[] ids = {5288, 5993, 8689, 8043, 8699, 2156, 4457, 8938, 2618, 9033, 9971, 3874, 5914, 2398, 3725, 5210, 7363, 7631, 4513, 5656, 6453, 8783, 8194, 9783, 3685, 4490, 8294, 8563, 1070, 5408, 8258, 9309, 1138, 2751, 3258, 6402, 7921, 9781, 3818, 5204, 6119, 1928, 4207, 7255, 5309, 2897, 8028, 1660, 3248, 5641, 7376, 3525, 4492, 7187, 1305, 6602, 8153, 3561, 5082, 7151, 7524, 9178, 9817, 4304, 6820, 9151, 3482, 3316, 5192, 7572, 7660, 9224, 5083, 6362, 6465, 9888, 4159, 4969, 5097, 6271, 9250, 3409, 4577, 6244, 8612, 4650, 6799, 9298, 4361, 4379, 6928, 3195, 5741, 6852, 8147, 8902, 8967, 1302, 2363, 6861};
        String[] names = {"pensil", "pulpen", "penghapus", "buku", "sampul", "penggaris", "kertas", "cat", "stabilo", "mobil", "motor", "becak", "sepeda", "kereta", "pesawat", "perahu", "kapal", "rakit", "kipas", "charger", "peci", "sarung", "sajadah", "smartphone", "jam", "televisi", "laptop", "komputer", "mouse", "keyboard", "tablet", "jendela", "kaca", "pintu", "kompor", "lemari", "kasur", "ranjang", "bantal", "baju", "kaos", "celana", "mukena", "jilbab", "pigura", "antena", "kulkas", "dispenser", "meja", "kursi", "kemoceng", "sapu", "gayung", "sabun", "sikat", "shampo", "botol", "gelas", "piring", "panci", "wajan", "blender", "galon", "cobek", "termos", "kran", "selang", "karpet", "tikar", "keset", "sepatu", "kaos kaki", "jaket", "piama", "piano", "gitar", "angklung", "suling", "toples", "parfum", "sisir", "topi", "gunting", "pisau", "kaleng", "tisu", "tas", "ikat pinggang", "korek api", "kopi", "gula", "cabai", "wortel", "timun", "apel", "jeruk", "tomat", "pisang", "pepaya", "bawang"};

        // Looping untuk memasukkan semua data otomatis
        for (int i = 0; i < ids.length; i++) {
            bst.insert(ids[i], names[i]);
        }

        int pilihan;
        do {
            System.out.println("\n========= MENU PROGRAM BST =========");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Traversal Inorder (Urut ID)");
            System.out.println("5. Traversal Preorder");
            System.out.println("6. Traversal Postorder");
            System.out.println("0. Keluar");
            System.out.print("Pilih Menu: ");
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID: "); int idBaru = input.nextInt();
                    System.out.print("Masukkan Nama: "); String namaBaru = input.next();
                    bst.insert(idBaru, namaBaru);
                    System.out.println("Data berhasil ditambahkan.");
                    break;
                case 2:
                    System.out.print("Cari ID: "); int idCari = input.nextInt();
                    Node hasil = bst.search(idCari);
                    if (hasil != null) System.out.println("Ditemukan: " + hasil.nama);
                    else System.out.println("Data tidak ditemukan.");
                    break;
                case 3:
                    System.out.print("ID yang dihapus: "); int idHapus = input.nextInt();
                    bst.delete(idHapus);
                    System.out.println("Proses penghapusan selesai.");
                    break;
                case 4:
                    System.out.println("\n--- INORDER TRAVERSAL ---");
                    bst.inorder(bst.root);
                    break;
                case 5:
                    System.out.println("\n--- PREORDER TRAVERSAL ---");
                    bst.preorder(bst.root);
                    break;
                case 6:
                    System.out.println("\n--- POSTORDER TRAVERSAL ---");
                    bst.postorder(bst.root);
                    break;
                case 0:
                    System.out.println("Program Selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } while (pilihan != 0);
        
        input.close();
    }
}