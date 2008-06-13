package playground.mmoyo.pttest;

import org.matsim.basic.v01.IdImpl;
import org.matsim.network.LinkImpl;
import org.matsim.network.NetworkLayer;
import org.matsim.network.Node;

public class PTLink extends LinkImpl {
	private String idPTLine;
	private boolean isBusStop;
	private String ptType;  // (PT, Transfer, Walking) 
	private int nextDepature;

	public PTLink(String id, Node from, Node to, NetworkLayer network,double length, double freespeed, double capacity, double permlanes) {
		super(new IdImpl(id), from, to, network, length, freespeed, capacity, permlanes);
	}

	public String getIdPTLine() {
		return idPTLine;
	}

	public void setIdPTLine(String idPTLine) {
		this.idPTLine = idPTLine;
	}

	public boolean isBusStop() {
		return isBusStop;
	}

	public void setBusStop(boolean isBusStop) {
		this.isBusStop = isBusStop;
	}

	public String getPtType() {
		return ptType;
	}

	public void setPtType(String ptType) {
		this.ptType = ptType;
	}

	public int getNextDepature() {
		return nextDepature;
	}

	public void setNextDepature(int nextDepature) {
		this.nextDepature = nextDepature;
	}
	
}// class
