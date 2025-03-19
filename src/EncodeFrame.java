import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EncodeFrame
{
    FileManage file_manager;
    HuffmanTree huffman_tree;
    HuffmanEncoding huffman_encoding;
    JPanel encode_panel=new JPanel();
    Container cp;
    private JFrame frame;
    private final JTextArea txtArea = new JTextArea();
    private final JTextArea huffArea = new JTextArea();


    public EncodeFrame(FileManage FileManager,HuffmanTree HuffmanTree,HuffmanEncoding huffman_encoding)
    {
        this.file_manager = FileManager;
        this.huffman_tree = HuffmanTree;
        this.huffman_encoding = huffman_encoding;
        txtArea.setBounds(20, 80, 450, 300);
        txtArea.setColumns(10);
        txtArea.setText(file_manager.readFile());
        txtArea.setFont(new Font("굴림", Font.BOLD, 16));
        txtArea.setEditable(false);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);


        huffArea.setBounds(485, 80, 450, 300);
        huffArea.setColumns(10);
        huffArea.setText(huffman_tree.codeToString());
        huffArea.setFont(new Font("굴림", Font.BOLD, 16));
        huffArea.setEditable(false);
        huffArea.setLineWrap(true);
        huffArea.setWrapStyleWord(true);

        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("허프만 압축");
        cp=frame.getContentPane();

        encode_panel.setBackground(new Color(163, 204, 162));
        encode_panel.setLayout(null);
        cp.add(encode_panel,BorderLayout.CENTER);

        JLabel hufLabel = new JLabel("huf 파일");
        hufLabel.setFont(new Font("굴림", Font.BOLD, 16));
        hufLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hufLabel.setBounds(485, 55, 450, 20);

        JLabel txtLabel = new JLabel("txt 파일");
        txtLabel.setFont(new Font("굴림", Font.BOLD, 16));
        txtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        txtLabel.setBounds(20, 55, 450, 20);

        encode_panel.add(txtLabel);
        encode_panel.add(hufLabel);

        JScrollPane scrollPane = new JScrollPane(txtArea);
        JScrollPane scrollPane2 = new JScrollPane(huffArea);
        scrollPane.setBounds(20, 80, 450, 300);
        scrollPane2.setBounds(485, 80, 450, 300);
        txtArea.setCaretPosition(0);
        huffArea.setCaretPosition(0);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        encode_panel.add(scrollPane);
        encode_panel.add(scrollPane2);

        JPanel rate_panel = new JPanel();
        rate_panel.setForeground(SystemColor.white);
        rate_panel.setBackground(SystemColor.white);
        rate_panel.setBounds(485, 400, 140, 30);
        encode_panel.add(rate_panel);
        rate_panel.setLayout(null);

        String encodeRate="압축률: ";
        encodeRate += String.format("%.2f", huffman_encoding.calculateRatio()) + " %";
        JLabel encodeRateLabel = new JLabel(encodeRate);
        encodeRateLabel.setFont(new Font("굴림", Font.BOLD, 14));
        encodeRateLabel.setBounds(13, 0, 333, 30);
        rate_panel.add(encodeRateLabel);

        JButton headBtn = new JButton("압축 정보");
        headBtn.setFont(new Font("굴림", Font.BOLD, 12));
        headBtn.setBounds(640, 400, 140, 30);
        headBtn.addActionListener(new EncodeActionListener());
        encode_panel.add(headBtn);

        JButton to_mainframeBtn = new JButton("메뉴로");
        to_mainframeBtn.setFont(new Font("굴림", Font.BOLD, 12));
        to_mainframeBtn.setBounds(795, 400, 140, 30);
        to_mainframeBtn.addActionListener(new EncodeActionListener());
        encode_panel.add(to_mainframeBtn);

        frame.setSize(1000,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    class EncodeActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand()=="메뉴로") {
                frame.setVisible(false);
                new MainFrame();
            }

            else if(e.getActionCommand()=="압축 정보") {
                new ShowFrame(huffman_tree);
            }
        }
    }
}