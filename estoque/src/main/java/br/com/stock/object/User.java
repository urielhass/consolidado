package br.com.stock.object;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 5)
	private int Id;
	
//	@NotEmpty(message = "O campo nome é obrigatório.")
	@Column(length = 45)
	private String name;
	
	@Column(length = 13)
	private Long phone;

	//	@NotEmpty(message = "O campo email é obrigatório.")
	@Column(length = 100)
	private String email;
	
//	@NotEmpty(message = "O campo senha é obrigatório.")
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(length = 45)	
	private String password;
	
	private Boolean status;
	
	private Boolean adm;
	
	private int permission;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="userHasUnit", 
		joinColumns={@JoinColumn(name="userId")},
		inverseJoinColumns={@JoinColumn(name="unitId")})
	private List<Unit> units;
	
	@Transient
	private String token;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getAdm() {
		return adm;
	}

	public void setAdm(Boolean adm) {
		this.adm = adm;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Id != other.Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", name=" + name + ", phone=" + phone
				+ ", email=" + email + ", password=" + password + ", status="
				+ status + ", adm=" + adm + "]";
	}

}
