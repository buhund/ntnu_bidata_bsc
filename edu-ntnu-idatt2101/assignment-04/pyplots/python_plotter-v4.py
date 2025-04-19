import matplotlib.pyplot as plt
import numpy as np

# Step 1: Read data from the text file
with open('data2.txt', 'r') as file:
    lines = file.readlines()

# Initialize empty lists for "Steps" and "Time"
steps = []
time = []

# Step 2: Split data into lists
for line in lines:
    data = line.strip().split(', ')
    if len(data) == 3:
        _, s, t = map(int, data)  # Ignore "People" value
        steps.append(s)
        time.append(t)

# Step 3: Calculate the correlation between "Steps" and "Time"
correlation_steps_time = np.corrcoef(steps, time)[0, 1]

# Create a scatter plot of "Steps" vs. "Time"
plt.figure(figsize=(8, 6))
plt.scatter(steps, time, label=f'Correlation: {correlation_steps_time:.2f}', color='blue')

coefficient = np.polyfit(steps, time, 2)
poly = np.poly1d(coefficient)
x_new = np.linspace(min(steps), max(steps), 300)
y_new = poly(x_new)
plt.plot(x_new, y_new, color='r', label='Average Runtime')


plt.xlabel('Steps')
plt.ylabel('Time')
plt.legend()
plt.grid(True)

plt.title('Correlation Scatter Plot (Steps vs. Time)')
plt.show()
