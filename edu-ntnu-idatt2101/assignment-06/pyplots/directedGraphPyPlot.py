import os  # Import the os library
import matplotlib.pyplot as plt
import networkx as nx


# Read the graph data from given file, and return as a list of edges and number of vertices
def read_graph_from_file(file_path):
    with open(file_path, 'r') as f:
        lines = f.readlines()

    # First line in file contains the number of vertices and edges
    V, E = map(int, lines[0].strip().split())

    # Remaining lines contain the edges
    edges = [tuple(map(int, line.strip().split())) for line in lines[1:]]

    return V, edges


# Read graph data from file
file_path = "../ø6g7"  # Replace with the actual path to your file
V, edges = read_graph_from_file(file_path)

# Extract filename from the file path
filename = os.path.basename(file_path)

# Create a new directed graph
G = nx.DiGraph()


# Create a new figure and axes object using Matplotlib
fig, ax = plt.subplots(figsize=(15, 15))  # Increase figure size



# Modify spring layout parameters for more space
pos = nx.spring_layout(G, seed=42, scale=2.0)  # Increase scale parameter

# Add edges to the graph
for u, v in edges:
    G.add_edge(u, v)

# Draw the graph
pos = nx.spring_layout(G, seed=42, scale=2.0)  # positions for all nodes
nx.draw(G, pos, with_labels=True, node_color='lightblue', font_weight='bold',
        node_size=700, font_size=18, arrows=True, connectionstyle='arc3,rad=0.1')


# Add filename to the title
plt.title(f"Directed Graph {filename}")
plt.show()
