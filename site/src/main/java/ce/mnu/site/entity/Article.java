package ce.mnu.site.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long num;
	
	@Column(length = 20, nullable = false)
	private String author;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(nullable = false, length = 2048)
	private String body;

	@Column
	private LocalDateTime time = LocalDateTime.now();

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
