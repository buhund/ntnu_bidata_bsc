import matplotlib.pyplot as plt
import numpy as np

thresholds = []
times = []

with open("data.txt", "r") as f:
    for line in f:
        threshold, time = line.strip().split(", ")
        thresholds.append(int(threshold))
        times.append(int(time))

coeff = np.polyfit(thresholds, times, 2)
poly = np.poly1d(coeff)

x_new = np.linspace(min(thresholds), max(thresholds), 300)
y_new = poly(x_new)

plt.figure(figsize=(10, 6))
plt.scatter(thresholds, times, label='Data Points', c='b', marker='o')
plt.plot(x_new, y_new, color='r', label='Average Runtime')
plt.xlabel('Threshold')
plt.ylabel('Runtime (nanoseconds)')
plt.title('Threshold vs Runtime (nanoseconds)')
plt.grid(True)
plt.legend()
plt.show()
