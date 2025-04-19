#!/usr/bin/env pybricks-micropython
from pybricks.hubs import EV3Brick
from pybricks.ev3devices import (Motor, TouchSensor, ColorSensor,
                                 InfraredSensor, UltrasonicSensor, GyroSensor)
from pybricks.parameters import Port, Stop, Direction, Button, Color
from pybricks.tools import wait, StopWatch, DataLog
from pybricks.robotics import DriveBase
from pybricks.media.ev3dev import SoundFile, ImageFile

# Import own classes
from arm import Arm
from base import Base
from claw import Claw
from rotation import Rotation

# Initialize brick.
ev3 = EV3Brick()

# Initialize motors and sensors
baseMotor = Motor(Port.) # Motor to drive forward and backwards
rotationMotor = Motor(Port.A) # Motor to rotate the arm
armMotor = Motor(Port.B) # Motor to extend the arm
clawMotor = Motor(Port.C) # Motor to grip and release claw

# Initialize classes
robotBase = Base(speed=200, motor=baseMotor) # Class to operate the wheels
arm = Arm(armSpeed=100, motor=armMotor) # Class to operate the arm (motor, speed)
claw = Claw(motor=clawMotor, gripSpeed=100) # Class to operate the claw (speed, motor)
rotation = Rotation(rotationSpeed=100, motor=rotationMotor) # Class to operate the rotation (speed, startAngle, motor)

def joystickValues(value): # Method to convert the joystick values from (0, 255) to (-127.5, 127.5)
    minValue = 0
    maxValue = 255
    threshold = 10 # Threshold of joystick drift
    newValue = value - ((maxValue - minValue) / 2)
    
    return newValue if newValue > threshold or newValue < -threshold else return 0 # Remove joystick drift and return value

inFilePath = "/dev/input/event4" # Filepath to the events from the ps4 controller
inFile = open(inFilePath, "rb") # Open the file
FORMAT = 'llHHI' # Format of the events
eventSize = struct.calcsize(FORMAT) # Get the size of the event file

event = inFile.read(eventSize) # Read the file based on the formats to get the right inputs

while event: # loop while events on the controller happen
    if ev_type == 3:
        if code == 1: # left joystick up and down.
            robotBase.move(self, joystickValues(value))
        elif code == 3: # right joystick left and right
            rotation.rotate(self, joystickValues(value))
        elif code == 4: # right joystick up and down
            arm.extendAndRetract(self, joystickValues(value))
    elif ev_type == 1:
        if code == 308: # square button
            if value == 0: # button is pressed and released                
                robotBase.changeDriveMode(self)
                rotation.changeDriveMode(self)
                ev3.speaker.beep() # Beep to confirm the drive mode is changed
        elif code == 311: # R1 button
            claw.grip(self, value)
        elif code == 310: # L1 button
            claw.release(self, value)
            
    event = inFile.read(eventSize) # Read the file again for new inputs

inFile.close()
