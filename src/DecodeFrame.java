import javax.swing.*;
import java.awt.*;


public class DecodeFrame
{
    HuffmanDecoding huffman_decoding;
    JPanel encode_panel=new JPanel();
    Container cp;
    private JFrame frame;
    private final JTextArea txtArea = new JTextArea();


    public DecodeFrame(HuffmanDecoding huffmanDecoding)
    {
        this.huffman_decoding = huffmanDecoding;
        txtArea.setBounds(20, 80, 450, 300);
        txtArea.setColumns(10);
        txtArea.setText(huffman_decoding.readUnzipFile());
        txtArea.setFont(new Font("굴림", Font.BOLD, 16));
        txtArea.setEditable(false);

        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);

        frame=new JFrame();
        frame.setTitle("unzip");
        cp=frame.getContentPane();

        encode_panel.setBackground(new Color(163, 204, 162));
        encode_panel.setLayout(null);
        cp.add(encode_panel,BorderLayout.CENTER);



        JLabel txtLabel = new JLabel("unzip 파일");
        txtLabel.setFont(new Font("굴림", Font.BOLD, 16));
        txtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        txtLabel.setBounds(20, 55, 450, 20);

        encode_panel.add(txtLabel);
        JScrollPane scrollPane = new JScrollPane(txtArea);
        scrollPane.setBounds(20, 80, 450, 300);
        txtArea.setCaretPosition(0);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        encode_panel.add(scrollPane);

        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
