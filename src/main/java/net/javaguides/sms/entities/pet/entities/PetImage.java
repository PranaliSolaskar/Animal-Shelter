package net.javaguides.sms.entities.pet.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "TBL_PETS_IMAGES")
@Getter
@Setter
@NoArgsConstructor

public class PetImage implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sizes")
    private Integer id;
    
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pet_id")
    private Pet pet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
