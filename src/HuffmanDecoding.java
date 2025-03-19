import java.io.*;
import java.util.*;
import java.nio.file.Path;
public class HuffmanDecoding {

    private String hufFilePath;
    private String unzipFilePath;
    private Path unzipPath;
    public HuffmanDecoding(String hufFilePath) {
        this.hufFilePath = hufFilePath;
        this.unzipFilePath = hufFilePath.replace(".huf", ".unzip");  // unzip파일 경로 생성
        this.unzipPath = Path.of(unzipFilePath);
    }

    public void decodingFile() {        //huf파일을 받아서 압축해제 하여 unzip파일에 저장
        try (FileInputStream huf_stream = new FileInputStream(hufFilePath);
             ObjectInputStream objectStream = new ObjectInputStream(huf_stream)) {

            Map<Character, String> huffman_code_map = (Map<Character, String>) objectStream.readObject(); //해시맵 역 직렬화

            // 구분자 읽기
            int separator = huf_stream.read();
            if (separator != 0x00) {        //설정한 구분자를 읽지 못한 경우
                System.out.println("구분자 확인 실패");
                return;
            }

            // 패딩 비트 읽기
            int paddingBits = huf_stream.read();
            if (paddingBits == -1) {
                System.out.println("패딩 비트 읽기 실패");
                return;
            }

            // 압축 데이터 읽기
            byte[] encoded_data = huf_stream.readAllBytes();
            BitSet bitSet = BitSet.valueOf(encoded_data);

            // 비트셋을 비트 문자열로 변환
            StringBuilder bit_sb = new StringBuilder(); // 허프만 테이블을 저장하는 빌더 생성
            for (int i = 0; i <= bitSet.length() ; i++) {
                bit_sb.append(bitSet.get(i) ? '1' : '0');
            }

            // 비트를 문자로 변환
            StringBuilder txt_sb = new StringBuilder(); //복호화된 파일 전체의 내용을 저장하는 빌더 생성
            Map<String, Character> reversed_map = new HashMap<>();     //허프만 해시맵의 키와 밸루값을 전환한 해시맵 생성
            for (Map.Entry<Character, String> entry : huffman_code_map.entrySet()) {
                reversed_map.put(entry.getValue(), entry.getKey());
            }

            StringBuilder char_sb = new StringBuilder();    //복호화된 문자를 저장하는 빌더 생성
            for (char bit : bit_sb.toString().toCharArray()) {  //bit_sb의 비트들을 char_sb에 저장
                char_sb.append(bit);
                if (reversed_map.containsKey(char_sb.toString())) {     //char_sb에 저장된 비트가 리버스맵에 포함되면 txt_sb에 추가하고 char_sb를 초기화
                    char decodedChar = reversed_map.get(char_sb.toString());
                    if (decodedChar == '\n') {
                        txt_sb.append('\n'); // 줄바꿈 문자 복원
                    }
                    else {
                        txt_sb.append(decodedChar); // 다른 문자 복원
                    }
                    char_sb.setLength(0);
                }
            }

            // 복호화된 데이터를 .unzip 파일에 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(unzipFilePath))) {
                writer.write(txt_sb.toString());
                writer.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("복호화 실패");
            e.printStackTrace();
        }


    }
    public String readUnzipFile() {
        StringBuilder stringBuilder = new StringBuilder(); // 파일 내용을 저장할 StringBuilder

        try (BufferedReader reader = new BufferedReader(new FileReader(unzipPath.toFile())))
        {
            int current_line;
            while ((current_line = reader.read()) != -1) {
                stringBuilder.append((char) current_line);  // 현재 라인을 StringBuilder에 추가
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 실패");
        }

        return stringBuilder.toString();  // StringBuilder를 String으로 반환
    }
}
