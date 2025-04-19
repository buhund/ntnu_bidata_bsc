# Class to operate the claw: Grip and release
class Claw:
    # Variables
    motor = None
    gripSpeed = 0
    
    # Constructor
    def __init__(self, motor, gripSpeed):
        self.motor = motor # Initialize motor
        self.gripSpeed = gripSpeed #Initialize the grip speed
    
    # Methods
    def grip(self, value):
        self.motor.run(value * self.gripSpeed) # Make the claw grip
        
    def release(self, value):
        # Do the things to release the ball
        self.motor.run(-value * self.gripSpeed) # Make the claw release
