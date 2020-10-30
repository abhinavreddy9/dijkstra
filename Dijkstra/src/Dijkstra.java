import java.util.Scanner;
class Dijkstra
{
    static void initialArray(int[][] node,int numberOfNodes)
    {
        for(int i=0;i<numberOfNodes;i++)
        {
            for(int j=0;j<numberOfNodes;j++)
            {
                if(i==j)
                    node[i][j]=0;  // setting the distance to same node as 0
                else
                    node [i][j]=100000; // setting initial infinite value
            }
        }
    }

    static void Relaxation(int sourcePos,int x,int[][] nodeRepeat,int[][] node)
    {
        int next=0; // initializing next node to 0
        int last=0;
        for(int i=0;i<x-1;i++)
        {
            int min=100000; // setting a very high default value
            for(int j=0;j<x;j++)
            {
                if(j==sourcePos || node[sourcePos][j]<=last)
                    continue;
                if(min>node[sourcePos][j])
                {
                    min=node[sourcePos][j];
                    next=j;
                }
            }
            last=min;

            nodeRepeat[i][0]=next;
            for(int j=0;j<x;j++)
            {
                nodeRepeat[i][j+1]=node[sourcePos][j];

            }


            // actual relaxation step
            for(int z=0;z<x;z++)
            {
                if(node[sourcePos][z] > node[sourcePos][next]+node[next][z])
                    node[sourcePos][z]=node[sourcePos][next]+node[next][z];
            }
        }
    }


    static int a=0;
    static int setValues(String name,String[] stringArray)
    {
        int x=-1;
        for(int i=0;i<stringArray.length && stringArray[i]!=null;i++)
        {
            if(name.equals(stringArray[i]))
            {
                x=i;
                break;
            }
        }
        if(x==-1)
        {
            stringArray[a]=name;
            x=a;
            a++;
        }
        return x;
    }
    static int path(int sourcePos,int destPos,int[] order,int[][] nodeRepeat,int a)
    {

        order[0]=destPos;
        int pos=1;
        int backTrack=destPos;
        for(int i=a-2;i>0;i--)
        {
            if(nodeRepeat[i][backTrack+1]==nodeRepeat[i-1][backTrack+1])
                continue;

            else
            {
                order[pos]=nodeRepeat[i-1][0];
                pos++;
                backTrack=nodeRepeat[i-1][0];
            }
        }
        order[pos]=sourcePos;
        return pos;
    }


    public static void main (String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of nodes:");
        int numberOfNodes=sc.nextInt();  // n is the number of nodes


        System.out.println("Enter the number of arcs: ");
        int p = sc.nextInt(); // p is the number of edges

        System.out.println("Enter the Source node:");
        String startPoint=sc.next(); //startPoint stores the Source in the graph

        System.out.println("Enter the Destination node:");
        String endPoint=sc.next(); //endPoint store the destination in the graph

        String[] allNodes = new String[numberOfNodes];
        int[][] node = new int[numberOfNodes][numberOfNodes]; //two dimensional array to store the distances

        initialArray(node,numberOfNodes); // setting base by making everything 1000000

        System.out.println("Enter all the arc lengths(in the form-> 'Node1' 'Node2' 'Length')");
        String src,dest;
        int dist,source,destination;
        int m=0;
        while(m<p)
        {
            src=sc.next();
            dest= sc.next();
            dist=sc.nextInt();
            source=setValues(src,allNodes);
            destination=setValues(dest,allNodes);
            node[source][destination]=dist;
            node[destination][source]=dist;
            m++;

        }

        // finds the position of Source and Destination in the graph
        int sourcePos=setValues(startPoint,allNodes);
        int destPos=setValues(endPoint,allNodes);
        System.out.println();

        int[][] nodeRepeat = new int[numberOfNodes-1][numberOfNodes+1]; //stores each iteration
        Relaxation(sourcePos,numberOfNodes,nodeRepeat,node);


        int[] order= new int[numberOfNodes]; //stores the order to find shortest path
        int pos=path(sourcePos,destPos,order,nodeRepeat,numberOfNodes); //stores the shortest path in array order and returns the element counts in it

        System.out.println("\nShortest distance between "+startPoint+" and "+endPoint+" => "+ node [sourcePos][destPos]);
        System.out.println();
        System.out.print("The Shortest path is ");
        for(int i=pos;i>-1;i--)
        {
            System.out.print(allNodes[order[i]]); // prints the nodes in the path
            if(i<=0)
                continue;
            System.out.print("-->");
        }
        sc.close();
        System.out.println();
    } // end of main
}
