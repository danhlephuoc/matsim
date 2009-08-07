/* *********************************************************************** *
 * project: org.matsim.*
 * RawLeg.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package playground.johannes.plans.plain;

import org.matsim.api.basic.v01.TransportMode;

/**
 * @author illenberger
 *
 */
public interface PlainLeg extends PlainPlanElement {

	public TransportMode getMode();
	
	public void setMode(TransportMode mode);
	
	public PlainRoute getRoute();
	
	public void setRoute(PlainRoute route);
	
}
