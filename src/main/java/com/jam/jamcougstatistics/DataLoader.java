package com.jam.jamcougstatistics;

import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader{
    private File mFile;
    public DataLoader(File infile){
        this.mFile = infile;
    }

    public boolean IsValid() throws FileNotFoundException {
        InputStream ifstream = new FileInputStream(this.mFile);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        for (Object line : reader.lines().toArray()){
            String l = line.toString();
            if (l.split(",").length > 2){
                return false;
            }
            for (String entry : l.split(",")){
                if(entry == ""){
                    continue;
                }
                try {
                    Integer.decode(entry);
                }
                catch (Exception e){
                    return false;
                }
            }
        }
        return true;
    }
    //Assume that stream has been validated
    public int CountSets() throws FileNotFoundException {
        InputStream ifstream = new FileInputStream(this.mFile);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        int max = 0;
        for (Object line : reader.lines().toArray()){
            String l = line.toString();
            int len = l.split(",").length;
            if (len > max) max = len;

        }
        return max;
    }

    public ArrayList<Integer> LoadSingleDataSet() throws Exception {
        InputStream ifstream = new FileInputStream(this.mFile);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        ArrayList<Integer> ret = new ArrayList<>();
        for (Object line : reader.lines().toArray()){
            String l = line.toString();
            try{
                ret.add(Integer.parseInt(l));
            }
            catch(Exception e){
                throw new Exception();
            }


        }
        return ret;
    }

    public ArrayList<Integer>[] LoadDoubleDataSet() throws Exception{
        InputStream ifstream = new FileInputStream(this.mFile);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        ArrayList<Integer>[] ret = new ArrayList[2];
        ret[0] = new ArrayList<Integer>();
        ret[1] = new ArrayList<Integer>();
        for (Object line : reader.lines().toArray()){
            String[] l = line.toString().split(",");
            if (l.length != 2) throw new Exception();
            for(int i = 0; i < l.length; i++){
                if (l[i] == ""){
                    continue;
                }
                ret[i].add(Integer.parseInt(l[i]));
            }
        return ret;


        }
        return ret;

    }
}