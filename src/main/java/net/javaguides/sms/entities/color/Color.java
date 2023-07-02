package net.javaguides.sms.entities.color;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import net.javaguides.sms.entities.pet.entities.Pet;

@Entity
@Table(name = "TBL_COLORS")
@Getter
@Setter
@NoArgsConstructor
public class Color implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    @Pattern(regexp = "[A-Za-z0-9#]*")
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private String hexCode;

    @Column(columnDefinition = "tinyint default 1")
    private Boolean status;

    @OneToMany(mappedBy="color", cascade = CascadeType.PERSIST)
    private Set<Pet> pets;

    public Color(String name, String hexCode) {
        this.name = name;
        this.hexCode = hexCode;
        this.status = true;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
