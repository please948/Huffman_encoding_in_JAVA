import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;


import java.awt.*;
import java.awt.event.*;

public class MainFrame
{
    private JFrame frame;
    JLabel center_title=new JLabel("허프만 압축 프로그램");
    JButton openBtn=new JButton("파일 찾기");
    JTextField text_field = new JTextField();
    JButton encodeBtn =new JButton("압축");
    JButton decodeBtn=new JButton("압축해제");
    JPanel jpanel=new JPanel();
    Container cp;
    String filePath;
    String fileName;
    FileManage file_manager;
    HuffmanTree huff_tree;
    HuffmanEncoding huffman_encoding;

    HuffmanDecoding huffman_decoding;
    public MainFrame()
    {
        frame=new JFrame();
        cp=frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("허프만 압축 프로그램");
        jpanel.setLayout(null);

        //프로그램 명 텍스트 설정
        center_title.setHorizontalAlignment(SwingConstants.CENTER);
        center_title.setFont(new Font("굴림", Font.BOLD, 26));
        center_title.setBounds(90, 50, 300, 75);

        //파일 열기 버튼 설정
        openBtn.setFont(new Font("굴림", Font.BOLD, 12));
        openBtn.setBounds(125, 210, 220, 30);
        //openBtn.addActionListener(new SwingAction());
        openBtn.addActionListener(new OpenListener());

        //불러온 파일 확인하는 필드 설정
        text_field.setEditable(false);
        text_field.setBounds(125, 170, 220, 35);
        text_field.setColumns(20);

        //압축 버튼 설정
        encodeBtn.setFont(new Font("굴림", Font.BOLD, 16));
        encodeBtn.setBounds(125, 250, 220, 30);
        encodeBtn.addActionListener(new SwingAction());

        //압축 해제 버튼 설정
        decodeBtn.setFont(new Font("굴림", Font.BOLD, 16));
        decodeBtn.setBounds(125, 290, 220, 30);
        decodeBtn.addActionListener(new SwingAction());

        //초기에는 버튼 비활성화
        encodeBtn.setEnabled(false);
        decodeBtn.setEnabled(false);

        jpanel.add(decodeBtn);
        jpanel.add(encodeBtn);
        jpanel.add(center_title);
        jpanel.add(openBtn);
        jpanel.add(text_field);

        jpanel.setBackground(new Color(163, 204, 162));
        cp.add(jpanel,BorderLayout.CENTER);

        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    class OpenListener implements ActionListener
    {
        JFileChooser file_chooser;

        OpenListener()
        {
            file_chooser= new JFileChooser();
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            FileNameExtensionFilter filter=new FileNameExtensionFilter("txt & huf",
                                               "txt","huf");
            file_chooser.setFileFilter(filter); //파일 필터 설정


            int ret=file_chooser.showOpenDialog(null);
            if(ret!=JFileChooser.APPROVE_OPTION)     //사용자가 취소 버튼을 누른 경우
            {
                JOptionPane.showMessageDialog(null, "파일 선택 취소");
                return;
            }

            //사용자가 파일을 선택하고 열기 버튼을 누른 경우
            filePath=file_chooser.getSelectedFile().getPath(); //파일 경로를 가져온다.
            fileName=file_chooser.getSelectedFile().getName(); //파일 이름을 가져온다.

            //파일 유형이 huf인 경우 압축 해제 버튼 활성화
            if(fileName.contains("huf"))
                decodeBtn.setEnabled(true);

           //파일 유형이 huf가 아니라면 압축 버튼 활성화
            else
                encodeBtn.setEnabled(true);

            text_field.setText(fileName);   //불러온 파일의 이름을 필드에 세트

            try
            {
                file_manager =new FileManage(filePath);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    private class SwingAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand()=="압축해제")
            {
                    huffman_decoding = new HuffmanDecoding(filePath);
                    huffman_decoding.decodingFile();
                    new DecodeFrame(huffman_decoding);
            }
            else if(e.getActionCommand()=="압축")
            {

                huff_tree = new HuffmanTree(file_manager.calcFreq(),filePath);

                try {
                    huffman_encoding = new HuffmanEncoding(filePath,huff_tree);
                    huffman_encoding.encodingFile();
                }

                catch (IOException e1) {e1.printStackTrace();}
                new EncodeFrame(file_manager,huff_tree,huffman_encoding);
                frame.setVisible(false);
            }
        }
    }
}