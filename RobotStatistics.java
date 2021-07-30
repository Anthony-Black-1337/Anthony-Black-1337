/**
 * Copyright (c) 2001-2021 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;


import robocode.BattleResults;
import java.util.Map;
import java.util.HashMap;


/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Titus Chen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Nathaniel Troutman (contributor)
 */

 // Falar um pouco como o jogo funciona?

public class RobotStatistics implements ContestantStatistics //< Implements >
{ 

	private final RobotPeer robotPeer; // < Final > < Private >
	private int rank;
	private final int numberOfRobots;
	private boolean isActive;
	private boolean isInRound;

	private double survivalScore;
	private double lastSurvivorBonus; 
	private double bulletDamageScore;
	private double bulletKillBonus;
	private double rammingDamageScore;
	private double rammingKillBonus;

	private Map<String, Double> robotDamageMap; // < Map >

	private double totalScore;
	private double totalSurvivalScore;
	private double totalLastSurvivorBonus;
	private double totalBulletDamageScore;
	private double totalBulletKillBonus;
	private double totalRammingDamageScore;
	private double totalRammingKillBonus;

	private int totalFirsts;
	private int totalSeconds;
	private int totalThirds;

	RobotStatistics(RobotPeer robotPeer, int numberOfRobots) 
	{
		super();		// Falar sobre super?
		this.robotPeer = robotPeer;	// Falar sobre this?
		this.numberOfRobots = numberOfRobots;
	}

	public void setRank(int rank) {	// Falar sobre funçoes set para modificar variaveis private?
		this.rank = rank;
	}

	void reset() {
		resetScores();

		isActive = true;
		isInRound = true;
	}

	private void resetScores() {
		robotDamageMap = null;
		survivalScore = 0;
		lastSurvivorBonus = 0;
		bulletDamageScore = 0;
		bulletKillBonus = 0;
		rammingDamageScore = 0;
		rammingKillBonus = 0;
	}

	public void generateTotals() {
		totalSurvivalScore += survivalScore;
		totalLastSurvivorBonus += lastSurvivorBonus;
		totalBulletDamageScore += bulletDamageScore;
		totalBulletKillBonus += bulletKillBonus;
		totalRammingDamageScore += rammingDamageScore;
		totalRammingKillBonus += rammingKillBonus;

		totalScore = robotPeer.isSentryRobot()	// Pesquisar o que são esses sinais?
				? 0
				: totalBulletDamageScore + totalRammingDamageScore + totalSurvivalScore + totalRammingKillBonus
				+ totalBulletKillBonus + totalLastSurvivorBonus;

		isInRound = false;
	}

	public double getTotalScore() {		// Falar sobre funçoes get para acessar uma variavel private?
		return totalScore;
	}

	public double getTotalSurvivalScore() {
		return totalSurvivalScore;
	}

	public double getTotalLastSurvivorBonus() {
		return totalLastSurvivorBonus;
	}

	public double getTotalBulletDamageScore() {
		return totalBulletDamageScore;
	}

	public double getTotalBulletKillBonus() {
		return totalBulletKillBonus;
	}

	public double getTotalRammingDamageScore() {
		return totalRammingDamageScore;
	}

	public double getTotalRammingKillBonus() {
		return totalRammingKillBonus;
	}

	public int getTotalFirsts() {
		return totalFirsts;
	}

	public int getTotalSeconds() {
		return totalSeconds;
	}

	public int getTotalThirds() {
		return totalThirds;
	}

	public double getCurrentScore() {
		return robotPeer.isSentryRobot()
				? 0
				: (bulletDamageScore + rammingDamageScore + survivalScore + rammingKillBonus + bulletKillBonus
				+ lastSurvivorBonus);
	}

	public double getCurrentSurvivalScore() {
		return survivalScore;
	}

	public double getCurrentSurvivalBonus() {
		return lastSurvivorBonus;
	}

	public double getCurrentBulletDamageScore() {
		return bulletDamageScore;
	}

	public double getCurrentBulletKillBonus() {
		return bulletKillBonus;
	}

	public double getCurrentRammingDamageScore() {
		return rammingDamageScore;
	}

	public double getCurrentRammingKillBonus() {
		return rammingKillBonus;
	}

	public void scoreSurvival() {
		if (isActive && !robotPeer.isSentryRobot()) { // Pesquisar isActive e o que ela faz?
			survivalScore += 50;
		}
	}

	public void scoreLastSurvivor() {
		if (isActive && !robotPeer.isSentryRobot()) {
			int enemyCount = numberOfRobots - 1;

			if (robotPeer.getTeamPeer() != null) {
				enemyCount -= (robotPeer.getTeamPeer().size() - 1);
			}

			lastSurvivorBonus += 10 * enemyCount;

			if ((robotPeer.getTeamPeer() == null || robotPeer.isTeamLeader())) {
				totalFirsts++;
			}
		}
	}

	void scoreBulletDamage(String robot, double damage) {
		if (isActive) {
			incrementRobotDamage(robot, damage);
			bulletDamageScore += damage;
		}
	}

	double scoreBulletKill(String robot) {
		if (isActive) {
			double bonus;

			if (robotPeer.getTeamPeer() == null) {
				bonus = getRobotDamage(robot) * 0.20;
			} else {
				bonus = 0;
				for (RobotPeer teammate : robotPeer.getTeamPeer()) {
					bonus += teammate.getRobotStatistics().getRobotDamage(robot) * 0.20;
				}
			}

			bulletKillBonus += bonus;
			return bonus;
		}
		return 0;
	}

	void scoreRammingDamage(String robot) {
		if (isActive) {
			incrementRobotDamage(robot, robocode.Rules.ROBOT_HIT_DAMAGE);
			rammingDamageScore += robocode.Rules.ROBOT_HIT_BONUS;
		}
	}

	double scoreRammingKill(String robot) {
		if (isActive) {
			double bonus;

			if (robotPeer.getTeamPeer() == null) {
				bonus = getRobotDamage(robot) * 0.30;
			} else {
				bonus = 0;
				for (RobotPeer teammate : robotPeer.getTeamPeer()) {
					bonus += teammate.getRobotStatistics().getRobotDamage(robot) * 0.30;
				}
			}
			rammingKillBonus += bonus;
			return bonus;
		}
		return 0;
	}

	public void scoreRobotDeath(int enemiesRemaining) {
		if (!robotPeer.isSentryRobot()) {
			switch (enemiesRemaining) {		// Falar sobre cases?
			case 0:
				if (!robotPeer.isWinner()) {
					totalFirsts++;
				}
				break;
	
			case 1:
				totalSeconds++;
				break;
	
			case 2:
				totalThirds++;
				break;
			}
		}
	}

	public void scoreFirsts() {
		if (isActive && !robotPeer.isSentryRobot()) {
			totalFirsts++;
		}
	}

	void setInactive() {
		resetScores();		// Falar como funciona chamar uma função dentro de outra para o mesmo objeto?
		isActive = false;
	}

	public BattleResults getFinalResults() {
		return new BattleResults(robotPeer.getTeamName(), rank, totalScore, totalSurvivalScore, totalLastSurvivorBonus,
				totalBulletDamageScore, totalBulletKillBonus, totalRammingDamageScore, totalRammingKillBonus, totalFirsts,
				totalSeconds, totalThirds);
	}

	private double getRobotDamage(String robot) {
		if (robotDamageMap == null) {
			robotDamageMap = new HashMap<String, Double>();		// Pesquisar sobre HashMap?
		}
		Double damage = robotDamageMap.get(robot);

		return (damage != null) ? damage : 0;
	}

	private void incrementRobotDamage(String robot, double damage) {
		double newDamage = getRobotDamage(robot) + damage;

		robotDamageMap.put(robot, newDamage);
	}

	void cleanup() {// Do nothing, for now
	}

	public boolean isInRound() {
		return isInRound;
	}
}