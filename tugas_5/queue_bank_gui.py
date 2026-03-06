import tkinter as tk
from tkinter import messagebox, ttk
from collections import deque
import time
import threading

class QueueBankGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Simulasi Antrian Bank")
        self.root.geometry("600x400")
        
        # Gunakan deque sebagai queue (FIFO)
        self.antrian = deque()
        self.nomor_terakhir = 0
        
        self.setup_ui()
    
    def setup_ui(self):
        # Main frame
        main_frame = tk.Frame(self.root, bg='#f0f0f0')
        main_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # Title
        title_label = tk.Label(main_frame, text="🏦 SISTEM ANTRIAN BANK", 
                               font=('Arial', 16, 'bold'), 
                               bg='#2c3e50', fg='white', pady=10)
        title_label.pack(fill=tk.X, pady=(0, 10))
        
        # Input Frame
        input_frame = tk.LabelFrame(main_frame, text="✏️ Ambil Antrian", 
                                   font=('Arial', 10, 'bold'),
                                   bg='#f0f0f0', padx=10, pady=10)
        input_frame.pack(fill=tk.X, pady=5)
        
        # Nama input
        tk.Label(input_frame, text="Nama Nasabah:", 
                bg='#f0f0f0', font=('Arial', 10)).grid(row=0, column=0, padx=5)
        
        self.txt_nama = tk.Entry(input_frame, width=30, font=('Arial', 10))
        self.txt_nama.grid(row=0, column=1, padx=5)
        self.txt_nama.focus()
        
        # Tombol Ambil
        self.btn_ambil = tk.Button(input_frame, text="AMBIL ANTRIAN", 
                                   bg='#27ae60', fg='white', 
                                   font=('Arial', 10, 'bold'),
                                   padx=15, pady=5,
                                   command=self.ambil_antrian)
        self.btn_ambil.grid(row=0, column=2, padx=10)
        
        # Label nomor antrian
        self.lbl_nomor = tk.Label(input_frame, text="Nomor Antrian: -", 
                                 font=('Arial', 11, 'bold'),
                                 bg='#3498db', fg='white', padx=10, pady=5)
        self.lbl_nomor.grid(row=0, column=3, padx=10)
        
        # Table Frame
        table_frame = tk.LabelFrame(main_frame, text="📋 Daftar Antrian", 
                                   font=('Arial', 10, 'bold'),
                                   bg='#f0f0f0', padx=10, pady=10)
        table_frame.pack(fill=tk.BOTH, expand=True, pady=5)
        
        # Scrollbars
        scrollbar_y = tk.Scrollbar(table_frame)
        scrollbar_y.pack(side=tk.RIGHT, fill=tk.Y)
        
        scrollbar_x = tk.Scrollbar(table_frame, orient=tk.HORIZONTAL)
        scrollbar_x.pack(side=tk.BOTTOM, fill=tk.X)
        
        # Treeview untuk tabel
        columns = ('No. Antrian', 'Nama Nasabah', 'Waktu')
        self.tree = ttk.Treeview(table_frame, columns=columns, 
                                 show='headings', height=10,
                                 yscrollcommand=scrollbar_y.set,
                                 xscrollcommand=scrollbar_x.set)
        
        # Konfigurasi kolom
        self.tree.heading('No. Antrian', text='No. Antrian')
        self.tree.heading('Nama Nasabah', text='Nama Nasabah')
        self.tree.heading('Waktu', text='Waktu Ambil')
        
        self.tree.column('No. Antrian', width=100, anchor='center')
        self.tree.column('Nama Nasabah', width=250, anchor='w')
        self.tree.column('Waktu', width=150, anchor='center')
        
        self.tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        
        # Konfigurasi scrollbar
        scrollbar_y.config(command=self.tree.yview)
        scrollbar_x.config(command=self.tree.xview)
        
        # Button Frame
        button_frame = tk.Frame(main_frame, bg='#f0f0f0')
        button_frame.pack(fill=tk.X, pady=10)
        
        # Tombol Tampilkan
        self.btn_tampilkan = tk.Button(button_frame, text="🔄 TAMPILKAN", 
                                       bg='#3498db', fg='white',
                                       font=('Arial', 9, 'bold'),
                                       padx=15, pady=5,
                                       command=self.tampilkan_antrian)
        self.btn_tampilkan.pack(side=tk.LEFT, padx=5)
        
        # Tombol Panggil
        self.btn_panggil = tk.Button(button_frame, text="📢 PANGGIL", 
                                     bg='#9b59b6', fg='white',
                                     font=('Arial', 9, 'bold'),
                                     padx=15, pady=5,
                                     command=self.panggil_antrian)
        self.btn_panggil.pack(side=tk.LEFT, padx=5)
        
        # Tombol Reset
        self.btn_reset = tk.Button(button_frame, text="🗑️ RESET", 
                                   bg='#e74c3c', fg='white',
                                   font=('Arial', 9, 'bold'),
                                   padx=15, pady=5,
                                   command=self.reset_antrian)
        self.btn_reset.pack(side=tk.LEFT, padx=5)
        
        # Status Bar
        self.status_bar = tk.Label(main_frame, text="Siap", 
                                   bg='#34495e', fg='white',
                                   font=('Arial', 9), anchor='w', padx=10)
        self.status_bar.pack(fill=tk.X, pady=(5, 0))
        
        # Bind Enter key
        self.txt_nama.bind('<Return>', lambda event: self.ambil_antrian())
    
    def ambil_antrian(self):
        nama = self.txt_nama.get().strip()
        
        if not nama:
            messagebox.showwarning("Peringatan", 
                                  "Nama tidak boleh kosong!")
            return
        
        # Generate nomor otomatis
        self.nomor_terakhir += 1
        
        # Simpan data dengan waktu
        from datetime import datetime
        waktu_sekarang = datetime.now().strftime("%H:%M:%S")
        
        nasabah = {
            'nomor': self.nomor_terakhir,
            'nama': nama,
            'waktu': waktu_sekarang
        }
        
        # Masukkan ke antrian (FIFO)
        self.antrian.append(nasabah)
        
        # Update UI
        self.lbl_nomor.config(text=f"Nomor Antrian: {self.nomor_terakhir}")
        self.txt_nama.delete(0, tk.END)
        
        # Tampilkan notifikasi
        self.status_bar.config(text=f"✅ Antrian ditambahkan: Nomor {self.nomor_terakhir} - {nama}")
        
        # Update tampilan tabel
        self.tampilkan_antrian()
    
    def tampilkan_antrian(self):
        # Hapus semua data di tree
        for item in self.tree.get_children():
            self.tree.delete(item)
        
        # Tampilkan semua data dalam antrian
        for nasabah in self.antrian:
            self.tree.insert('', tk.END, values=(
                nasabah['nomor'], 
                nasabah['nama'],
                nasabah['waktu']
            ))
        
        # Update status
        jumlah = len(self.antrian)
        if jumlah > 0:
            depan = self.antrian[0]  # Lihat data paling depan
            self.status_bar.config(
                text=f"📊 Total Antrian: {jumlah} | Antrian Depan: {depan['nomor']} - {depan['nama']}"
            )
        else:
            self.status_bar.config(text="📊 Antrian kosong")
    
    def panggil_antrian(self):
        if len(self.antrian) == 0:
            messagebox.showinfo("Informasi", 
                               "Tidak ada antrian untuk dipanggil!")
            return
        
        # Ambil data paling depan (FIFO)
        nasabah = self.antrian.popleft()  # pop from left = FIFO
        
        pesan = f"PANGGILAN: Nomor {nasabah['nomor']} - {nasabah['nama']}"
        
        # Tampilkan pesan
        messagebox.showinfo("📢 Panggilan Antrian", pesan)
        
        # Panggil suara (beep sederhana)
        self.play_beep()
        
        # Update status
        self.status_bar.config(text=f"🔊 {pesan}")
        
        # Update tampilan
        self.tampilkan_antrian()
    
    def play_beep(self):
        """Memainkan suara beep sederhana"""
        def beep():
            for _ in range(3):
                print('\a', end='', flush=True)  # Bell character
                time.sleep(0.3)
        
        # Jalankan di thread terpisah
        thread = threading.Thread(target=beep)
        thread.daemon = True
        thread.start()
    
    def reset_antrian(self):
        # Konfirmasi reset
        if messagebox.askyesno("Konfirmasi", 
                               "Apakah Anda yakin ingin mereset semua antrian?"):
            # Kosongkan antrian
            self.antrian.clear()
            self.nomor_terakhir = 0
            
            # Update UI
            self.lbl_nomor.config(text="Nomor Antrian: -")
            self.tampilkan_antrian()
            self.status_bar.config(text="🔄 Antrian telah direset")

def main():
    root = tk.Tk()
    app = QueueBankGUI(root)
    root.mainloop()

if __name__ == "__main__":
    main()