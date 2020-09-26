package com.glencconnect.model;

import java.io.*;
import java.util.*;

public class SongBook {

    private List<Song> songs;
    public SongBook(){
        songs = new ArrayList<>();
    }
    public void exportTo(String filename){
        try(
                FileOutputStream fos = new FileOutputStream(filename);
                PrintWriter printWriter = new PrintWriter(fos);
                ){

            for (Song song: songs){
                printWriter.printf("%s|%s|%s%n",
                        song.getArtist(),
                        song.getTitle(),
                        song.getVideoUrl());
            }
        }catch (IOException ioe){
            System.out.printf("problem saving %s",filename);
            ioe.printStackTrace();
        }
    }
    public void importFrom(String filename){
        try(
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis))
            ){
            String line;
            while((line = reader.readLine())!=null){
               String[] args= line.split("\\|");
               addSong(new Song(args[0],args[1],args[2]));
            }

        }catch (IOException ioe){
            System.out.printf("problem saving %s",filename);
            ioe.printStackTrace();

        }
    }

    public void addSong(Song song){
        songs.add(song);
    }

    public int getSongCount(){
        return songs.size();
    }

    private Map<String,List<Song>> byArtist(){
        Map<String,List<Song>> byArtsist = new TreeMap<>();
        for (Song sang: songs){
            List<Song> artistSongs = byArtsist.get(sang.getArtist());
            if(artistSongs ==null){
                artistSongs = new ArrayList<>();
                byArtsist.put(sang.getArtist(),artistSongs);
            }
            artistSongs.add(sang);
        }
        return byArtsist;
    }
    public Set<String> getArtists(){
        return byArtist().keySet();
    }
    public List<Song> getSongsForArtists(String artistName){
        List<Song> songs=  byArtist().get(artistName);
        songs.sort((Comparator<Song>) (song1, song2) -> {
            if (song1.equals(song2)){
                return 0;
            }
            return song1.getTitle().compareTo(song2.getTitle());

        });
        return songs;
    }
}
