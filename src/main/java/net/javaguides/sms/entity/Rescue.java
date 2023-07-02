package net.javaguides.sms.entity;

import javax.persistence.*;


@Entity
@Table(name = "rescue")
public class Rescue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String fName;
	
	@Column(name = "phone_no")
	private String Phoneno;
	
	@Column(name = "animal_type")
	private String Atype;
	
	@Column(name = "no_ofa")
	private int No_ofa;
	
	@Column(name = "more_info")
	private String minfo;

	@Column(name = "address")
	private String address;
	public Rescue(Long id, String fName, String phoneno, String atype, int no_ofa, String minfo , String address) {
		super();
		this.id = id;
		this.fName = fName;
		Phoneno = phoneno;
		Atype = atype;
		No_ofa = no_ofa;
		this.minfo = minfo;
		this.address=address;
	}

	public Rescue() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getPhoneno() {
		return Phoneno;
	}

	public void setPhoneno(String phoneno) {
		Phoneno = phoneno;
	}

	public String getAtype() {
		return Atype;
	}

	public void setAtype(String atype) {
		Atype = atype;
	}

	public int getNo_ofa() {
		return No_ofa;
	}

	public void setNo_ofa(int no_ofa) {
		No_ofa = no_ofa;
	}

	public String getMinfo() {
		return minfo;
	}

	public void setMinfo(String minfo) {
		this.minfo = minfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
