package main;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list of maps from node to weight
     */
    public static List<Map<Integer, Integer>> parse(int[][] adjMatrix) {

        ArrayList<Map<Integer, Integer>> listOfMaps = new ArrayList<>();
        for (int i = 0; i < adjMatrix.length; i++){
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = 0; j < adjMatrix[i].length; j++){
                if (adjMatrix[i][j] != Integer.MAX_VALUE){
                    map.put(j, adjMatrix[i][j]);
                }
            }
            listOfMaps.add(map);
        }
        //System.out.println(listOfMaps);
        return listOfMaps;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list of maps from node to weight with incoming edges
     */
    public static List<Map<Integer, Integer>> parseInverse(int[][] adjMatrix) {
        ArrayList<Map<Integer, Integer>> listIncoming = new ArrayList<>();
        for (int i = 0; i < adjMatrix.length; i++){
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = 0; j < adjMatrix[i].length; j++){
                if (adjMatrix[j][i] != Integer.MAX_VALUE){
                    map.put(j, adjMatrix[j][i]);
                }
            }
            listIncoming.add(map);
        }
        return listIncoming;
    }
}