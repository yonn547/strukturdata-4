import java.util.Scanner;

class Node {
    String nim, nama;
    Node next;
    Node(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
        this.next = null;
    }
}

class SinglyLinkedList {
    Node head;
    int count = 0;

    void insertBeginning(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        newNode.next = head;
        head = newNode;
        count++;
    }

    void insertAtPos(String nim, String nama, int pos) {
        if (pos < 1 || pos > count + 1) return;
        if (pos == 1) insertBeginning(nim, nama);
        else {
            Node newNode = new Node(nim, nama);
            Node temp = head;
            for (int i = 1; i < pos - 1; i++) temp = temp.next;
            newNode.next = temp.next;
            temp.next = newNode;
            count++;
        }
    }

    void insertEnd(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        if (head == null) head = newNode;
        else {
            Node temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }
        count++;
    }

    void deleteBeginning() {
        if (head != null) {
            head = head.next;
            count--;
        }
    }

    void deleteAtPos(int pos) {
        if (pos < 1 || pos > count) return;
        if (pos == 1) deleteBeginning();
        else {
            Node temp = head;
            for (int i = 1; i < pos - 1; i++) temp = temp.next;
            temp.next = temp.next.next;
            count--;
        }
    }

    void deleteEnd() {
        if (head == null) return;
        if (head.next == null) head = null;
        else {
            Node temp = head;
            while (temp.next.next != null) temp = temp.next;
            temp.next = null;
        }
        count--;
    }

    void deleteFirstOccurrence(String nim) {
        Node temp = head, prev = null;
        while (temp != null) {
            if (temp.nim.equals(nim)) {
                if (prev == null) head = temp.next;
                else prev.next = temp.next;
                count--;
                return;
            }
            prev = temp;
            temp = temp.next;
        }
    }

    void showData() {
        if (head == null) {
            System.out.println("List Kosong.");
            return;
        }
        Node temp = head;
        int i = 1;
        while (temp != null) {
            System.out.println(i + ". [" + temp.nim + "] " + temp.nama);
            temp = temp.next;
            i++;
        }
    }
}

public class MahasiswaApp {
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        Scanner sc = new Scanner(System.in);
        String choiceStr;

        do {
            System.out.println("\n=== MENU JAVA LINKED LIST ===");
            System.out.println("1-3: Insert, 4-6: Delete, 7: Del NIM, 8: Show, 9: Exit");
            System.out.print("Pilih: ");
            choiceStr = sc.nextLine();
            int choice = Integer.parseInt(choiceStr.replaceAll("[^0-9]", "0"));

            if (choice >= 1 && choice <= 3) {
                System.out.print("NIM: "); String nim = sc.nextLine();
                System.out.print("Nama: "); String nama = sc.nextLine();
                if (choice == 1) list.insertBeginning(nim, nama);
                else if (choice == 3) list.insertEnd(nim, nama);
                else {
                    System.out.print("Pos: ");
                    int p = Integer.parseInt(sc.nextLine());
                    list.insertAtPos(nim, nama, p);
                }
            } else if (choice == 4) list.deleteBeginning();
            else if (choice == 5) {
                System.out.print("Pos: ");
                int p = Integer.parseInt(sc.nextLine());
                list.deleteAtPos(p);
            } else if (choice == 6) list.deleteEnd();
            else if (choice == 7) {
                System.out.print("NIM: "); String n = sc.nextLine();
                list.deleteFirstOccurrence(n);
            } else if (choice == 8) list.showData();
            else if (choice == 9) break;

        } while (true);
    }
}