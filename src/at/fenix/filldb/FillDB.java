package at.fenix.filldb;

import at.fenix.filldb.persist.Song;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import at.fenix.filldb.persist.SongDao;

public class FillDB {

    static int countFiles = 0;
    static int countDirectories = 0;
    static ArrayList<Song> songList = new ArrayList<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        
        SongDao songDao = new SongDao();

        String path = "You haven't entered a path!";
        if (args.length > 0){
            path = args[0];
            System.out.println("");
            System.out.println("The following directory will be imported to the database: " +path );
        }else{
            System.out.println("");
            System.out.println(path);
            System.out.println("Please enter the path to your music directory.");
            System.out.println("[Example] c:\\user\\music");
            System.exit(0);
        }
               
        readMp3Files(path);
        System.out.println("File count: " + countFiles);
        System.out.println("Directory count: " + countDirectories);
        String result;

        long middle = System.currentTimeMillis();
       
        result = songDao.addSongs(songList);
        System.out.println("Database input: " + result);
        System.out.println("");

        long end = System.currentTimeMillis();
     
        System.out.println("Files processed: " + countFiles);
     
        System.out.println("Time to read " + (middle - start) / 1000 + " sec");
        System.out.println("Time to write " + (end - middle) / 1000 + " sec");
        System.out.println("Time total " + (end - start) / 1000 + " sec");
     
    }

    private static Song getOneSong(String path) {

        Song mySong = new Song();
        File myFile = new File(path);
        if (myFile.exists()) {
            for (File fileName : myFile.listFiles()) {
                if (fileName.isDirectory()) {
                    return null;
                } else if (fileName.isFile()) {
                    if (fileName.getName().endsWith("mp3")) {

                        try {
                            Mp3File mp3file = new Mp3File(fileName.getAbsolutePath());

                            ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                            mySong.setAlbum(id3v2Tag.getAlbum());
                            mySong.setArtist(id3v2Tag.getArtist());
                            mySong.setBitrate(mp3file.getBitrate());
                            mySong.setLenght(mp3file.getLength());
                            mySong.setPath(fileName.getAbsolutePath());
                            mySong.setTitle(id3v2Tag.getTitle());
                            mySong.setYear(id3v2Tag.getYear());
                            mySong.setTrack(id3v2Tag.getTrack());

                        } catch (UnsupportedTagException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvalidDataException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return mySong;
    }

    private static void readMp3Files(String path) {

        File myFile = new File(path);
        if (myFile.exists()) {
            for (File fileName : myFile.listFiles()) {
                if (fileName.isDirectory()) {
                    //System.out.println("Directory: " + fileName.getAbsolutePath());
                    countDirectories++;
                    readMp3Files(fileName.getAbsolutePath());
                } else if (fileName.isFile()) {
                    if (fileName.getName().endsWith("mp3")) {

                        readMp3TagFromFile(fileName.getAbsolutePath());
                      //  System.out.println("File: " + fileName.getName());
                        countFiles++;
                        if(countFiles % 100 == 0){
                            System.out.println(countDirectories + " directories with " +countFiles+ " files read");
                        }
                    }
                }
            }
        } else {
            System.out.println("Given path " + path + " doesn't exist.");
            System.out.println("");
        }

    }

    private static void readMp3TagFromFile(String absolutePath) {

        try {
            Mp3File mp3file = new Mp3File(absolutePath);
            Song mySong = new Song();

            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                mySong.setAlbum(id3v2Tag.getAlbum());
                mySong.setArtist(id3v2Tag.getArtist());
                mySong.setBitrate(mp3file.getBitrate());
                mySong.setLenght(mp3file.getLengthInSeconds());
                mySong.setPath(absolutePath);
                mySong.setTitle(id3v2Tag.getTitle());
                mySong.setYear(id3v2Tag.getYear());
                mySong.setTrack(id3v2Tag.getTrack());

                songList.add(mySong);
            } else if (mp3file.hasId3v1Tag()) {

                ID3v1 id3v1Tag = mp3file.getId3v1Tag();

                mySong.setAlbum(id3v1Tag.getAlbum());
                mySong.setArtist(id3v1Tag.getArtist());
                mySong.setBitrate(mp3file.getBitrate());
                mySong.setLenght(mp3file.getLengthInSeconds());
                mySong.setPath(absolutePath);
                mySong.setTitle(id3v1Tag.getTitle());
                mySong.setYear(id3v1Tag.getYear());
                mySong.setTrack(id3v1Tag.getTrack());
                

                songList.add(mySong);
            }

        } catch (UnsupportedTagException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
