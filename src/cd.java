
public class cd {
	private String Author;
	private String Voice;
	private String ID;
	private String Title;
	public cd(String Author,String Voice,String ID,String Title) {
		super();
		this.Author=Author;
		this.Voice=Voice;
		this.ID=ID;
		this.Title=Title;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getVoice() {
		return Voice;
	}
	public void setVoice(String voice) {
		Voice = voice;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
}
