package com.phonepe.cabbookingtest.vo;

import java.time.LocalDateTime;

public class CabStatus implements Cloneable{
	private static City unknown = new City("Unknown..in transit");
	private Cab cab;
	private Status status;
	private City location;
	private LocalDateTime statusTime;
	public CabStatus(Cab cab, Status status, City location,LocalDateTime statusTime) {
		super();
		this.cab = cab;
		this.status = status;
		this.location = location;
		this.statusTime = statusTime;
	}
	
	public CabStatus(Cab cab) {
		super();
		this.cab = cab;
	}

	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public City getLocation() {
		return location;
	}

	public void setLocation(City location) {
		this.location = location;
	}

	public LocalDateTime getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(LocalDateTime statusTime) {
		this.statusTime = statusTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cab == null) ? 0 : cab.hashCode());
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
		CabStatus other = (CabStatus) obj;
		if (cab == null) {
			if (other.cab != null)
				return false;
		} else if (!cab.equals(other.cab))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "<p>CabStatus [cab=" + cab + ", status=" + status + ", location=" + location + ", statusTime=" + statusTime
				+ "]";
	}
	
	
	
}
