public class Main {

    static String str1 = "sex";
    static String str2 = "six";
    static int n = str1.length();
    static int m = str2.length();
    static int u = 1+Math.max(n,m);
    static int[][] r =new int[u][u];

    public static void main(String[] args) {



        System.out.println(EditDistance(str1, str2, 0, 0));

        for (int i = 0; i < u; i++) {
            for (int j = 0; j < u; j++) {
                System.out.print(r[i][j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        PrintS(r,0,0);
    }

    private static void PrintS(int[][] r,int i, int j) {
        if(i>=u || j>=u || i<0 || j<0){
            return;
        }
        if(r[i][j] == 9){
            System.out.println("nothing  ");
            PrintS(r,i+1,j+1);
        }
        if(r[i][j] == 2){
            System.out.println("Replace  ");
            PrintS(r,i+1,j+1);
        }
        if (r[i][j] == 1){
            System.out.println("Insert ");
            PrintS(r, i, j+1);
        }
        if (r[i][j] == -1){
            System.out.println("Delete ");
            PrintS(r,i+1,j);
        }
    }

    private static int EditDistance(String str1, String str2, int i, int j) {
        if(i>=n){
            for (int k = i; k < u-1; k++) {
                r[i][k]=1;

            }
            return m-j;
        }
        if(j>=m){

            for (int k = j; k < u-1; k++) {
                r[k][j]=-1;
            }

            return n-i;
        }

        //if the letters are like each other
        if(str1.charAt(i)==str2.charAt(j)){
            r[i][j]=9;
            return EditDistance(str1,str2,i+1,j+1);
        }

        //insert
        int q = 1 + EditDistance(str1,str2,i,j+1);
        //delete
        int p = 1 + EditDistance(str1,str2,i+1,j);
        //Replace
        int x = 1 + EditDistance(str1,str2,i+1,j+1);

        int min = Integer.MAX_VALUE;
        //delete
        if (p<q & p<x){
            min = p;
            r[i][j]= -1;
            return  min;
        }//insert
        else if (q<p & q<x) {
            min = q;
            r[i][j]=1;
            System.out.println("inserted");
            return  min;
        }
        //Replace
        else {
            min=x;
            r[i][j]=2;
            return  min;
        }


    }
}