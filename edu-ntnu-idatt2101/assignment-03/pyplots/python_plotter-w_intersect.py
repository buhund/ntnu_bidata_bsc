import matplotlib.pyplot as plt
import numpy as np

# Load data from the file
thresholds = []
times = []

with open("data.txt", "r") as f:
    for line in f:
        threshold, time = line.strip().split(", ")
        thresholds.append(int(threshold))
        times.append(int(time))

# Fit a polynomial curve
coeff = np.polyfit(thresholds, times, 2)
poly = np.poly1d(coeff)

# Calculate x-values for the curve
x_new = np.linspace(min(thresholds), max(thresholds), 300)
y_new = poly(x_new)

# Find the x-coordinate of the minimum point of the curve
x_min = x_new[np.argmin(y_new)]
y_min = np.min(y_new)

# Create the plot
plt.figure(figsize=(10, 6))
plt.scatter(thresholds, times, label='Data Points', c='b', marker='o')
plt.plot(x_new, y_new, color='r', label='Average Runtime')
plt.axvline(x=x_min, color='g', linestyle='--', label=f'Minimum at x={x_min:.2f}', ymax=y_min/np.max(y_new))
plt.xlabel('Threshold')
plt.ylabel('Runtime (nanoseconds)')
plt.title('Threshold vs Runtime (nanoseconds)')
plt.grid(True)
plt.legend()
plt.show()
