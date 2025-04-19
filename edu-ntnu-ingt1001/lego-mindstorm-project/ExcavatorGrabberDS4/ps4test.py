#!/usr/bin/env pybricks-micropython
from pybricks.hubs import EV3Brick
from pybricks.ev3devices import (Motor, TouchSensor, ColorSensor,
                                 InfraredSensor, UltrasonicSensor, GyroSensor)
from pybricks.parameters import Port, Stop, Direction, Button, Color
from pybricks.tools import wait, StopWatch, DataLog
from pybricks.robotics import DriveBase
from pybricks.media.ev3dev import SoundFile, ImageFile

import struct
import threading

# Import own classes
#from .arm import Arm
#from .base import Base
#from .claw import Claw
#from .rotation import Rotation

# Create your objects here.
ev3 = EV3Brick()

# Initialize motors and sensors
#baseMotor = Motor(Port.A) # Motor to drive sideways

# Initialize classes
rotationMotor = Motor(Port.A) # rotation
armMotor = Motor(Port.B)
clawMotor = Motor(Port.C)
baseMotor = Motor(Port.D)
distanceSensor = UltrasonicSensor(Port.S1)

inFilePath = "/dev/input/event4"
inFile = open(inFilePath, "rb") 
FORMAT = 'llHHI'
eventSize = struct.calcsize(FORMAT)
event = inFile.read(eventSize)

driveMode = False # bool to decide if the robot is in drive mode or not
speedMultiplier = 20 # multiplier to add to the 127.5
rotationMultiplier = 2 # multiplier to add to the 127.5
armMultiplier = 1.5 # multiplier to add to the 127.5

def joystickValues(value): 
    minValue = 0
    maxValue = 255
    threshold = 10
    newValue = value - ((maxValue - minValue) / 2)
    
    if newValue > threshold or newValue < -threshold:
        return newValue
    return 0
    
def changeSpeed():
    while True:
        if distanceSensor.distance() < 300:
            global speedMultiplier = 1
            print("close")
        else:
            global speedMultiplier = 20
            print("not")
        
    
t1 = threading.Thread(target=changeSpeed)
t1.start()

while event: 
    (tv_sec, tv_usec, ev_type, code, value) = struct.unpack(FORMAT, event)
    
    #if code != 0:
    #    print(tv_sec, tv_usec, ev_type, code, value)  
        
    if ev_type == 3:        
        if code == 1: # left joystick up and down.
            if driveMode:
                baseMotor.run(-joystickValues(value) * speedMultiplier) # negative when the two wheels are the front
            else:
                baseMotor.run(joystickValues(value) * speedMultiplier) # negative when the two wheels are the front
        elif code == 3: # right joystick left and right
            if driveMode:
                rotationMotor.run(-joystickValues(value) * rotationMultiplier)
            else:
                rotationMotor.run(joystickValues(value) * rotationMultiplier)
        elif code == 4: # right joystick up and down
            armMotor.run(joystickValues(value) * armMultiplier)
    elif ev_type == 1:
        if code == 308: # square button
            if value == 0: # button is pressed and released                
                driveMode = not driveMode # change drive mode
                if driveMode:
                    ev3.speaker.say("Drive mode activated")
                else:
                    ev3.speaker.say("Work mode activated")
        elif code == 311: # R1
            clawMotor.run(value * 100)
        elif code == 310: # L1
            clawMotor.run(value * -100)
    
    event = inFile.read(eventSize)

inFile.close()
