package com.phonepe.cabbookingtest.vo;

public class CabBookingHistory {
	private Long id;
	private Cab can;
	private City from;
	private City destination;
	public CabBookingHistory(Long id, Cab can, City from, City destination) {
		super();
		this.id = id;
		this.can = can;
		this.from = from;
		this.destination = destination;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cab getCan() {
		return can;
	}
	public void setCan(Cab can) {
		this.can = can;
	}
	public City getFrom() {
		return from;
	}
	public void setFrom(City from) {
		this.from = from;
	}
	public City getDestination() {
		return destination;
	}
	public void setDestination(City destination) {
		this.destination = destination;
	}
	@Override
	public String toString() {
		return "CabBookingHistory [id=" + id + ", can=" + can + ", from=" + from + ", destination=" + destination + "]";
	}		
}
