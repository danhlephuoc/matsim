/* *********************************************************************** *
 * project: org.matsim.*
 * IVT2006ToGraphML.java
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

/**
 * 
 */
package playground.johannes.socialnetworks.ivtsurveys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.matsim.api.basic.v01.population.BasicActivity;
import org.matsim.api.basic.v01.population.BasicPerson;
import org.matsim.api.basic.v01.population.BasicPlan;
import org.matsim.api.basic.v01.population.BasicPopulation;
import org.matsim.api.basic.v01.population.BasicPopulationBuilder;
import org.matsim.api.basic.v01.population.PlanElement;
import org.matsim.core.basic.v01.BasicPersonImpl;
import org.matsim.core.basic.v01.BasicPlanImpl;
import org.matsim.core.basic.v01.IdImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationWriter;
import org.matsim.core.utils.geometry.CoordImpl;
import org.matsim.core.utils.geometry.transformations.WGS84toCH1903LV03;
import org.matsim.core.utils.io.IOUtils;

import playground.johannes.socialnetworks.graph.social.Ego;
import playground.johannes.socialnetworks.graph.social.SocialNetwork;
import playground.johannes.socialnetworks.graph.social.io.SNGraphMLWriter;

/**
 * @author illenberger
 * 
 */
public class IVT2006ToGraphML {
	
	private static final Logger logger = Logger.getLogger(IVT2006ToGraphML.class);

	private static final String TAB = "\t";

	private static final String HOME_ACT_TYPE = "home";

	private static WGS84toCH1903LV03 transform = new WGS84toCH1903LV03();

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String egofile = args[0];
		String alterfile = args[1];
		String popfilename = args[2];
		String graphfile = args[3];

		SocialNetwork<BasicPerson<BasicPlan<PlanElement>>> socialnet = new SocialNetwork<BasicPerson<BasicPlan<PlanElement>>>();
		BasicPopulation population = new PopulationImpl();
		HashMap<String, Ego<BasicPerson<BasicPlan<PlanElement>>>> egos = new HashMap<String, Ego<BasicPerson<BasicPlan<PlanElement>>>>();
		
		int maxId = 0;
		/*
		 * Load egos...
		 */
		logger.info("Loading egos from file " + egofile);

		BufferedReader reader = IOUtils.getBufferedReader(egofile);
		String line;
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split(TAB);
			/*
			 * Create a BasicPerson
			 */
			BasicPerson<BasicPlan<PlanElement>> person = createPerson(
					tokens[0], tokens[1], tokens[2], population);
			population.getPersons().put(person.getId(), person);
			/*
			 * Create an Ego
			 */
			Ego<BasicPerson<BasicPlan<PlanElement>>> ego = socialnet
					.addEgo(person);
			egos.put(tokens[0], ego);
			maxId = Math.max(Integer.parseInt(tokens[0]), maxId);
		}
		/*
		 * Load alters...
		 */
		logger.info("Loading alters from file " + alterfile);

		Set<Ego<BasicPerson<BasicPlan<PlanElement>>>> anonymousVertices = new HashSet<Ego<BasicPerson<BasicPlan<PlanElement>>>>();
		reader = IOUtils.getBufferedReader(alterfile);
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split(TAB);
			Ego<BasicPerson<BasicPlan<PlanElement>>> ego = egos.get(tokens[0]);
			if (ego == null) {
				logger.fatal(String.format("Ego with id %1$s not found!", tokens[0]));
			} else {
				BasicPerson<BasicPlan<PlanElement>> person = createPerson(String.valueOf(++maxId), tokens[1], tokens[2],population);
				population.getPersons().put(person.getId(), person);

				Ego<BasicPerson<BasicPlan<PlanElement>>> alter = socialnet
						.addEgo(person);

				socialnet.addEdge(ego, alter);
				anonymousVertices.add(alter);
			}
		}
		/*
		 * Write population and social network...
		 */
		logger.info("Writing population to " + popfilename);
		PopulationWriter popWriter = new PopulationWriter(population, popfilename);
		popWriter.write();
		
		logger.info("Writing social network to " + graphfile);
		SNGraphMLWriter graphWriter = new SNGraphMLWriter();
		graphWriter.write(socialnet, graphfile);
		/*
		 * Write anonymous vertices...
		 */
		if(args.length > 3)
			SNGraphMLWriter.writeAnonymousVertices(anonymousVertices, args[4]);
	}

	private static BasicPerson<BasicPlan<PlanElement>> createPerson(
			String id, String x, String y, BasicPopulation population) {
		BasicPerson<BasicPlan<PlanElement>> person = new BasicPersonImpl<BasicPlan<PlanElement>>(
				new IdImpl(id));
		BasicPlan<PlanElement> plan = new BasicPlanImpl(person);
		person.addPlan( plan ) ;
//		BasicActivityImpl act = new BasicActivityImpl(HOME_ACT_TYPE);
//		act.setCoord(transform.transform(new CoordImpl(Double.parseDouble(x),
//				Double.parseDouble(y))));
		
		BasicPopulationBuilder pb = population.getPopulationBuilder() ;
		BasicActivity act = pb.createActivityFromCoord(HOME_ACT_TYPE,new CoordImpl(Double.parseDouble(x),
				Double.parseDouble(y))) ;
		
		plan.addActivity(act);

		return person;
	}
}