/* *********************************************************************** *
 * project: org.matsim.*
 * NetworkBuilder
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009, 2011 by the members listed in the COPYING,  *
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

package org.matsim.api.core.v01.network;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.core.api.internal.MatsimFactory;

/**
 * @author dgrether
 * @author mrieser
 */
public interface NetworkFactory extends MatsimFactory {

	public Node createNode(final Id id, final Coord coord ) ;

	/**
	 * @param id
	 * @param fromNodeId needs to be an id of a node that has already been added to the network
	 * @param toNodeId needs to be an id of a node that has already been added to the network
	 * @return the created link
	 * @deprecated use {@link #createLink(Id, Node, Node)} instead
	 */
	@Deprecated
	public Link createLink(final Id id, final Id fromNodeId, final Id toNodeId);

	/**
	 * Creates a link with the given id leading from one node to another.
	 *
	 * @param id
	 * @param fromNode
	 * @param toNode
	 * @return the newly created link
	 */
	public Link createLink(final Id id, final Node fromNode, final Node toNode);

}
