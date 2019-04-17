package com.suhas.cs6200.hackr.driver;

import com.suhas.cs6200.hackr.index.IndexBuilder;
import com.suhas.cs6200.hackr.utils.Logger;

import java.util.List;

/**
 * Driver Class to run the program and evaluate queries
 *
 * @author suhas
 */
public class Driver {

    /**
     * Main function
     * Builds the inverted index and executes all the queries from the query file
     *
     * @param args <Directory of AP_DATA folder> <Path to queries file>
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            Logger.log("Usage java com/suhas/cs6200/driver/Driver <Directory of AP_DATA folder> <Solr collection Name>");
            return;
        }

        String path = args[0];

        String collectionName = args[1];


        Logger.log("Building Index...");

        IndexBuilder idxBuilder = new IndexBuilder();



        idxBuilder.buildIndex(path, collectionName);
        List<String> res;

    }
}
