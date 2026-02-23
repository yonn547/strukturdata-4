import time

class Node:
    def __init__(self, berita):
        self.berita = berita
        self.next = None
        self.prev = None

class RunningText:
    def __init__(self):
        self.head = None
        self.size = 0

    def insert_berita(self, text):
        new_node = Node(text)
        if not self.head:
            self.head = new_node
            self.head.next = self.head
            self.head.prev = self.head
        else:
            last = self.head.prev
            last.next = new_node
            new_node.prev = last
            new_node.next = self.head
            self.head.prev = new_node
        self.size += 1
        print("Berita ditambahkan.")

    def hapus_berita(self, index):
        if not self.head or index < 1 or index > self.size:
            print("Indeks tidak valid.")
            return
        
        curr = self.head
        for _ in range(index - 1):
            curr = curr.next
        
        if self.size == 1:
            self.head = None
        else:
            curr.prev.next = curr.next
            curr.next.prev = curr.prev
            if curr == self.head:
                self.head = curr.next
        self.size -= 1
        print("Berita dihapus.")

    def tampilkan(self, forward=True):
        if not self.head:
            print("Kosong.")
            return
        
        curr = self.head if forward else self.head.prev
        for _ in range(self.size):
            print(f"\n>>> [BREAKING NEWS]: {curr.berita}")
            time.sleep(3)
            curr = curr.next if forward else curr.prev

    def tampil_spesifik(self, index):
        if not self.head or index < 1 or index > self.size:
            print("Tidak ditemukan.")
            return
        curr = self.head
        for _ in range(index - 1):
            curr = curr.next
        print(f"Hasil: {curr.berita}")

# Menu Utama
rt = RunningText()
while True:
    print("\n=== MENU NEWS TICKER ===")
    print("1. Insert | 2. Hapus | 3. Forward | 4. Backward | 5. Cari | 6. Exit")
    pilih = input("Pilihan: ")
    
    if pilih == '1':
        rt.insert_berita(input("Teks Berita: "))
    elif pilih == '2':
        rt.hapus_berita(int(input("Nomor urut: ")))
    elif pilih == '3':
        rt.tampilkan(forward=True)
    elif pilih == '4':
        rt.tampilkan(forward=False)
    elif pilih == '5':
        rt.t_spesifik(int(input("Nomor urut: ")))
    elif pilih == '6':
        break