import javax.swing.*;
import java.awt.*;

public class ShowFrame
{
    HuffmanTree huffman_tree;
    JPanel encode_panel=new JPanel();
    Container cp;
    private JFrame frame;
    private final JTextArea txtArea = new JTextArea();


    public ShowFrame(HuffmanTree huffmanTree)
    {
        this.huffman_tree = huffmanTree;
        txtArea.setBounds(20, 80, 450, 300);
        txtArea.setColumns(10);
        txtArea.setText(huffman_tree.getHuffmanCode());
        txtArea.setFont(new Font("굴림", Font.BOLD, 16));
        txtArea.setEditable(false);
        txtArea.append("\n");
        txtArea.append(huffman_tree.getFreq());

        frame=new JFrame();
        frame.setTitle("허프만 압축");
        cp=frame.getContentPane();

        encode_panel.setBackground(new Color(163, 204, 162));
        encode_panel.setLayout(null);
        cp.add(encode_panel,BorderLayout.CENTER);



        JLabel txtLabel = new JLabel("압축 정보");
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
