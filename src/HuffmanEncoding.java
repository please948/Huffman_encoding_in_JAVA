import java.io.*;
import java.util.*;
import java.io.File;

import java.util.Map;
public class HuffmanEncoding {

    private String txtFilePath;
    private String hufFilePath;
    private String hufFileName;
    HuffmanTree huffman_tree;
    public HuffmanEncoding(String filePath,HuffmanTree HuffmanTree) throws IOException {

        File file = new File(filePath);
        this.txtFilePath = filePath;
        this.huffman_tree = HuffmanTree;
        this.hufFileName = file.getName().replace(".txt", ".huf"); // 파일 이름 변경
        this.hufFilePath = file.getParent() + File.separator + hufFileName;
    }
    public void WriteHashMap(ObjectOutputStream objectStream, Map<Character, String> huffmanCodeMap) {
        try {
            // 허프만 코드 맵을 직렬화하여 저장
            objectStream.writeObject(huffmanCodeMap);

        } catch (IOException e) {
            System.err.println("허프만 코드 맵 직렬화 실패");
            e.printStackTrace();
        }
    }


    public void encodingFile() {
        try (BufferedReader txt_reader = new BufferedReader(new FileReader(txtFilePath));
             FileOutputStream huf_stream = new FileOutputStream(hufFilePath);
             ObjectOutputStream objectStream = new ObjectOutputStream(huf_stream)) {

            Map<Character, String> huffmanCodes = huffman_tree.makingHuffmanCode();

            // 직렬화한 해시맵을 huf에 저장
            WriteHashMap(objectStream,huffmanCodes);

            // 구분자 사용
            huf_stream.write(0x00);
            huf_stream.flush();  // 버퍼 비우기

            StringBuilder encoded_sb = new StringBuilder();
            String currentLine;

            while ((currentLine = txt_reader.readLine()) != null) {
                for (char character : currentLine.toCharArray()) {
                    String code = huffmanCodes.get(character);
                    if (code != null) {
                        encoded_sb.append(code);
                    }
                }
                // 줄바꿈 문자를 허프만 코드에 포함
                String newlineCode = huffmanCodes.get('\n');
                if (newlineCode != null) {
                    encoded_sb.append(newlineCode);
                }
            }
            int paddingBits = (8 - (encoded_sb.length() % 8)) % 8;  //패딩 구현

            // 비트셋을 바이트 배열로 변환
            BitSet bitSet = new BitSet(encoded_sb.length()+ paddingBits);
            for (int i = 0; i < encoded_sb.length(); i++) {
                if (encoded_sb.charAt(i) == '1') {
                    bitSet.set(i);
                }
            }
            for (int i = 0; i < paddingBits; i++) {
                bitSet.set(encoded_sb.length() + i, false); // 패딩은 0으로 설정
            }

            huf_stream.write(paddingBits); // 패딩 저장
            byte[] byteArray = bitSet.toByteArray();

            huf_stream.write(byteArray);


        } catch (IOException e) {
            System.err.println("파일 읽기 실패");
            e.printStackTrace();
        }
    }
    public double calculateRatio() {
        File txt_file = new File(txtFilePath);
        File huf_file = new File(hufFilePath);

        long txt_size = txt_file.length(); // 원본 파일 크기
        long huf_size = huf_file.length(); // 압축된 파일 크기

        // 압축률 계산 수식
        double ratio = (double) huf_size / txt_size;
        return (1-ratio) * 100; // 백분율로 반환
    }

}
