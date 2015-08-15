package problems;

import java.util.*;

/**
 * Cat and Mouse Uva 274
 */
class Main
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
     * Output array
     */
    static List<String[]> output = new ArrayList<>();

    /**
     * Flag to detect if input for cat path is complete
     */
    boolean isCatInput = true;

    /**
     * Case counter
     */
    int count = -1;
    /**
     * List of rooms
     */
    List<Vertex> vertexLst = new ArrayList<>();
    /**
     * Main method to accept input
     * @param args String args
     */
    public static void main(String[] args)
    {
        Main cm = new Main();
        cm.processInput();
        for(int i=0; i<output.size(); )
        {
            System.out.println(output.get(i)[0] + " " + output.get(i)[1]);
            i++;
            if(i<output.size())
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
                } else if (source.colour == Colour.GREY) {
                    //Cycle exists, return
                    path.hasLoop = true;
                    if (checkForCyleOnly) {
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
            //Scanner scan = new Scanner(System.in);
            scan = new Scanner(System.in);
            int cases = 0;
            if(scan.hasNextLine()) {
                cases = Integer.parseInt(scan.nextLine().trim());
            }
            for(int i=0; i<cases; i++)
            {
                //Set all the initial input to N N
                output.add(new String[]{"N", "N"});
            }
            
            if(cases != 0) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if (line != null) {
                        if (line.isEmpty()) {
                            count++;
                            if ((count > 0) && count <= cases) {
                                processOutput(count - 1);
                                reset();
                                //This is not possible unless you are providing the input manually and
                                //not through a file input
                                if (count == cases) {
                                    break;
                                }
                            }
                        } else {
                            if (isCatInput) {
                                isCatInput = processCatLine(line);
                            } else {
                                processMouseLine(line, count);
                            }
                        }
                    }
                }
                processOutput(count);
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
     * @param count case count
     */
    void processOutput(int count)
    {
        if(!mouseRooms.isEmpty())
        {
            boolean hasLoop = constructPath(mouseRooms, mouseHome, true).hasLoop;
            if(hasLoop)
            {
                output.get(count)[1] = "Y";
            }
        }
    }

    /**
     * Reset input variables
     */
    void reset()
    {
        //Reset all the attributes for the next iteration.
        isCatInput = true;
        catRooms.clear();
        mouseRooms.clear();
        catPath.clear();
        catHome.index = 0;
        mouseHome.index = 0;
    }

    /**
     * Process cat line and construct the AdjList
     * @param line String input line to be processed
     * @return true to continue cat line processing. false to stop processing cat lines
     */
    boolean processCatLine(String line)
    {
        String[] arr = line.trim().split(" ");
        if(arr.length == 3)
        {
            int roomCnt = Integer.parseInt(arr[0]);
            vertexLst.add(0, null);
            for(int i=1; i<=roomCnt; i++)
            {
                //Create a list of Vertices(rooms)
                Vertex v = new Vertex();
                v.index = i;
                vertexLst.add(i, v);
            }
            catHome.index = Integer.parseInt(arr[1]);
            mouseHome.index = Integer.parseInt(arr[2]);
            if(catHome.index == mouseHome.index)
            {
            	output.get(count)[0] = "Y";	
            }
        }
        else
        {
            if(!arr[0].equals("-1"))
            {
                if(catRooms.containsKey(arr[0]))
                {
                    Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
                    catRooms.get(arr[0]).add(v);
                }
                else
                {
                    Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
                    Set<Vertex> set = new HashSet<>();
                    set.add(v);
                    catRooms.put(arr[0], set);
                }
            }
            else
            {
                //Cat input ends here. Process the cat input data and prepare for mouse input.
                if(!catRooms.isEmpty()) {
                    catPath = constructPath(catRooms, catHome, false).path;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Process mouse lines and construct the AdjList
     * @param line String line
     * @param count case count
     */
    void processMouseLine(String line, int count)
    {

        String[] arr = line.trim().split(" ");
        if(catPath.contains(arr[0])
                || catPath.contains(arr[1]))
        {
            output.get(count)[0] = "Y";
        }
        else
        {
            if(mouseRooms.containsKey(arr[0]))
            {
                Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
                mouseRooms.get(arr[0]).add(v);
            }
            else
            {
                Vertex v = vertexLst.get(Integer.parseInt(arr[1]));
                Set<Vertex> set = new HashSet<>();
                set.add(v);
                mouseRooms.put(arr[0], set);
            }
        }
    }
}