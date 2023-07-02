package net.javaguides.sms.entities.pet.entities;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import net.javaguides.sms.entities.character.Character;
import net.javaguides.sms.entities.color.Color;
import net.javaguides.sms.entities.size.Size;
import net.javaguides.sms.entities.user.User;
import net.javaguides.sms.entities.type.Type;

@Entity
@Table(name = "TBL_PETS")
@Getter
@Setter
@NoArgsConstructor

public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Integer id;

    @NotNull
    @NotBlank
    @javax.validation.constraints.Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(columnDefinition = "enum ('Cachorro/Cria', 'Joven', 'Adulto') default 'Cachorro/Cria'")
    private String age;

    @Column(nullable = false, columnDefinition = "tinyint")
    private Boolean gender;

    @Column(columnDefinition = "varchar(60)")
    private String breed;

    @javax.validation.constraints.Size(min = 2, max = 160)
    @Column(columnDefinition = "varchar(160)")
    private String description;

    @Column(name = "is_adopted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isAdopted;

    @Column(name = "is_dropped", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isDropped;

    @Column(name = "is_active", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToMany(mappedBy = "favoitesPets")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST)
    private Set<PetAdopted> pets;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST)
    private List<PetImage> images;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsAdopted() {
		return isAdopted;
	}

	public void setIsAdopted(Boolean isAdopted) {
		this.isAdopted = isAdopted;
	}

	public Boolean getIsDropped() {
		return isDropped;
	}

	public void setIsDropped(Boolean isDropped) {
		this.isDropped = isDropped;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Set<PetAdopted> getPets() {
		return pets;
	}

	public void setPets(Set<PetAdopted> pets) {
		this.pets = pets;
	}

	public List<PetImage> getImages() {
		return images;
	}

	public void setImages(List<PetImage> images) {
		this.images = images;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
