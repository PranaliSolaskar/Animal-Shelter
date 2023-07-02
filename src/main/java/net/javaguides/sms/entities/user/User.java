package net.javaguides.sms.entities.user;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import net.javaguides.sms.entities.log.Log;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import net.javaguides.sms.entities.donation.Donation;
import net.javaguides.sms.entities.news.News;
import net.javaguides.sms.entities.pet.entities.Pet;
import net.javaguides.sms.entities.pet.entities.PetAdopted;
import net.javaguides.sms.entities.profile.Profile;
import net.javaguides.sms.entities.role.Role;
import net.javaguides.sms.entities.request.Request;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    @Email
    @Column(unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100)
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String password;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean enabled;

    @Column(name = "link_restore_password",unique = true, columnDefinition = "varchar(150)")
    private String linkRestorePassword;

    @Column(name = "link_activate_username",unique = true, columnDefinition = "varchar(150)")
    private String linkActivateUsername;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @OneToOne(mappedBy = "user")
    private Request request;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<PetAdopted> adoptedPets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Pet> pets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<News> news;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Donation> donations;


    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Log> logs;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "TBL_FAVORITES_PETS",
            joinColumns = @JoinColumn(name = "id_pet"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<Pet> favoitesPets;

    public void addToFavorite(Pet pet) {
        favoitesPets.add(pet);
        pet.getUsers().add(this);
    }

    public void removeFromFavorite(Pet pet) {
        favoitesPets.remove(pet);
        pet.getUsers().remove(this);
    }
    @Valid
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles;


    public void addRole() {
        roles = new HashSet<>();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.roles = roles;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLinkRestorePassword() {
		return linkRestorePassword;
	}

	public void setLinkRestorePassword(String linkRestorePassword) {
		this.linkRestorePassword = linkRestorePassword;
	}

	public String getLinkActivateUsername() {
		return linkActivateUsername;
	}

	public void setLinkActivateUsername(String linkActivateUsername) {
		this.linkActivateUsername = linkActivateUsername;
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

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Set<PetAdopted> getAdoptedPets() {
		return adoptedPets;
	}

	public void setAdoptedPets(Set<PetAdopted> adoptedPets) {
		this.adoptedPets = adoptedPets;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public List<Pet> getFavoitesPets() {
		return favoitesPets;
	}

	public void setFavoitesPets(List<Pet> favoitesPets) {
		this.favoitesPets = favoitesPets;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	
}
