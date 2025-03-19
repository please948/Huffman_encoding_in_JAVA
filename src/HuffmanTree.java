import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

class HuffmanNode implements Serializable {     //허프만 노드 클래스, 트리를 직렬화하여 압축파일에 저장하기 위해 Serializable
    char character; //알파뱃
    int frequency;  //빈도
    HuffmanNode left_child; //왼쪽 자식노드
    HuffmanNode right_child; // 오른쪽 자식노드

    public HuffmanNode(char character, int frequency) {     //생성자
        this.character = character;
        this.frequency = frequency;
        this.left_child = null;
        this.right_child = null;
    }
}

public class HuffmanTree {
    public  HuffmanNode huffman_tree;
    private Map<Character, Integer>frec_stat;
    String file_path;
    public  HuffmanTree(Map<Character, Integer> freq, String filePath){
        this.frec_stat = freq;
        this.file_path = filePath;
    }
    public  Map<Character, String> makingHuffmanCode() {

        Map<Character, Integer> freq_hashmap = FileManage.calcFreq();
        //빈도수의 오름차순을 우선순위로 하는 큐를 생성
        PriorityQueue<HuffmanNode> huffman_queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        //엔트리를 HuffmanNode로 변환 후 큐에 추가
        for (Map.Entry<Character, Integer> entry : freq_hashmap.entrySet()) {

            //엔트리의 알파뱃과 빈도 값을 가지는 허프만노드 생성
            huffman_queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        //허프만 트리 생성
        while (huffman_queue.size() > 1) {          //큐에 모든 알파뱃을 자식노드로 가지는 노드 하나만 남을 때 까지 반복
            HuffmanNode left = huffman_queue.poll();       //큐에서 가장 작은 값을 반환, 큐에서 제거
            HuffmanNode right = huffman_queue.poll();

            //left를 왼쪽 자식 노드, right를 오른쪽 자식 노드로 가지는 parent노드 생성
            HuffmanNode parent = new HuffmanNode('p', left.frequency + right.frequency);
            parent.left_child = left;
            parent.right_child = right;

            // parent 노드를 큐에 삽입
            huffman_queue.add(parent);
        }
        // 허프만 트리의 루트 노드
        HuffmanNode huffman_root = huffman_queue.poll();
        huffman_tree = huffman_root;

        //허프만 트리를 순회하며 리프 노드(알파뱃)에 허프만 코드를 제공하는 함수
        Map<Character, String> huffman_code_map = new HashMap<>();
        givingCode(huffman_root, "", huffman_code_map);


        return huffman_code_map;
    }
    public  HuffmanNode getHuffmanRoot() {
        return huffman_tree;
    }

    //허프만 트리(node)를 순회하며 리프 노드(알파뱃)에 허프만 코드를 제공하는 함수
    public  void givingCode(HuffmanNode node, String code, Map<Character, String> code_map) {
        if (node == null) {return;} //트리가 비어있는 경우

        //리프 노드일 경우 code_map의 해당 알파뱃에 허프만 코드 제공
        if (node.left_child == null && node.right_child == null) {
            code_map.put(node.character, code); // character를 char형으로 변환, 코드 제공
            return;
        }

        //리프 노드가 아닌 경우
        givingCode(node.left_child, code + "0", code_map);     //왼쪽으로 갈 때 0 추가
        givingCode(node.right_child, code + "1", code_map);   //오른쪽으로 갈 때 1 추가
    }
    public String codeToString() {
        StringBuilder encodedString = new StringBuilder();

        Map<Character, String> huffmanCodeMap = makingHuffmanCode();

        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String current_line;
            while ((current_line = reader.readLine()) != null) {
                for (char c : current_line.toCharArray()) {
                    // 각 문자를 허프만 코드로 바꾸고 빌더에 추가
                    encodedString.append(huffmanCodeMap.get(c));
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 실패");
        }

        return encodedString.toString();
    }
    public String getHuffmanCode() {    //문자 별 허프만 코드 반환하여 gui에 출력
        // 허프만 코드 맵 생성
        Map<Character, String> huffmanCodeMap = makingHuffmanCode();

        StringBuilder huff_code_sb = new StringBuilder();
        for (Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
            huff_code_sb.append(entry.getKey())
                        .append(":")
                        .append(entry.getValue())
                        .append("\n");
        }
        return huff_code_sb.toString();
    }

    public String getFreq() {       //빈도 수 반환하여 gui에 출력
        Map<Character, Integer> freqMap = FileManage.calcFreq();

        StringBuilder freq_sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            freq_sb.append(entry.getKey())
                    .append("의 빈도: ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return freq_sb.toString();
    }
}
