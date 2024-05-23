public class Main {

    static String str1 = "abc";
    static String str2 = "adbc";
    static int n = str1.length();
    static int m = str2.length();

    public static void main(String[] args) {



        System.out.println(EditDistance(str1, str2, 1, 1));
    }

    private static int EditDistance(String str1, String str2, int i, int j) {
        if(i>=n){
            return m-j;
        }
        if(j>=m){
            return n-i;
        }

        //if the letters are like each other
        if(str1.charAt(i-1)==str2.charAt(j-1)){
            return EditDistance(str1,str2,i+1,j+1);
        }

        //insert
        int q = 1 + EditDistance(str1,str2,i,j+1);
        //delete
        int p = 1 + EditDistance(str1,str2,i+1,j);
        //Replace
        int x = 1 + EditDistance(str1,str2,i+1,j+1);

        int min = Integer.MAX_VALUE;
        if (p<q & p<x){
            min = p;
        } else if (q<p & q<x) {
            min = q;
        }
        else {
            min=x;
        }
        return min;

    }
}