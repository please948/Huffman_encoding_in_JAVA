import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
public class FileManage
{
    private static Path filePath; // 파일 경로 저장
    public FileManage(String filePath) throws IOException
    {
        this.filePath = Path.of(filePath); // 경로 설정
    }

    public String readFile() {
        StringBuilder stringBuilder = new StringBuilder(); // 파일 내용을 저장할 StringBuilder

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile())))
        {
            int currentLine;
            while ((currentLine = reader.read()) != -1) {
                stringBuilder.append((char) currentLine);  // 문자 그대로 추가
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 실패");
        }

        return stringBuilder.toString();  // StringBuilder를 String으로 반환
    }

    public static Map<Character, Integer> calcFreq()    //빈도수를 계산해서 해시맵에 넣고, 그 해시맵을 반환하여 HuffmanTree에서 사용
    {
        Map<Character, Integer> freq_hashmap = new HashMap<>();    //알파벳 별 빈도 해시맵

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile())))
        {

            int current_line;
            while ((current_line = reader.read()) != -1) { // 한 문자씩 읽기
                char alpha = (char) current_line;
                if (alpha == '\r') continue;    //캐리지 리턴 입력은 무시한다. (이미 '\n'입력을 카운트 했음)
                freq_hashmap.put(alpha, freq_hashmap.getOrDefault(alpha, 0) + 1); // 나머지 문자 빈도 증가
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 실패");
        }

        return freq_hashmap; // 문자 별 빈도수 해시맵 반환
    }
}
//해시맵을 통해 문자별 빈도 수 계산