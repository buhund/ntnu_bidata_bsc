# Class to operate the the rotation of the arm and the wheels
class Rotation:

    # Variables
    rotationSpeed = 0
    motor = None
    driveMode = False

    # Constructor
    def __init__(self, rotationSpeed, motor):
        self.rotationSpeed = rotationSpeed
        self.motor = motor

    # Methods
    def rotate(self, value):
        if self.driveMode:
            self.motor.run(-value * self.rotationSpeed)    
        else:
            self.motor.run(value * self.rotationSpeed)    
          
    def changeDriveMode(self):
        self.driveMode = not self.driveMode
        
