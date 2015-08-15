package problems;

import java.util.*;
/**
 * Cat and Mouse Uva 274
 */
class CatAndMouse
{
    /**
     * AdjList of catRooms
     */
    Map<String, Set<Vertex>> catRooms = new HashMap<>();
    /**
     * AdjList of mouseRooms
     */
    Map<String, Set<Vertex>> mouseRooms = new HashMap<>();
    /**
     * Cat home
     */
    Vertex catHome = new Vertex();
    /**
     * Mouse home
     */
    Vertex mouseHome = new Vertex();
    /**
     * Possible cat path
     */
    Set<String> catPath = new HashSet<>();
    /**
     * Flag to check if cat is in the path of mouse
     */
    boolean catCheck = false;

    static int cases = 0;
    /**
     * Total room count
     */
    int roomCnt = 0;
    /**
     * List of rooms
     */
    List<Vertex> vertexLst = new ArrayList<>();


    static StringBuilder sb = new StringBuilder();

    static List<String[]> output = new ArrayList<>();
    /**
     * CatAndMouse method to accept input
     * @param args String args
     */
    public static void main(String[] args)
    {
        CatAndMouse cm = new CatAndMouse();
        cm.processInput();
//        if(sb.length() == 0)
//        {
//            for(int i=0; i<cases; i++)
//            {
//                sb.append("N N")
//                .append('\n')
//                .append("\n");
//                output.add(sb.toString());
//            }
//        }
        //System.out.println(sb.toString());
        for(int i=0; i<output.size(); i++)
        {
        	System.out.println(output.get(i)[0] + output.get(i)[1] + output.get(i)[2]);
        	if(i+1 != output.size())
        	System.out.println();
        }
    }
    /**
     * Enum to hold the colour. As part of DFS search
     */
    enum Colour
    {
        WHITE,
        GREY,
        BLACK;
    }
    /**
     * Vertex colour and index
     */
    class Vertex
    {
        int index;
        Colour colour;
    }
    /**
     * Path to store the list of path reachable and if there is a loop existing in between them
     */
    class Path
    {
        Set<String> path;
        boolean hasLoop;
    }

    /**
     * Method to construct the path.
     * @param roomList AdjList of rooms
     * @param source Source Room
     * @return Set of Vertex.
     */
    Path constructPath(Map<String, Set<Vertex>> roomList,
                       Vertex source, boolean checkForCyleOnly)
    {
        //Colour all white
        for(String key : roomList.keySet())
        {
            if(roomList.get(key) != null)
            {
                Iterator<Vertex> ite = roomList.get(key).iterator();
                while(ite.hasNext())
                {
                    Vertex v = ite.next();
                    v.colour = Colour.WHITE;
                }
            }
        }
        Path path = new Path();
        path.path = new HashSet<>();
        source.colour = Colour.WHITE;
        return dfs(roomList, source, path,checkForCyleOnly);
    }

    /**
     * DFS search to fetch the path from the home room
     * @param roomList AdjList of roomList
     * @param source Start room
     * @param path Path of rooms that can be visited
     * @return Set of rooms visitable.
     */
    Path dfs(Map<String, Set<Vertex>> roomList,
             Vertex source, Path path, boolean checkForCyleOnly)
    {
        source.colour = Colour.GREY;
        Set<Vertex> children = roomList.get(String.valueOf(source.index));
        if(children != null) {
            Iterator<Vertex> ite = children.iterator();
            while (ite.hasNext()) {
                Vertex child = ite.next();
                if (child.colour == Colour.WHITE) {
                    dfs(roomList, child, path, checkForCyleOnly);
                    if (path.hasLoop && checkForCyleOnly) {
                        break;
                    }
                }
                else if (child.colour == Colour.GREY)
                {
                    if(checkForCyleOnly) {
                        if ((child.index == mouseHome.index))
                            //Cycle exists, return
                            path.hasLoop = true;
                            break;
                    }
                }
            }
        }
        source.colour = Colour.BLACK;
        path.path.add(String.valueOf(source.index));
        return path;
    }
    /**
     * Method accepts inputs from the user and invokes appropriate method.
     */
    void processInput()
    {
        Scanner scan = null;
        
        try
        {
            scan = new Scanner(System.in);
            String line = "";
            if(scan.hasNextLine())
                cases = Integer.parseInt(scan.nextLine().trim());
            if(scan.hasNextLine())
                scan.nextLine(); //ignore blank
            for(int i=1; i<= cases; i++)
            {
                if(scan.hasNextLine())
                    line = scan.nextLine().trim();
                String[] init = line.split(" ");
                roomCnt = Integer.parseInt(init[0]);
                vertexLst.add(0, null);
                for(int r = 1; r <= roomCnt; r++){
                    Vertex v = new Vertex();
                    v.index = r;
                    vertexLst.add(r, v);
                }
                catHome = vertexLst.get(Integer.parseInt(init[1]));
                mouseHome = vertexLst.get(Integer.parseInt(init[2]));
                boolean endOfCase = false;
                if(catHome.index == mouseHome.index){
                    catCheck = true;
                }
                while(scan.hasNextLine()){
                    line = scan.nextLine().trim();
                    if(line.isEmpty()){
                        endOfCase = true;
                        buildOutput(false);
//                        if(i < cases)
//                            sb.append("\n");
                        break;
                    }
                    else if(!(line.contains("-1"))){
                        String[] arr = line.split(" ");
                        createRooms(arr, catRooms);
                    }
                    else{
                        break;
                    }
                }
                if(!endOfCase) {
                    //Construct catPath
                    Path cPath = constructPath(catRooms, catHome, false);
                    catPath = cPath.path;
                    while (scan.hasNextLine() && !((line = scan.nextLine().trim()).isEmpty())) {
                        String[] arr = line.split(" ");
                        if (catPath.contains(arr[0]) || catPath.contains(arr[1])) {
                            catCheck = true;
                        } else {
                            createRooms(arr, mouseRooms);
                        }
                    }
                    catCheck = catCheck? catCheck : (catPath.contains(String.valueOf(mouseHome.index)));
                    //Construct mousePath
                    Path mPath = constructPath(mouseRooms, mouseHome, true);
                    buildOutput(mPath.hasLoop);
//                    if(i < cases)
//                        sb.append("\n");
                }
                //reset class member variables
                reset();
            }
            scan.close();
        }
        catch (Exception e)
        {
            if(scan != null) {
                scan.close();
            }
        }
        finally {
            if(scan != null) {
                scan.close();
            }
        }
    }

    /**
     * Construct output
     * @param hasCycle
     */
    void buildOutput(boolean hasCycle)
    {
    	//String s = ((catCheck? 'Y' : 'N') + ' ' + (hasCycle ? 'Y' : 'N') + '\n'); 
    	//output.add((catCheck? 'Y' : 'N') + ' ' + (hasCycle ? 'Y' : 'N') + '\n');
    	
    	String s1 = (catCheck? "Y" : "N");
    	String s2 = " ";
    	String s3 = (hasCycle ? "Y" : "N");
    	String[] arr = new String[3];
    	arr[0] = s1;
    	arr[1] = s2;
    	arr[2] = s3;
    	output.add(arr);
    }

    /**
     * Create rooms
     *
     * @param arr
     * @param room
     */
    void createRooms(String[] arr, Map<String, Set<Vertex>>room)
    {
        if(room.containsKey(arr[0]))
        {
            Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
            room.get(arr[0]).add(v);
        }
        else
        {
            Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
            Set<Vertex> set = new HashSet<>();
            set.add(v);
            room.put(arr[0], set);
        }
    }
    /**
     * Reset input variables
     */
    void reset()
    {
        //Reset all the attributes for the next iteration.
        catRooms.clear();
        mouseRooms.clear();
        catPath.clear();
        catHome.index = 0;
        catHome.colour = null;
        mouseHome.index = 0;
        mouseHome.colour = null;
        catCheck = false;
        roomCnt = 0;
        vertexLst.clear();
    }
}