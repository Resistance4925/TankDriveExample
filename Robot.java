// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it contains the code
 * necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  WPI_TalonFX _rghtFront = new WPI_TalonFX(4);
  WPI_TalonFX _rghtFollower = new WPI_TalonFX(3);
  WPI_TalonFX _leftFront = new WPI_TalonFX(2);
  WPI_TalonFX _leftFollower = new WPI_TalonFX(1);
 
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  Faults _faults_L = new Faults();
  Faults _faults_R = new Faults();

  @Override
  public void robotInit() {
    /* factory default values */
    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();

    /* set up followers */
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    _rghtFront.setInverted(TalonFXInvertType.Clockwise); // !< Update this
    _leftFront.setInverted(TalonFXInvertType.CounterClockwise); // !< Update this

    /*
     * set the invert of the followers to match their respective master controllers
     */
    _rghtFollower.setInverted(InvertType.FollowMaster);
    _leftFollower.setInverted(InvertType.FollowMaster);
/*
 * Talon FX does not need sensor phase set for its integrated sensor
 * This is because it will always be correct if the selected feedback device is integrated sensor (default value)
 * and the user calls getSelectedSensor* to get the sensor's position/velocity.
 * 
 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-phase
 */
    // _rghtFront.setSensorPhase(true);
    // _leftFront.setSensorPhase(true);

    /*
     * WPI drivetrain classes defaultly assume left and right are opposite. call
     * this so we can apply + to both sides when moving forward. DO NOT CHANGE
     */
    m_myRobot.setRightSideInverted(false);
    
    m_myRobot  = new DifferentialDrive(_leftFront, _rghtFront);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
  }
}
