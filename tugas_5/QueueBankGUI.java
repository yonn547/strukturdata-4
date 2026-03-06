import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class QueueBankGUI extends JFrame {
    private Queue<Nasabah> antrian;
    private DefaultTableModel tableModel;
    private JTable tableAntrian;
    private JTextField txtNama;
    private JLabel lblNomorAntrian;
    private int nomorTerakhir = 0;
    
    // Class untuk menyimpan data nasabah
    class Nasabah {
        int nomor;
        String nama;
        
        Nasabah(int nomor, String nama) {
            this.nomor = nomor;
            this.nama = nama;
        }
    }
    
    public QueueBankGUI() {
        antrian = new LinkedList<>();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Simulasi Antrian Bank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Input
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Ambil Antrian"));
        
        panelInput.add(new JLabel("Nama:"));
        txtNama = new JTextField(20);
        panelInput.add(txtNama);
        
        JButton btnAmbil = new JButton("Ambil Antrian");
        btnAmbil.setBackground(new Color(46, 204, 113));
        btnAmbil.setForeground(Color.WHITE);
        panelInput.add(btnAmbil);
        
        lblNomorAntrian = new JLabel("Nomor Antrian: -");
        lblNomorAntrian.setFont(new Font("Arial", Font.BOLD, 14));
        panelInput.add(lblNomorAntrian);
        
        // Panel Tabel
        String[] columns = {"No. Antrian", "Nama Nasabah"};
        tableModel = new DefaultTableModel(columns, 0);
        tableAntrian = new JTable(tableModel);
        tableAntrian.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tableAntrian);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Antrian"));
        
        // Panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnTampilkan = new JButton("Tampilkan Antrian");
        btnTampilkan.setBackground(new Color(52, 152, 219));
        btnTampilkan.setForeground(Color.WHITE);
        
        JButton btnPanggil = new JButton("Panggil Antrian");
        btnPanggil.setBackground(new Color(155, 89, 182));
        btnPanggil.setForeground(Color.WHITE);
        
        JButton btnReset = new JButton("Reset Antrian");
        btnReset.setBackground(new Color(231, 76, 60));
        btnReset.setForeground(Color.WHITE);
        
        panelTombol.add(btnTampilkan);
        panelTombol.add(btnPanggil);
        panelTombol.add(btnReset);
        
        // Add panels to frame
        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);
        
        // Action Listeners
        btnAmbil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ambilAntrian();
            }
        });
        
        btnTampilkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanAntrian();
            }
        });
        
        btnPanggil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panggilAntrian();
            }
        });
        
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAntrian();
            }
        });
        
        // Set frame properties
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
    
    private void ambilAntrian() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nama tidak boleh kosong!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        nomorTerakhir++;
        Nasabah nasabah = new Nasabah(nomorTerakhir, nama);
        antrian.offer(nasabah);
        
        lblNomorAntrian.setText("Nomor Antrian: " + nomorTerakhir);
        txtNama.setText("");
        
        JOptionPane.showMessageDialog(this, 
            "Antrian berhasil diambil!\nNomor: " + nomorTerakhir + "\nNama: " + nama,
            "Sukses", 
            JOptionPane.INFORMATION_MESSAGE);
        
        tampilkanAntrian();
    }
    
    private void tampilkanAntrian() {
        tableModel.setRowCount(0);
        
        for (Nasabah n : antrian) {
            Object[] row = {n.nomor, n.nama};
            tableModel.addRow(row);
        }
        
        if (antrian.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Antrian kosong!", 
                "Informasi", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void panggilAntrian() {
        if (antrian.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Tidak ada antrian untuk dipanggil!", 
                "Informasi", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Nasabah nasabah = antrian.poll();
        String pesan = "Memanggil: Nomor " + nasabah.nomor + " - " + nasabah.nama;
        
        // Tampilkan pesan
        JOptionPane.showMessageDialog(this, 
            pesan, 
            "Panggilan Antrian", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Simulasi panggilan suara (beep)
        playBeepSound();
        
        // Update tampilan
        tampilkanAntrian();
        
        // Baca panggilan dengan suara (Windows TTS sederhana)
        try {
            String command = "cmd /c echo " + pesan + " | powershell -Command \"Add-Type -AssemblyName System.Speech; $synth = New-Object System.Speech.Synthesis.SpeechSynthesizer; $synth.Speak('" + pesan + "')\"";
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            // Fallback jika TTS tidak tersedia
            System.out.println("TTS tidak tersedia");
        }
    }
    
    private void playBeepSound() {
        // Membuat suara beep sederhana
        final Runnable beepTask = new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        Toolkit.getDefaultToolkit().beep();
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(beepTask).start();
    }
    
    private void resetAntrian() {
        antrian.clear();
        nomorTerakhir = 0;
        lblNomorAntrian.setText("Nomor Antrian: -");
        tampilkanAntrian();
        
        JOptionPane.showMessageDialog(this, 
            "Antrian telah direset!", 
            "Sukses", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QueueBankGUI().setVisible(true);
            }
        });
    }
}