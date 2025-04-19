# Class to operate the arm
class Arm:
    # Variables
    motor = None
    armSpeed = 0
    
    # Constructor
    def __init__(self, motor, armSpeed):
        self.motor = motor # Intitialize motor
        self.armSpeed = armSpeed # Initialize arm speed
    
    # Methods
    def extendAndRetract(self, value):
        self.motor.run(value * self.armSpeed) # Extend and retract the arm
    
