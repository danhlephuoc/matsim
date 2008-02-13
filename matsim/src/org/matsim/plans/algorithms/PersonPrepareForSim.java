/* *********************************************************************** *
 * project: org.matsim.*
 * PersonPrepareForSim.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
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

package org.matsim.plans.algorithms;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.matsim.plans.Leg;
import org.matsim.plans.Person;
import org.matsim.plans.Plan;

/**
 * Performs several checks that persons are ready for a mobility simulation.
 * It is intended to run only once after the initial plans are read from file,
 * as we expect the {@link org.matsim.controler.Controler} not to "damage" any
 * plans during the iterations.<br/>
 * Currently, this only checks that all plans have valid routes, calculating
 * missing routes if required. Additionally, it will output a warning to the
 * log if a person has no plans at all.
 *
 * @author mrieser
 */
public class PersonPrepareForSim extends PersonAlgorithm {

	private final PlanAlgorithmI router;

	private static final Logger log = Logger.getLogger(PersonPrepareForSim.class);

	public PersonPrepareForSim(final PlanAlgorithmI router) {
		super();
		this.router = router;
	}

	@Override
	public void run(final Person person) {
		// first make sure we have a selected plan
		Plan selectedPlan = person.getSelectedPlan();
		if (selectedPlan == null) {
			// the only way no plan can be selected should be when the person has no plans at all
			log.warn("Person " + person.getId() + " has no plans!");
			return;
		}

		// make sure all the plans have valid routes
		for (Plan plan : person.getPlans()) {
			boolean hasRoute = true;
			ArrayList<Object> actslegs = plan.getActsLegs();
			for (int i = 1; i < actslegs.size(); i = i+2) {
				Leg leg = (Leg)actslegs.get(i);
				if (leg.getRoute() == null) {
					hasRoute = false;
				}
			}
			if (!hasRoute) {
				this.router.run(plan);
			}
		}

	}

}
