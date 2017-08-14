package br.com.stock.object;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class SubCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 5)
	private int Id;
	
	@NotEmpty
	@Column(length = 45)
	private String name;
	
	@NotEmpty
	@Column(length = 200)
	private String description;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "CategoryId")
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
