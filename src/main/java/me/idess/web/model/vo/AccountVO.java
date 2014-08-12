package me.idess.web.model.vo;

import java.io.Serializable;

public class AccountVO implements Serializable {
	private static final long serialVersionUID = 8325859627946261279L;
	
	private int seq;
	private String userid;
	private String password;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AccountVO [seq=" + seq + ", userid=" + userid + ", password="
				+ password + "]";
	}

}
