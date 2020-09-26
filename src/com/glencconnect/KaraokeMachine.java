package com.glencconnect;

import com.glencconnect.model.Song;
import com.glencconnect.model.SongBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KaraokeMachine {
    private SongBook songBook;
    private BufferedReader reader;
    private Map<String,String> menu;
    private Queue<Song> songQueue;
    public KaraokeMachine(SongBook songBook){
        this.songBook = songBook;
        reader = new BufferedReader(new InputStreamReader(System.in));
        songQueue = new ArrayDeque<>();
        menu = new HashMap<>();
        menu.put("add","add a new song");
        menu.put("play","play next song");
        menu.put("choose","choose a song");
        menu.put("quit","exit program");
    }

    private String promptAction() throws IOException {
        System.out.printf("There are %d songs available and %d in queue." +
                "Your options are %n",songBook.getSongCount(),songQueue.size());
        for (Map.Entry<String,String> options: menu.entrySet()){
            System.out.printf("%s -- %s %n",options.getKey(),options.getValue());
        }
        System.out.printf("What do you want to do: ");
        String choice = reader.readLine();
        return choice.trim().toLowerCase();

    }

    public void run(){
        String choice ="";
        do{
            try{
                choice = promptAction();
                switch (choice){
                    case "add":{
                        //add new song
                        Song song = pronptNEwSong();
                        songBook.addSong(song);
                        System.out.printf("%s added %n5n5n", song);
                        break;
                    }
                    case "play":{
                        playNext();
                        break;
                    }
                    case "choose":{
                        String artist = promptArtist();
                        Song artistSong = promptSongForArtist(artist);
                        songQueue.add(artistSong);
                        System.out.println("you chose "+artistSong);
                        break;
                    }
                    case "quit":{
                        System.out.println("Well goodbye ");
                        break;
                    }
                    default:
                        System.out.printf("Unknown %s choice. Try Again. %n%n%n",
                                choice);
                }
            }catch(IOException ioe){
                System.out.println("problem with input!!!");
                ioe.printStackTrace();
            }
        }while (!choice.equals("quit"));
    }

    private Song promptSongForArtist(String artist) throws IOException{
        List<Song> songs = songBook.getSongsForArtists(artist);
        List<String> songTitle = new ArrayList<>();
        for (Song song: songs){
            songTitle.add(song.getTitle());
        }
        System.out.println("Available songs for "+artist);
        int index =promptIndex(songTitle);
        return songs.get(index);
    }
    private Song pronptNEwSong() throws IOException{
        System.out.println("Enter artist nmae:   ");
        String artist = reader.readLine();
        System.out.println("Enter title:   ");
        String title = reader.readLine();
        System.out.println("Enter video url:  ");
        String videoUrl = reader.readLine();
        return new Song(artist,title,videoUrl);

    }
    private String promptArtist() throws IOException{
        System.out.println("Available Artists: ");
        List<String> artists = new ArrayList<>(songBook.getArtists());
        int index = promptIndex(artists);
        return artists.get(index);
    }
    private int promptIndex(List<String> options) throws IOException{
        int counter =1;
        for (String option: options){
            System.out.printf("%d) %s %n",counter,option);
            counter++;
        }
        System.out.println("Your choice:   ");
        String optionAsString =reader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice-1;
    }
    public void playNext(){
        Song song = songQueue.poll();
        if(song ==null){
            System.out.printf("Sorry no songs io queue, use menu to add %n%n%n");
        }else{
            System.out.printf("%n%n%n Open %s to hear %s by %s %n%n%n",
                    song.getVideoUrl(),
                    song.getTitle(),
                    song.getArtist());
        }
    }
}
