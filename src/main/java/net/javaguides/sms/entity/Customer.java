package net.javaguides.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Column(name = "cname", nullable = false)
    public String custname;
    
    @Column(name = "cusername", nullable = false)
    public String cusername;
    
    @Column(name = "phone_no")
    public String cphoneNumber;
    
    @Column(name = "cemail")
    public String cemail;
    
    @Column(name = "password")
    public String cpassword;
    
    @Column(name = "gender")
    public String cgender;
    
    @Column(name = "c_address")
    public String caddress;

	public Customer() {
		super();
	}

	public Customer(Long id, String custname, String cusername, String cphoneNumber, String cemail, String cpassword,
			String cgender, String caddress) {
		super();
		this.id = id;
		this.custname = custname;
		this.cusername = cusername;
		this.cphoneNumber = cphoneNumber;
		this.cemail = cemail;
		this.cpassword = cpassword;
		this.cgender = cgender;
		this.caddress = caddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCusername() {
		return cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getCphoneNumber() {
		return cphoneNumber;
	}

	public void setCphoneNumber(String cphoneNumber) {
		this.cphoneNumber = cphoneNumber;
	}

	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getCgender() {
		return cgender;
	}

	public void setCgender(String cgender) {
		this.cgender = cgender;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	
    // Constructors, getters, and setters
    
    
}