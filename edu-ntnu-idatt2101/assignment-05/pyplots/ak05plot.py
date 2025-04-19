import pandas as pd
import matplotlib.pyplot as plt

# Assuming data is loaded into a DataFrame named df
data = {
    'Strategy': ['LinearProbing']*5 + ['DoubleHashing']*5,
    'Percentage': [50, 80, 90, 99, 100]*2,
    'Collisions': [108492, 985005, 2320006, 7949302, 9855807,
                   1110612, 413926, 778511, 1591667, 2817857],
    'Time(ms)': [18, 52, 82, 127, 170,
                 193, 223, 256, 295, 337]
}

df = pd.DataFrame(data)

# Convert Percentage to string to treat it as categorical
df['Percentage'] = df['Percentage'].astype(str) + '%'

for metric in ['Collisions', 'Time(ms)']:
    plt.figure(figsize=(10, 6))
    for strategy in df['Strategy'].unique():
        subset = df[df['Strategy'] == strategy]
        plt.plot(subset['Percentage'], subset[metric], label=strategy)
    plt.title(f'{metric} by Percentage Load')
    plt.xlabel('Percentage Load')
    plt.ylabel(metric)
    plt.legend()
    plt.show()

