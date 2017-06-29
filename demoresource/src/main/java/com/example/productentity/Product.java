package com.example.productentity;
// Generated Jun 25, 2017 11:35:56 PM by Hibernate Tools 5.2.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "product", catalog = "product_info")
public class Product implements java.io.Serializable {

	private String id;
	private String name;
	private String description;
	private String language;
	private String writer;
	private Date dateOfPublish;
	private String companyName;

	public Product() {
	}

	public Product(String id, Date dateOfPublish) {
		this.id = id;
		this.dateOfPublish = dateOfPublish;
	}

	public Product(String id, String name, String description, String language, String writer, Date dateOfPublish,
				   String companyName) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.language = language;
		this.writer = writer;
		this.dateOfPublish = dateOfPublish;
		this.companyName = companyName;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 25)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "language", length = 19)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "writer")
	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_publish", nullable = false, length = 19)
	public Date getDateOfPublish() {
		return this.dateOfPublish;
	}

	public void setDateOfPublish(Date dateOfPublish) {
		this.dateOfPublish = dateOfPublish;
	}

	@Column(name = "company_name", length = 100)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}