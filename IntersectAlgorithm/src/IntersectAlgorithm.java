import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class IntersectAlgorithm {
    public static List<Integer> returnWordsIndex(String word,List<String> filesData){
        List<Integer> wordIndex = new ArrayList<Integer>();

        for (int i=0; i<filesData.size();i++)
        {
            if (filesData.get(i).contains(word))
            {
             wordIndex.add(i+1);
            }

        }
        return wordIndex;
    }
    public static String readFile (String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner fileReader = new Scanner(file);
        String fileData = "";
        while (fileReader.hasNextLine())
        {
            fileData += fileReader.nextLine();
            fileData  += "\n";
        }
        return fileData.toLowerCase();
    }
    public static List<List<Integer>> getAllWordsIndex(String[] words,List<String> filesData)
    {

        List<List<Integer>> allIndexes = new ArrayList<>();
        for (int i=0; i< words.length;i++)
        {
            List <Integer> index = returnWordsIndex(words[i],filesData);
            allIndexes.add(index);
        }
        return allIndexes;
    }
    public static List<Integer> and(List<Integer> wordA, List<Integer> wordB)
    {
        List <Integer> answer = new ArrayList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1!= wordA.size() & p2!= wordB.size())
        {
            if (wordA.get(p1).equals(wordB.get(p2)))
            {
                answer.add(wordA.get(p1));
                p1++;
                p2++;
            }
            else if (wordA.get(p1)<wordB.get(p2))
                p1++;
            else
                p2++;
        }
        /*wordA.retainAll(wordB);
        answer=wordA;*/
        return answer;
    }
    public static List<Integer> or(List<Integer> wordA, List<Integer> wordB)
    {
        List <Integer> answer = new ArrayList<>();
        answer = wordA;
        for (int i=0; i< wordB.size(); i++)
        {
            if(!answer.contains(wordB.get(i)))
            {
                answer.add(wordB.get(i));
            }

        }
        return answer;
    }
    public static List<Integer> not(List<Integer> wordA,List<Integer> wordB)
    {
        List <Integer> answer = new ArrayList<>();
        answer = wordA;
        for (int i=0; i< wordB.size(); i++)
        {
            if(answer.contains(wordB.get(i)))
            {
                answer.remove(wordB.get(i));
            }

        }
        return answer;
    }
    public static List<Integer> algorithm(String[] words,List<String> operations, List<List<Integer>> allIndexes)
    {
        List <Integer> tempAnswer = new ArrayList<>();
        int j=0;
        tempAnswer= allIndexes.get(0);
        for (int i=0; i< operations.size();i++)
        {
            if (operations.get(i).equals("and"))
            {
                tempAnswer = and(tempAnswer,allIndexes.get(i+1));
            }
            else if (operations.get(i).equals("or"))
            {
                tempAnswer = or(tempAnswer,allIndexes.get(i+1));
            }
            else if (operations.get(i).equals("not"))
            {
                tempAnswer = not(tempAnswer,allIndexes.get(i+1));

            }


        }
        List<Integer> answer = new ArrayList<>(tempAnswer);
        return answer;
    }
    public static void main(String[] args) throws FileNotFoundException {
        //query input
        System.out.println("Enter your Query:");
        Scanner input = new Scanner(System.in);
        String queryInput = input.nextLine();
        String query = queryInput.toLowerCase();
        //words splitting
        /**WORDs**/
        String[] words = query.split("and|or|not");

        System.out.println("Num of words : "+ words.length);
        System.out.println("Words in the query: ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("\\s+","");
            System.out.println(words[i]);

        }
        //operands extracting
        String[] tempQuery = query.split("\\s+");
        /**OPERATIONS**/
        List<String> operations = new ArrayList<>();
        for (int i = 0; i < tempQuery.length; i++) {
            if (tempQuery[i].equals("and") | tempQuery[i].equals("or") | tempQuery[i].equals("not")) {
                operations.add(tempQuery[i]);
            }
        }
        System.out.println("operations used : ");
        for (int i = 0; i < operations.size(); i++) {
            System.out.println(operations.get(i));

        }
        //files reading
        List<String> files = new ArrayList<String>();
        files.add(readFile("src/file1.txt"));
        files.add(readFile("src/file2.txt"));
        files.add(readFile("src/file3.txt"));

        //index finder
        /**Each WORD INDEX**/

        List<List<Integer>> allIndexes = new ArrayList<>();

        allIndexes = getAllWordsIndex(words,files);
        System.out.println("Words index: ");
        System.out.println(allIndexes.toString());
        List<Integer> a = algorithm(words,operations,allIndexes);
        System.out.println("Query answer : ");
        System.out.println(a.toString());

        /**TEST CASES**/
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("TEST CASE 1 : ");
        /**TEST CASE 1 : Sarah found in doc 3 while ehab found in doc 1 and 2 and 3 ====> exp answer 1,2,3 **/

        String[] words1 = {"sarah", "ehab"};
        List<String> operations1 = new ArrayList<>();
        operations1.add("or");
        List<List<Integer>> allIndexes1 = new ArrayList<>();
        allIndexes1 = getAllWordsIndex(words1,files);
        List<Integer> answer1 = algorithm(words1,operations1,allIndexes1);
        System.out.println(answer1);

        /**TEST CASE 2 : Sarah found in doc 3 while ehab found in doc 1 and 2 and 3 ====> exp answer 3 **/
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("TEST CASE 2 : ");
        String[] words2 = {"sarah", "ehab"};
        List<String> operations2 = new ArrayList<>();
        operations2.add("and");
        List<List<Integer>> allIndexes2 = new ArrayList<>();
        allIndexes2 = getAllWordsIndex(words2,files);
        List<Integer> answer2 = algorithm(words2,operations2,allIndexes2);
        System.out.println(answer2);

        /**TEST CASE 3 : Sarah found in doc 3 while ehab found in doc 1 and 2 and 3 ====> exp answer NULL **/
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("TEST CASE 2 : ");
        String[] words3 = {"sarah", "ehab"};
        List<String> operations3 = new ArrayList<>();
        operations3.add("not");
        List<List<Integer>> allIndexes3 = new ArrayList<>();
        allIndexes3 = getAllWordsIndex(words3,files);
        List<Integer> answer3 = algorithm(words3,operations3,allIndexes3);
        System.out.println(answer3);



    }
}
