# Class to operate the base to move forward or backwards
class Base:
    # Variables
    speed = 0
    motor = None
    driveMode = False
    
    # Constructor
    def __init__(self, speed, motor):
        self.speed = speed # Initialize speed
        self.motor = motor # Initialize motor
    
    # Methods
    def move(self, value):
        if self.driveMode: # Check if drive mode is enabled
            self.motor.run(-value * self.speed) # Drive backwards when in drivemode
        else:
            self.motor.run(value * self.speed) # Drive forwards when not in drivemode
            
    def changeDriveMode(self):
        self.driveMode = not self.driveMode # Change the drive mode
