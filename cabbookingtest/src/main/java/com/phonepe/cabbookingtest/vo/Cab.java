package com.phonepe.cabbookingtest.vo;

public class Cab {
	private String regId;
	private String ownerName;	
	public Cab(String regId, String ownerName) {
		super();
		this.regId = regId;
		this.ownerName = ownerName;
	}
	public String getRegId() {
		return regId;
	}
	public String getOwnerName() {
		return ownerName;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		result = prime * result + ((regId == null) ? 0 : regId.hashCode());
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
		Cab other = (Cab) obj;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		if (regId == null) {
			if (other.regId != null)
				return false;
		} else if (!regId.equals(other.regId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cab [regId=" + regId + ", ownerName=" + ownerName + "]";
	}
	
	
}
