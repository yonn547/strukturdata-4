class Node:
    def __init__(self, id_data, nama_data):
        self.id = id_data
        self.nama = nama_data
        self.left = None
        self.right = None

class PenjualanBST:
    def __init__(self):
        self.root = None

    def insert(self, id_data, nama_data):
        self.root = self._insert_recursive(self.root, id_data, nama_data)

    def _insert_recursive(self, curr, id_val, nama_val):
        if curr is None: return Node(id_val, nama_val)
        if id_val < curr.id: curr.left = self._insert_recursive(curr.left, id_val, nama_val)
        elif id_val > curr.id: curr.right = self._insert_recursive(curr.right, id_val, nama_val)
        return curr

    def search(self, id_val):
        return self._search_recursive(self.root, id_val)

    def _search_recursive(self, curr, id_val):
        if curr is None or curr.id == id_val: return curr
        if id_val < curr.id: return self._search_recursive(curr.left, id_val)
        return self._search_recursive(curr.right, id_val)

    def delete(self, id_val):
        self.root = self._delete_recursive(self.root, id_val)

    def _delete_recursive(self, curr, id_val):
        if curr is None: return curr
        if id_val < curr.id: curr.left = self._delete_recursive(curr.left, id_val)
        elif id_val > curr.id: curr.right = self._delete_recursive(curr.right, id_val)
        else:
            if curr.left is None: return curr.right
            elif curr.right is None: return curr.left
            temp = self._min_value_node(curr.right)
            curr.id, curr.nama = temp.id, temp.nama
            curr.right = self._delete_recursive(curr.right, temp.id)
        return curr

    def _min_value_node(self, node):
        current = node
        while current.left is not None: current = current.left
        return current

    def inorder(self, node):
        if node:
            self.inorder(node.left)
            print(f"[{node.id}: {node.nama}]", end=" ")
            self.inorder(node.right)

    def preorder(self, node):
        if node:
            print(f"[{node.id}: {node.nama}]", end=" ")
            self.preorder(node.left)
            self.preorder(node.right)

    def postorder(self, node):
        if node:
            self.postorder(node.left)
            self.postorder(node.right)
            print(f"[{node.id}: {node.nama}]", end=" ")

# --- PROGRAM UTAMA DENGAN MENU ---
if __name__ == "__main__":
    bst = PenjualanBST()
    
    # Input 100 data otomatis di awal (Testing)
    dataset = [(5288,"pensil"), (5993,"pulpen"), (8689,"penghapus"), (8043,"buku"), (8699,"sampul"), (2156,"penggaris"), (4457,"kertas"), (8938,"cat"), (2618,"stabilo"), (9033,"mobil"), (9971,"motor"), (3874,"becak"), (5914,"sepeda"), (2398,"kereta"), (3725,"pesawat"), (5210,"perahu"), (7363,"kapal"), (7631,"rakit"), (4513,"kipas"), (5656,"charger"), (6453,"peci"), (8783,"sarung"), (8194,"sajadah"), (9783,"smartphone"), (3685,"jam"), (4490,"televisi"), (8294,"laptop"), (8563,"komputer"), (1070,"mouse"), (5408,"keyboard"), (8258,"tablet"), (9309,"jendela"), (1138,"kaca"), (2751,"pintu"), (3258,"kompor"), (6402,"lemari"), (7921,"kasur"), (9781,"ranjang"), (3818,"bantal"), (5204,"baju"), (6119,"kaos"), (1928,"celana"), (4207,"mukena"), (7255,"jilbab"), (5309,"pigura"), (2897,"antena"), (8028,"kulkas"), (1660,"dispenser"), (3248,"meja"), (5641,"kursi"), (7376,"kemoceng"), (3525,"sapu"), (4492,"gayung"), (7187,"sabun"), (1305,"sikat"), (6602,"shampo"), (8153,"botol"), (3561,"gelas"), (5082,"piring"), (7151,"panci"), (7524,"wajan"), (9178,"blender"), (9817,"galon"), (4304,"cobek"), (6820,"termos"), (9151,"kran"), (3482,"selang"), (3316,"karpet"), (5192,"tikar"), (7572,"keset"), (7660,"sepatu"), (9224,"kaos kaki"), (5083,"jaket"), (6362,"piama"), (6465,"piano"), (9888,"gitar"), (4159,"angklung"), (4969,"suling"), (5097,"toples"), (6271,"parfum"), (9250,"sisir"), (3409,"topi"), (4577,"gunting"), (6244,"pisau"), (8612,"kaleng"), (4650,"tisu"), (6799,"tas"), (9298,"ikat pinggang"), (4361,"korek api"), (4379,"kopi"), (6928,"gula"), (3195,"cabai"), (5741,"wortel"), (6852,"timun"), (8147,"apel"), (8902,"jeruk"), (8967,"tomat"), (1302,"pisang"), (2363,"pepaya"), (6861,"bawang")]
    for i, n in dataset: bst.insert(i, n)

    while True:
        print("\n" + "="*30)
        print(" MENU STRUKTUR DATA BST ")
        print("="*30)
        print("1. Tambah Data")
        print("2. Cari Data (berdasarkan ID)")
        print("3. Hapus Data (berdasarkan ID)")
        print("4. Tampilkan (Inorder - Urut ID)")
        print("5. Tampilkan (Preorder)")
        print("6. Tampilkan (Postorder)")
        print("7. Keluar")
        
        pilihan = input("Pilih menu (1-7): ")

        if pilihan == '1':
            id_in = int(input("Masukkan ID baru: "))
            nama_in = input("Masukkan Nama barang: ")
            bst.insert(id_in, nama_in)
            print("Data berhasil ditambahkan!")
        
        elif pilihan == '2':
            id_cari = int(input("Masukkan ID yang dicari: "))
            res = bst.search(id_cari)
            print(f"Hasil: {res.nama}" if res else "ID tidak ditemukan.")

        elif pilihan == '3':
            id_hapus = int(input("Masukkan ID yang akan dihapus: "))
            bst.delete(id_hapus)
            print(f"ID {id_hapus} telah diproses untuk dihapus.")

        elif pilihan == '4':
            print("\nInorder Traversal:")
            bst.inorder(bst.root)
            print()

        elif pilihan == '5':
            print("\nPreorder Traversal:")
            bst.preorder(bst.root)
            print()

        elif pilihan == '6':
            print("\nPostorder Traversal:")
            bst.postorder(bst.root)
            print()

        elif pilihan == '7':
            print("Terima kasih! Program selesai.")
            break
        else:
            print("Pilihan tidak valid.")