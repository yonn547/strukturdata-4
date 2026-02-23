import java.util.Scanner;

class Node {
    String berita;
    Node next, prev;

    public Node(String berita) {
        this.berita = berita;
    }
}

class CircularDoublyLinkedList {
    Node head = null;
    int size = 0;

    // 1. Insert berita di akhir
    public void insert(String data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
        System.out.println("Berita berhasil ditambahkan!");
    }

    // 2. Hapus berita berdasarkan nomor urut
    public void delete(int index) {
        if (head == null || index < 1 || index > size) {
            System.out.println("Nomor urut tidak valid.");
            return;
        }
        Node current = head;
        for (int i = 1; i < index; i++) current = current.next;

        if (size == 1) {
            head = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            if (current == head) head = current.next;
        }
        size--;
        System.out.println("Berita berhasil dihapus.");
    }

    // 3 & 4. Tampilkan Berita (Forward/Backward)
    public void display(boolean forward) {
        if (head == null) {
            System.out.println("Tidak ada berita.");
            return;
        }
        Node temp = head;
        if (!forward) temp = head.prev;

        for (int i = 0; i < size; i++) {
            System.out.println(">>> [NEWS]: " + temp.berita);
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
            temp = forward ? temp.next : temp.prev;
        }
    }

    // 5. Tampilkan berita tertentu
    public void showSpecific(int index) {
        if (head == null || index < 1 || index > size) {
            System.out.println("Berita tidak ditemukan.");
            return;
        }
        Node temp = head;
        for (int i = 1; i < index; i++) temp = temp.next;
        System.out.println("Berita ke-" + index + ": " + temp.berita);
    }
}

public class Main {
    public static void main(String[] args) {
        CircularDoublyLinkedList list = new CircularDoublyLinkedList();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- SIMULASI RUNNING TEXT TV ---");
            System.out.println("1. Insert Berita\n2. Hapus Berita\n3. Tampil Forward (3s delay)\n4. Tampil Backward (3s delay)\n5. Cari Berita\n6. Exit");
            System.out.print("Pilih: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> { System.out.print("Isi Berita: "); list.insert(sc.nextLine()); }
                case 2 -> { System.out.print("Nomor urut: "); list.delete(sc.nextInt()); }
                case 3 -> list.display(true);
                case 4 -> list.display(false);
                case 5 -> { System.out.print("Nomor urut: "); list.showSpecific(sc.nextInt()); }
            }
        } while (choice != 6);
    }
}