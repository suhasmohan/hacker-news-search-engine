package com.suhas.cs6200.hackr.tokenizer;

import com.google.gson.Gson;
import org.apache.solr.common.SolrInputDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    /**
     * Tokenizes a File and returns a list of tuples of the format: (TermId,
     * DocumentId, Position)
     *
     * @param file
     * @return List of Tuples
     * @throws IOException
     */
    public List<SolrInputDocument> tokenizeStories(File file) throws IOException {
        List<SolrInputDocument> docList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        Gson g = new Gson();

        while ((line = br.readLine()) != null) {

            Story story = g.fromJson(line, Story.class);
            if(!story.isDead() && !story.isDeleted() && story.time_ts != null) {
                SolrInputDocument doc = story.toSolrDoc();

                docList.add(doc);
            }
        }
        br.close();
        return docList;
    }

    public List<SolrInputDocument> tokenizeComments(File file) throws IOException {
        List<SolrInputDocument> docList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        Gson g = new Gson();

        while ((line = br.readLine()) != null) {

            Comment comment = g.fromJson(line,Comment.class);
            if(!comment.isDead() && !comment.isDeleted() && comment.time_ts != null) {
                SolrInputDocument doc = comment.toSolrDoc();

                docList.add(doc);
            }
        }
        br.close();
        return docList;
    }
}
