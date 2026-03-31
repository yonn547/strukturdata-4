# Inisialisasi Fixed Size Array (Kapasitas 10)
capacity = 10
mhs_array = [None] * capacity 
count = 0  # Variabel penghitung jumlah data

def input_data():
    nim = input("Masukkan NIM: ")
    nama = input("Masukkan Nama: ")
    return {"nim": nim, "nama": nama}

def insert_at_beginning():
    global count
    if count >= capacity:
        print("Array Penuh!")
        return

    # Geser elemen dari belakang ke depan (Manual shifting)
    for i in range(count, 0, -1):
        mhs_array[i] = mhs_array[i-1]
    
    mhs_array[0] = input_data()
    count += 1
    print("Data berhasil ditambahkan di awal.")

def insert_at_position():
    global count
    if count >= capacity:
        print("Array Penuh!")
        return

    try:
        pos = int(input(f"Masukkan posisi (1 - {count + 1}): "))
        if pos < 1 or pos > count + 1:
            print("Posisi tidak valid!")
            return
        
        index = pos - 1
        # Geser elemen untuk membuat ruang kosong
        for i in range(count, index, -1):
            mhs_array[i] = mhs_array[i-1]
            
        mhs_array[index] = input_data()
        count += 1
        print(f"Data berhasil ditambahkan di posisi {pos}.")
    except ValueError:
        print("Input harus angka!")

def insert_at_end():
    global count
    if count >= capacity:
        print("Array Penuh!")
        return
    
    # Masukkan langsung di indeks 'count'
    mhs_array[count] = input_data()
    count += 1
    print("Data berhasil ditambahkan di akhir.")

def delete_from_beginning():
    global count
    if count <= 0:
        print("Array Kosong!")
        return

    # Geser elemen ke kiri untuk menimpa data pertama
    for i in range(0, count - 1):
        mhs_array[i] = mhs_array[i+1]
    
    count -= 1
    mhs_array[count] = None # Bersihkan sisa data di akhir
    print("Data awal dihapus.")

def delete_at_position():
    global count
    if count <= 0:
        print("Array Kosong!")
        return

    try:
        pos = int(input(f"Masukkan posisi hapus (1 - {count}): "))
        if pos < 1 or pos > count:
            print("Posisi tidak valid!")
            return
        
        index = pos - 1
        # Geser elemen ke kiri mulai dari posisi yang dihapus
        for i in range(index, count - 1):
            mhs_array[i] = mhs_array[i+1]
            
        count -= 1
        mhs_array[count] = None
        print(f"Data posisi {pos} dihapus.")
    except ValueError:
        print("Input harus angka!")

def delete_from_end():
    global count
    if count <= 0:
        print("Array Kosong!")
        return
    
    count -= 1
    mhs_array[count] = None
    print("Data terakhir dihapus.")

def delete_first_occurrence():
    global count
    if count <= 0:
        print("Array Kosong!")
        return
    
    target_nim = input("Masukkan NIM yang akan dihapus: ")
    index_found = -1
    
    # Cari index (Linear Search)
    for i in range(count):
        if mhs_array[i]['nim'] == target_nim:
            index_found = i
            break
    
    if index_found != -1:
        # Geser manual
        for i in range(index_found, count - 1):
            mhs_array[i] = mhs_array[i+1]
        
        count -= 1
        mhs_array[count] = None
        print(f"Mahasiswa dengan NIM {target_nim} dihapus.")
    else:
        print("Data tidak ditemukan.")

def show_data():
    print("\n--- Daftar Mahasiswa ---")
    if count == 0:
        print("(Kosong)")
    else:
        for i in range(count):
            data = mhs_array[i]
            print(f"{i+1}. {data['nim']} - {data['nama']}")

# Main Loop
while True:
    print(f"\n=== MENU (Count: {count}/{capacity}) ===")
    print("1. Insert at beginning")
    print("2. Insert at given position")
    print("3. Insert at end")
    print("4. Delete from beginning")
    print("5. Delete given position")
    print("6. Delete from end")
    print("7. Delete first occurence")
    print("8. Show data")
    print("9. Exit")
    
    pilihan = input("Pilih menu: ")
    
    if pilihan == '1': insert_at_beginning()
    elif pilihan == '2': insert_at_position()
    elif pilihan == '3': insert_at_end()
    elif pilihan == '4': delete_from_beginning()
    elif pilihan == '5': delete_at_position()
    elif pilihan == '6': delete_from_end()
    elif pilihan == '7': delete_first_occurrence()
    elif pilihan == '8': show_data()
    elif pilihan == '9': 
        print("Keluar...")
        break
    else:
        print("Pilihan tidak valid.")