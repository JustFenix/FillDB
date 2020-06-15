
package at.fenix.filldb.persist;


public class Song {
    private String title;
        private int id;
	private String artist;
	private String year;
	private String album;
	private long lenght;
	private int bitrate;
	private String path;
	private String track;
	
	
	
	public Song() {
		super();
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public long getLenght() {
		return lenght;
	}
	public void setLenght(long lenght) {
		this.lenght = lenght;
	}
	public int getBitrate() {
		return bitrate;
	}
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

    @Override
    public String toString() {
        return "Song{" + "title=" + title + ", id=" + id + ", artist=" + artist + ", year=" + year + ", album=" + album + ", lenght=" + lenght + ", bitrate=" + bitrate + ", path=" + path + ", track=" + track + '}';
    }

    
}
