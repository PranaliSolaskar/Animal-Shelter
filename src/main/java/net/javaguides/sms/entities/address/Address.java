package net.javaguides.sms.entities.address;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import net.javaguides.sms.entities.profile.Profile;

@Entity
@Table(name = "TBL_ADDRESS")
@Setter
@Getter
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-./#]*")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String street;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    @Pattern(regexp = "[0-9]*")
    @Column(name="external_number", nullable = false, columnDefinition = "varchar(5)")
    private String externalNumber;

    @Pattern(regexp = "[0-9]*")
    @Column(name="internal_number", columnDefinition = "varchar(5)")
    private String internalNumber;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    @Pattern(regexp = "[0-9]*")
    @Column(name="zip_code", nullable = false, columnDefinition = "varchar(50)")
    private String zipCode;

    @Pattern(regexp = "[A-Za-zÀ-ÿ '-./#]*")
    @Column(name="references_street", columnDefinition = "varchar(128)")
    private String references;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "address")
    private Profile profile;

    public Address() {
    }

    public Address(String street, String externalNumber, String internalNumber, String zipCode, String references) {
        this.street = street;
        this.externalNumber = externalNumber;
        this.internalNumber = internalNumber;
        this.zipCode = zipCode;
        this.references = references;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getExternalNumber() {
		return externalNumber;
	}

	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}

	public String getInternalNumber() {
		return internalNumber;
	}

	public void setInternalNumber(String internalNumber) {
		this.internalNumber = internalNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
