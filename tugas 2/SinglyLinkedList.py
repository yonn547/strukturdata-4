class Node:
    def __init__(self, nim, nama):
        self.nim = nim
        self.nama = nama
        self.next = None

class SinglyLinkedList:
    def __init__(self):
        self.head = None
        self.count = 0

    def show_data(self):
        if not self.head:
            print("\n[!] List Kosong.")
            return
        print(f"\n--- Data Mahasiswa (Total: {self.count}) ---")
        temp = self.head
        pos = 1
        while temp:
            print(f"{pos}. [{temp.nim}] {temp.nama}")
            temp = temp.next
            pos += 1

    def insert_beginning(self, nim, nama):
        new_node = Node(nim, nama)
        new_node.next = self.head
        self.head = new_node
        self.count += 1

    def insert_end(self, nim, nama):
        new_node = Node(nim, nama)
        if not self.head:
            self.head = new_node
        else:
            temp = self.head
            while temp.next:
                temp = temp.next
            temp.next = new_node
        self.count += 1

    def insert_at_pos(self, nim, nama, pos):
        if pos < 1 or pos > self.count + 1:
            print("[!] Posisi di luar jangkauan!")
            return
        
        if pos == 1:
            self.insert_beginning(nim, nama)
        else:
            new_node = Node(nim, nama)
            temp = self.head
            for _ in range(pos - 2):
                temp = temp.next
            new_node.next = temp.next
            temp.next = new_node
            self.count += 1

    def delete_beginning(self):
        if self.head:
            self.head = self.head.next
            self.count -= 1
        else:
            print("[!] List sudah kosong.")

    def delete_end(self):
        if not self.head:
            print("[!] List kosong.")
            return
        if not self.head.next:
            self.head = None
        else:
            temp = self.head
            while temp.next.next:
                temp = temp.next
            temp.next = None
        self.count -= 1

    def delete_at_pos(self, pos):
        if pos < 1 or pos > self.count:
            print("[!] Posisi tidak valid.")
            return
        if pos == 1:
            self.delete_beginning()
        else:
            temp = self.head
            for _ in range(pos - 2):
                temp = temp.next
            temp.next = temp.next.next
            self.count -= 1

    def delete_by_nim(self, nim):
        temp = self.head
        prev = None
        while temp:
            if temp.nim == nim:
                if prev:
                    prev.next = temp.next
                else:
                    self.head = temp.next
                self.count -= 1
                print(f"[V] Data NIM {nim} berhasil dihapus.")
                return
            prev = temp
            temp = temp.next
        print("[!] NIM tidak ditemukan.")

# --- BAGIAN MENU UTAMA ---
def main():
    mhs_list = SinglyLinkedList()
    
    while True:
        print("\n=== MENU LINKED LIST MAHASISWA ===")
        print("1. Insert Beginning  5. Delete Position")
        print("2. Insert Position   6. Delete End")
        print("3. Insert End        7. Delete by NIM")
        print("4. Delete Beginning  8. Show Data")
        print("9. Exit")
        
        choice = input("Pilih menu (1-9): ")
        
        if choice in ['1', '2', '3']:
            nim = input("Masukkan NIM: ")
            nama = input("Masukkan Nama: ")
            if choice == '1': mhs_list.insert_beginning(nim, nama)
            elif choice == '3': mhs_list.insert_end(nim, nama)
            else:
                p = int(input(f"Posisi (1-{mhs_list.count+1}): "))
                mhs_list.insert_at_pos(nim, nama, p)
        
        elif choice == '4': mhs_list.delete_beginning()
        elif choice == '5':
            p = int(input(f"Posisi yang dihapus (1-{mhs_list.count}): "))
            mhs_list.delete_at_pos(p)
        elif choice == '6': mhs_list.delete_end()
        elif choice == '7':
            n = input("Masukkan NIM yang akan dihapus: ")
            mhs_list.delete_by_nim(n)
        elif choice == '8': mhs_list.show_data()
        elif choice == '9': break
        else: print("Pilihan tidak valid!")

if __name__ == "__main__":
    main()