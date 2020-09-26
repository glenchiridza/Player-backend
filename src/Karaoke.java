import com.glencconnect.KaraokeMachine;
import com.glencconnect.model.Song;
import com.glencconnect.model.SongBook;

public class Karaoke {
    public static void main(String[] args){
//        Song song = new Song (
//            "Glen Chiridza",
//            "I stand in |Awe",
//            "https://wwww/youtube.com/watch?v=SaECQ$"
//        );
        SongBook songBook =  new SongBook();
//        System.out.printf("Adding %s %n",song);
//        songBook.addSong(song);
//        System.out.printf("There are %d songs. %n",songBook.getSongCount());
        songBook.importFrom("Songs.txt");
        KaraokeMachine karaokeMachine = new KaraokeMachine(songBook);
        karaokeMachine.run();
        System.out.println("Saving song book...");
        songBook.exportTo("Songs.txt");
    }
}
