package com.inma.invest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskDto implements Comparable<TaskDto> {

	private long id;

	private String content;

	private String date;

	private String time;

	private boolean scheduled;

	private boolean executed;

	private boolean error;

	private String img;

	private int timezoneOffset;

	private float longitude;

	private float latitude;

	private boolean enabled;

	public TaskDto(String content, String date) {
		this.content = content;
		this.date = date;
	}

	public int compareTo(TaskDto post) {

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
