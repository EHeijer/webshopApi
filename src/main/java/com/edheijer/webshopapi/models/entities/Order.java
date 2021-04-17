package com.edheijer.webshopapi.models.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dateCreated;
	private Double orderSum;
	
	@Column(columnDefinition = "boolean default false")
	private boolean orderSent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("orders")
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
	private Set<Orderline> orderlines = new HashSet<>();
	
	public void addOrderline(Orderline orderline) {
		this.orderlines.add(orderline);
		orderline.setOrder(this);
	}

	public void removeOrderline(Orderline orderline) {
		this.orderlines.remove(orderline);
		orderline.setOrder(null);
	}
}
