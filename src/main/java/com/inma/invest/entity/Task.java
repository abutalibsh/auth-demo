package com.inma.invest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.inma.invest.domain.audit.UserDateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tasks")
@ToString
public class Task extends UserDateAudit implements Comparable<Task> {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 10485760, nullable = false)
	private String content;

	@Column(nullable = false)
	private String date;

	@Column(nullable = false)
	private String time;

	@Column(columnDefinition = "boolean default false")
	private boolean scheduled;

	@Column(columnDefinition = "boolean default false")
	private boolean executed;

	@Column(columnDefinition = "boolean default false")
	private boolean error;

	@Column(length = 10485760)
	private String img;

	@Column(nullable = false)
	private int timezoneOffset;

	@Column
	private float longitude;

	@Column
	private float latitude;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled;

	public Task(String content, String date) {
		this.content = content;
		this.date = date;
	}

	public int compareTo(Task post) {

		if (this.executed) {
			return 1;
		} else if (post.isExecuted()) {
			return -1;
		}

		StringBuilder time1 = new StringBuilder(post.time.replace(":", ""));
		StringBuilder time2 = new StringBuilder(this.time.replace(":", ""));

		while (time1.length() < 4) {
			time1.insert(0, "0");
		}

		while (time2.length() < 4) {
			time2.insert(0, "0");
		}

		String dateTime1 = post.date.replace("-", "") + time1.toString();
		String dateTime2 = this.date.replace("-", "") + time2.toString();

		return dateTime2.compareTo(dateTime1);

	}

	@Override
	public String toString() {
		return "Task{" + "id=" + id + '\'' + ", content='" + content + '\'' + ", date='" + date + '\'' + ", time='"
				+ time + '\'' + ", scheduled=" + scheduled + ", posted=" + executed + ", img='" + img + '\''
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", enabled=" + enabled + '}';
	}
}
